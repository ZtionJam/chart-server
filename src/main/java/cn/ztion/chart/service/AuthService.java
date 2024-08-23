package cn.ztion.chart.service;

import cn.ztion.chart.entity.vo.AccountLoginVo;
import cn.ztion.chart.entity.vo.TokenInfo;

public interface AuthService {
    /**
     * 用户端账号密码登录
     *
     * @return token信息
     */
    TokenInfo appUserLogin(AccountLoginVo loginInfo);
}
