package cn.ztion.chart.service;

import cn.ztion.chart.entity.Chat;
import cn.ztion.chart.entity.vo.MessageVo;
import cn.ztion.chart.openai.Exception.TokenExceededException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatService {
    /**
     * 发送消息
     *
     * @param messageVo 消息内容
     */
    SseEmitter talk(MessageVo messageVo) throws TokenExceededException;

    /**
     * 查找用户会话，没有则创建
     *
     * @param userId  用户id
     * @param modelId 模型id
     * @return 会话
     */
    Chat getChat(Long userId, Long modelId);
}
