package cn.ztion.chart.entity.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:04
 * @Description: 普通账号密码登录入参
 * @Version 1.0.0
 */
@Data
public class AccountLoginVo {
    /**
     * 账号
     */
    @NotEmpty(message = "账号不能为空")
    private String username;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
}
