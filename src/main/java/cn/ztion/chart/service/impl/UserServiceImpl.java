package cn.ztion.chart.service.impl;

import cn.ztion.chart.entity.User;
import cn.ztion.chart.mapper.UserMapper;
import cn.ztion.chart.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 11:56
 * @Description: UserServiceImpl
 * @Version 1.0.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }
}
