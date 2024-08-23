package cn.ztion.chart.entity;


import com.mybatisflex.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缓存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cache {
    /**
     * 键
     */
    @Id
    private String key;
    /**
     * 值
     */
    private String value;
    /**
     * 失效时间
     */
    private Long expiry = -1L;
}
