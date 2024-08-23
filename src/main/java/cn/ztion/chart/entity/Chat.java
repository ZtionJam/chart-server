package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 15:58
 * @Description: 会话
 * @Version 1.0.0
 */

@Data
@Accessors(chain = true)
public class Chat {
    @Id
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 模型类型
     */
    private String modelType;
    /**
     * 模型id
     */
    private Long modelId;
    /**
     * 模型名字
     */
    private String modelName;
}
