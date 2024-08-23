package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import lombok.Data;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 15:25
 * @Description: 模型实体
 * @Version 1.0.0
 */

@Data
public class Model {
    @Id
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 完整模型名字
     */
    private String fullName;
    /**
     * 介绍
     */
    private String detail;
    /**
     * 限制等级
     */
    private Integer level;
    /**
     * 预制提示词(咒语)
     */
    private String prompt;
    /**
     * 图标
     */
    private String avatar;

}
