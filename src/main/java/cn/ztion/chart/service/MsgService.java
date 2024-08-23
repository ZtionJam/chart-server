package cn.ztion.chart.service;

import cn.ztion.chart.entity.Msg;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface MsgService {
    /**
     * 查询会话消息
     *
     * @param chatId 会话id
     * @param userId 用户
     * @return 消息列表
     */
    List<Msg> getChatMessageList(Long chatId, Long userId,Boolean hasSystem);

    /**
     * 消息追加
     *
     * @param msgId 消息id
     */
    void msgAppend(Long msgId, String content);

    /**
     * 清除会话
     * @param chatId 会话id
     */
    void clear(Long chatId,Long userId);

    /**
     * 检查限制
     * @param chatId 会话id
     * @param userId 用户id
     */
    Boolean checkLimit(Long chatId,Long userId, SseEmitter emitter);

    /**
     * 删除消息
     * @param msgId 消息id
     * @return 结果
     */
    Boolean del(Long msgId);

    /**
     * 根据id取
     * @param msgId id
     * @return 消息
     */
    Msg findById(Long msgId);
}
