package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:14
 * @Description: Message
 * @Version 1.0.0
 */

@Data
@Accessors(chain = true)
public class Msg {
    @Id
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 会话id
     */
    private Long chatId;
    /**
     * 内容
     */
    private String content;
    /**
     * 角色
     */
    private String role;
    /**
     * 时间
     */
    private String time;
}
