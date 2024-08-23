package cn.ztion.chart.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:14
 * @Description: Token实体
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    /**
     * token值
     */
    private String token;
    /**
     * 过期时间
     */
    private Long timeout;
}
