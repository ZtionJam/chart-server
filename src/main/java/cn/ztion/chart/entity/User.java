package cn.ztion.chart.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 11:38
 * @Description: User
 * @Version 1.0.0
 */

@Data
@Table("user")
public class User {
    @Id
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 密码
     */
    private String password;

}
