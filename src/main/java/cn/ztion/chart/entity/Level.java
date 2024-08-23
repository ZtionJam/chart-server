package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import lombok.Data;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:05
 * @Description: 等级
 * @Version 1.0.0
 */

@Data
public class Level {
    @Id
    private Long id;
    /**
     * 等级值
     */
    private Integer level;
    /**
     * 等级名
     */
    private String name;
    /**
     * 每日限额
     */
    private Integer dayLimit;
    /**
     * 是否可以自定模型
     */
    private Boolean canCustomModel;
}
