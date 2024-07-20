package org.apei.userserver.controller.login;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.constant.StatusCodeEnum;
import org.apei.bizcommon.entity.Result;
import org.apei.bizcommon.util.JwtUtil;
import org.apei.userserver.service.login.LARService;
import org.apei.userserver.vo.login.LoginForm;
import org.apei.userserver.vo.login.RegisterForm;
import org.apei.userserver.vo.request.user.UserInfoRequest;
import org.apei.userserver.vo.user.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
* @description: 登录与注册控制器
* @author apeiMark
* @date 2024/7/12
*/

@Slf4j
@RestController
public class LARController {

    private LARService LARService;
    private final int USERNAME = 1;
    private final int EMAIL = 2;
    private final int MOBILENUMBER = 3;
    private final int QQ = 4;
    private final int WECHART = 5;
    private final int TENCENTWEIBO = 6;
    private final int SINAWEIBO = 7;

    @Autowired
    public LARController(LARService LARService) {
        this.LARService = LARService;
    }

    /**
    * @description:
    * @param loginForm
    * @return org.apei.bizcommon.entity.Result
    * @author apeiMark
    * @date 2024/7/12
    */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm){
        log.info("LoginForm: "+loginForm);
        if(loginForm==null){
            return new Result(false, StatusCodeEnum.ERROR.getCode(),"登录表单为空，请稍后重试...");
        } else{
            try{
                Long uid;
                switch(loginForm.getIdentityType()){
                    case USERNAME :
                        //用户名登录
                        uid = LARService.loginByUserName(loginForm);
                        break;
                    case EMAIL :
                        //邮箱登录
                        uid = LARService.loginByEmail(loginForm);
                        break;
                    case MOBILENUMBER :
                        //手机号登录
                        uid = LARService.loginByUserMobileNumber(loginForm);
                        break;
                    case QQ :
                        //QQ登录
                        uid = LARService.loginByUserQQ(loginForm);
                        break;
                    case WECHART :
                        //微信登录
                        uid = LARService.loginBeWechart(loginForm);
                        break;
                    case TENCENTWEIBO :
                        //腾讯微博登录
                        uid = LARService.loginByTencentWeibo(loginForm);
                        break;
                    case SINAWEIBO :
                        //新浪微博登录
                        uid = LARService.loginBySinaWeibo(loginForm);
                        break;
                    default:
                        throw new IllegalStateException("无法解析的登录方式: " + loginForm.getIdentityType());
                }
                //生成jwt令牌,返回到客户端
                Map<String,String> info = new HashMap<>();
                //基于工具类生成jwt令牌
                String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), String.valueOf(uid), null);
                info.put("token",jwt);
                System.out.println("token: "+jwt);
                return new Result(true, StatusCodeEnum.OK.getCode(),"登录成功",info);
            }catch (Exception e){
                return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
            }
        }

    }


    /**
     * @description:
     * @param registerForm
     * @return org.apei.bizcommon.entity.Result
     * @author apeiMark
     * @date 2024/7/16
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterForm registerForm){
        log.info("RegisterForm: "+registerForm);
        if(registerForm==null){
            return new Result(false, StatusCodeEnum.ERROR.getCode(),"注册表单为空，请稍后重试...");
        } else{
            try{
                Long uid;
                switch(registerForm.getIdentityType()){
                    case USERNAME :
                        //用户名注册
                        uid = LARService.registerByUserName(registerForm);
                        break;
                    case EMAIL :
                        //邮箱注册
                        uid = LARService.registerByEmail(registerForm);
                        break;
                    case MOBILENUMBER :
                        //手机号注册
                        uid = LARService.registerByUserMobileNumber(registerForm);
                        break;
                    case QQ :
                        //QQ注册
                        uid = LARService.registerByUserQQ(registerForm);
                        break;
                    case WECHART :
                        //微信注册
                        uid = LARService.registerBeWechart(registerForm);
                        break;
                    case TENCENTWEIBO :
                        //腾讯微博注册
                        uid = LARService.registerByTencentWeibo(registerForm);
                        break;
                    case SINAWEIBO :
                        //新浪微博注册
                        uid = LARService.registerBySinaWeibo(registerForm);
                        break;
                    default:
                        throw new IllegalStateException("无法解析的注册方式: " + registerForm.getIdentityType());
                }
                Map<String,String> info = new HashMap<>();
                info.put("uid", String.valueOf(uid));
                return new Result(true, StatusCodeEnum.OK.getCode(),"注册成功",info);
            }catch (Exception e){
                return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
            }
        }

    }

    @PostMapping("/info")
    public Result getUserInfo(@RequestBody UserInfoRequest userInfoRequest){
        try{
            String token = userInfoRequest.getToken();
            // 移除 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                log.info("token以Bearer 开头");
                token = token.substring(7);
            }
            Claims claims = JwtUtil.parseJWT(token);
            log.info("token: " + token);
            String uid = claims.getSubject();
            log.info("Parsed UID: " + uid);
            UserBaseVO data = LARService.getUserInfo(uid);
            return new Result(true, StatusCodeEnum.OK.getCode(),"获取个人信息成功",data);
        }catch (Exception e){
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    @PostMapping("/edit")
    public Result editUserInfo(@RequestBody UserBaseVO userBaseVO){
        try{
            LARService.editUserInfo(userBaseVO);
            return new Result(true, StatusCodeEnum.OK.getCode(),"编辑个人信息成功");
        }catch (Exception e){
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

}
