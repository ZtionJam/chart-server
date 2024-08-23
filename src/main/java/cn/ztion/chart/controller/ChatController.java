package cn.ztion.chart.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.ztion.chart.entity.Msg;
import cn.ztion.chart.entity.vo.ChatVo;
import cn.ztion.chart.entity.vo.MessageVo;
import cn.ztion.chart.entity.vo.ModelVO;
import cn.ztion.chart.openai.Exception.TokenExceededException;
import cn.ztion.chart.service.ChatService;
import cn.ztion.chart.service.ModelService;
import cn.ztion.chart.service.MsgService;
import cn.ztion.chart.util.R;
import cn.ztion.chart.util.ServiceAssert;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:18
 * @Description: 会话交互
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/app/chat")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ModelService modelService;
    private final MsgService msgService;

    @PostMapping("/talk")
    public SseEmitter talk(@RequestBody @Validated MessageVo message) throws TokenExceededException {


        return chatService.talk(message);
    }

    @GetMapping("/something")
    public R<String> something() {
        return R.ok();
    }

    /**
     * 会话详情
     *
     * @param modelId 米星id
     * @return 会话实体
     */
    @GetMapping("/modelChat")
    public R<ChatVo> modelChat(@RequestParam Long modelId) {
        return R.ok(new ChatVo().from(chatService.getChat(StpUtil.getLoginIdAsLong(), modelId)));
    }

    /**
     * 模型列表
     *
     * @return 模型列表
     */
    @GetMapping("/modelList")
    public R<List<ModelVO>> modelList() {
        return R.ok(modelService.getUserCanUseModel(StpUtil.getLoginIdAsLong()).stream().map(m -> new ModelVO().from(m)).collect(Collectors.toList()));
    }

    /**
     * 消息列表
     *
     * @param chatId 会话id
     * @return 消息列表
     */
    @GetMapping("/msgList")
    public R<List<MessageVo>> msgList(@RequestParam Long chatId) {
        return R.ok(msgService.getChatMessageList(chatId, StpUtil.getLoginIdAsLong(), false).stream().map(m -> new MessageVo().from(m)).collect(Collectors.toList()));
    }

    /**
     * 消息列表清理
     *
     * @param chatId 会话id
     * @return 消息列表
     */
    @GetMapping("/msgClear")
    public R<String> msgClear(@RequestParam Long chatId) {
        msgService.clear(chatId, StpUtil.getLoginIdAsLong());
        return R.ok("清理完成");
    }

    /**
     * 消息
     *
     * @param msgId id
     * @return 结果
     */
    @GetMapping("/msg/{msgId}")
    public R<MessageVo> getById(@PathVariable Long msgId) {
        Msg msg = msgService.findById(msgId);
        ServiceAssert.ok(msg.getUserId().equals(StpUtil.getLoginIdAsLong()), "越权操作");
        return R.ok(new MessageVo().from(msg));
    }
}
