package org.apei.userserver.controller;
import org.apei.bizcommon.constant.StatusCodeEnum;
import org.apei.bizcommon.entity.Result;
import org.apei.bizcommon.entity.user.User;
import org.apei.bizcommon.util.JwtUtil;
import org.apei.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        boolean result = userService.login(user);//验证密码是否正确
        if (result){
            //密码是正确的
            //生成jwt令牌,返回到客户端
            Map<String,String> info = new HashMap<>();
            info.put("username",user.getUsername());
            info.put("password",user.getPassword());
            //基于工具类生成jwt令牌
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), user.getUsername(), null);
            info.put("token",jwt);
            System.out.println("token: "+jwt);
            return new Result(true, StatusCodeEnum.OK.getCode(),info.toString());
        }else{
            //密码错误
            return new Result(false, StatusCodeEnum.ERROR.getCode(),"密码错误");
        }
    }

}
