package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 11:53
 * @Description: BaseEntity
 * @Version 1.0.0
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
    @Id
    private Long id;
}
