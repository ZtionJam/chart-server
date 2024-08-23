package cn.ztion.chart.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.ztion.chart.constants.CommonStr;
import cn.ztion.chart.constants.OpenAiMsg;
import cn.ztion.chart.entity.*;
import cn.ztion.chart.mapper.*;
import cn.ztion.chart.service.MsgService;
import cn.ztion.chart.util.CacheDao;
import cn.ztion.chart.util.ServiceAssert;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@AllArgsConstructor
public class MsgServiceImpl implements MsgService {

    private final UserMapper userMapper;
    private final ChatMapper chatMapper;
    private final MsgMapper msgMapper;
    private final LevelMapper levelMapper;
    private final ModelMapper modelMapper;
    private final CacheDao cacheDao;
    private final Snowflake snowflake;

    @Override
    public List<Msg> getChatMessageList(Long chatId, Long userId, Boolean hasSystem) {
        Chat chat = chatMapper.selectOneById(chatId);
        ServiceAssert.ok(chat.getUserId().equals(userId), OpenAiMsg.NO_PER);
        return msgMapper.selectListByQuery(QueryWrapper.create()
                .ne(Msg::getRole, "system", !hasSystem)
                .eq(Msg::getChatId, chatId));

    }

    @Override
    @Async
    public void msgAppend(Long msgId, String content) {
        synchronized ((msgId + "").intern()) {
            msgMapper.msgAppend(content, msgId);
        }
    }

    @Override
    public void clear(Long chatId, Long userId) {
        msgMapper.deleteByQuery(QueryWrapper.create()
                .eq(Msg::getUserId, userId)
                .eq(Msg::getChatId, chatId));
        Chat chat = chatMapper.selectOneById(chatId);
        Model model = modelMapper.selectOneById(chat.getModelId());
        //重新下咒语
        Msg msg = new Msg()
                .setChatId(chat.getId())
                .setTime(DateUtil.now())
                .setContent(model.getPrompt())
                .setRole("system")
                .setUserId(chat.getUserId())
                .setId(snowflake.nextId());
        msgMapper.insert(msg);
    }

    @SneakyThrows
    @Override
    public Boolean checkLimit(Long chatId, Long userId, SseEmitter emitter) {
        String key = CommonStr.LIMIT_COUNT + userId + ":" + DateUtil.today();
        Integer times = 0;
        String todayCountStr = cacheDao.get(key);
        if (todayCountStr != null) {
            times = Integer.valueOf(todayCountStr);
        }
        User user = userMapper.selectOneById(userId);
        Level level = levelMapper.selectOneById(user.getLevel());
        if (level.getDayLimit() <= times) {
            emitter.send(SseEmitter.event().data(OpenAiMsg.DAY_LIMIT_ERR + CommonStr.END_DATA));
            emitter.send(SseEmitter.event().data(CommonStr.SSE_DOWN + CommonStr.END_DATA));
            emitter.complete();
            return false;
        }
        return true;
    }

    @Override
    public Boolean del(Long msgId) {

        return msgMapper.deleteById(msgId) > 0;
    }

    @Override
    public Msg findById(Long msgId) {
        return msgMapper.selectOneById(msgId);
    }
}








