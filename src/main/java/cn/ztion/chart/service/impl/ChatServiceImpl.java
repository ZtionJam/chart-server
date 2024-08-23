package cn.ztion.chart.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.ztion.chart.constants.OpenAiMsg;
import cn.ztion.chart.entity.Chat;
import cn.ztion.chart.entity.Model;
import cn.ztion.chart.entity.Msg;
import cn.ztion.chart.entity.User;
import cn.ztion.chart.entity.vo.MessageVo;
import cn.ztion.chart.enums.ModelTypeEnum;
import cn.ztion.chart.mapper.ChatMapper;
import cn.ztion.chart.mapper.ModelMapper;
import cn.ztion.chart.mapper.MsgMapper;
import cn.ztion.chart.mapper.UserMapper;
import cn.ztion.chart.openai.Exception.TokenExceededException;
import cn.ztion.chart.openai.OpenAI;
import cn.ztion.chart.openai.domain.ChatParam;
import cn.ztion.chart.openai.domain.Message;
import cn.ztion.chart.service.ChatService;
import cn.ztion.chart.service.MsgService;
import cn.ztion.chart.util.ServiceAssert;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:17
 * @Description: ChatServiceImpl
 * @Version 1.0.0
 */
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;
    private final UserMapper userMapper;
    private final MsgMapper msgMapper;
    private final ModelMapper modelMapper;
    private final Snowflake snowflake;
    private final MsgService msgService;
    private final OpenAI openAI;

    @Override
    @Transactional
    public SseEmitter talk(MessageVo messageVo) throws TokenExceededException {
        Chat chat = chatMapper.selectOneById(messageVo.getChatId());
        ServiceAssert.notNull(chat, OpenAiMsg.CHAT_NOT_EXISTS);
        Model model = modelMapper.selectOneById(chat.getModelId());
        ServiceAssert.notNull(model, OpenAiMsg.MODEL_NOT_EXISTS);
        //消息列表,转换消息体
        Msg msg = messageVo.to()
                .setTime(DateUtil.now())
                .setRole("user")
                .setUserId(chat.getUserId())
                .setId(snowflake.nextId());
        Msg aiMsg = messageVo.to()
                .setContent("")
                .setUserId(chat.getUserId())
                .setTime(DateUtil.now())
                .setRole("assistant")
                .setId(snowflake.nextId());
        msgMapper.insert(msg);
        msgMapper.insert(aiMsg);
        List<Msg> chatMessageList = msgService.getChatMessageList(msg.getChatId(), chat.getUserId(), true);
        chatMessageList.add(msg);
        List<Message> messages = chatMessageList.stream().map(m -> new Message(m.getRole(), m.getContent())).toList();
        //构建请求体
        ChatParam chatParam = new ChatParam()
                .setStream(true)
                .setModel(model.getFullName())
                .setMessages(messages);
        //SSE创建
        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);
        Long msgId = aiMsg.getId();
        emitter.onCompletion(() -> OpenAI.msgClients.remove(msgId));
        emitter.onTimeout(() -> OpenAI.msgClients.remove(msgId));
        emitter.onError((e) -> OpenAI.msgClients.remove(msgId));
        //请求
        if (msgService.checkLimit(chat.getId(), chat.getUserId(), emitter)) {
            OpenAI.msgClients.put(msgId, emitter);
            openAI.chatStream(chatParam, msgId);
        } else {
            msgMapper.deleteBatchByIds(List.of(msg.getId(), aiMsg.getId()));
        }

        return emitter;
    }

    @Override
    @Transactional
    public Chat getChat(Long userId, Long modelId) {
        Chat chat = chatMapper.selectOneByQuery(QueryWrapper.create()
                .eq(Chat::getModelId, modelId)
                .eq(Chat::getUserId, userId));
        if (chat == null) {
            User user = userMapper.selectOneById(userId);
            ServiceAssert.notNull(modelId, OpenAiMsg.PLEASE_CHOOSE_MODEL);
            Model model = modelMapper.selectOneById(modelId);
            ServiceAssert.notNull(model, OpenAiMsg.MODEL_NOT_EXISTS);
            ServiceAssert.ok(user.getLevel() >= model.getLevel(), OpenAiMsg.LEVEL_LOW);
            chat = new Chat()
                    .setId(snowflake.nextId())
                    .setUserId(userId)
                    .setModelId(modelId)
                    .setModelType(ModelTypeEnum.Default.getValue())
                    .setModelName(model.getName());
            chatMapper.insert(chat);
            //咒语注入
            Msg msg = new Msg()
                    .setChatId(chat.getId())
                    .setTime(DateUtil.now())
                    .setContent(model.getPrompt())
                    .setRole("system")
                    .setUserId(chat.getUserId())
                    .setId(snowflake.nextId());
            msgMapper.insert(msg);
        }

        return chat;
    }


}
