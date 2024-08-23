package cn.ztion.chart.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.ztion.chart.constants.AuthErrMsg;
import cn.ztion.chart.entity.User;
import cn.ztion.chart.entity.vo.AccountLoginVo;
import cn.ztion.chart.entity.vo.TokenInfo;
import cn.ztion.chart.mapper.UserMapper;
import cn.ztion.chart.service.AuthService;
import cn.ztion.chart.util.ServiceAssert;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:17
 * @Description: AuthServiceImpl
 * @Version 1.0.0
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    @Override
    public TokenInfo appUserLogin(AccountLoginVo loginInfo) {
        User user = userMapper.selectOneByQuery(QueryWrapper.create()
                .eq(User::getUsername, loginInfo.getUsername()));
        //未注册
        ServiceAssert.notNull(user, AuthErrMsg.USERNAME_OR_PASS_ERR);
        //密码校验
        ServiceAssert.ok(loginInfo.getPassword().equals(user.getPassword()), AuthErrMsg.USERNAME_OR_PASS_ERR);

        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return new TokenInfo(tokenInfo.getTokenValue(), tokenInfo.getTokenTimeout());
    }
}
