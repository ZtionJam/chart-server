package cn.ztion.chart.controller;

import cn.ztion.chart.entity.vo.AccountLoginVo;
import cn.ztion.chart.entity.vo.TokenInfo;
import cn.ztion.chart.service.AuthService;
import cn.ztion.chart.util.R;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:02
 * @Description: 登录控制
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 账密登录
     *
     * @param loginInfo 登录信息
     * @return token信息
     */
    @PostMapping("/login")
    public R<TokenInfo> userLogin(@RequestBody @Validated AccountLoginVo loginInfo) {
        return R.ok(authService.appUserLogin(loginInfo));
    }

}
