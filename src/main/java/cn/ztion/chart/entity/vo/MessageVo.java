package cn.ztion.chart.entity.vo;

import cn.ztion.chart.entity.Msg;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:21
 * @Description: 消息
 * @Version 1.0.0
 */
@Data
public class MessageVo {
    /**
     * 消息id
     */
    private String id;
    /**
     * 会话id
     */
    private Long chatId;
    /**
     * 内容
     */
    @NotEmpty(message = "消息内容不能为空")
    private String content;
    /**
     * 角色
     */
    private String role;

    public MessageVo from(Msg m) {
        BeanUtils.copyProperties(m, this);
        return this;
    }

    public Msg to() {
        Msg m = new Msg();
        BeanUtils.copyProperties(this, m);
        return m;
    }
}
