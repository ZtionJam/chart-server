package cn.ztion.chart.controller;

import cn.ztion.chart.entity.User;
import cn.ztion.chart.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 11:45
 * @Description: 用户端 用户信息
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/app/user")
@AllArgsConstructor
public class UserAppController {

    private final UserService userService;

    @GetMapping("/list")
    public List<User> text() {
        return userService.getAllUser();
    }
}
