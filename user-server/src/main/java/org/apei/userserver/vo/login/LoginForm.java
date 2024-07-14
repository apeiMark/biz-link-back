package org.apei.userserver.vo.login;

import lombok.Data;

/**
 * @Description 登录表单
 * @Author apeiMark
 * @Date 2024/7/12
 */
@Data
public class LoginForm {
    /**
    * 登录方式：1用户名 2邮箱 3手机号 4qq 5微信 6腾讯微博 7新浪微博
    */
    private int identityType;
    /**
     *  手机号 邮箱 用户名或第三方应用的唯一标识
     */
    private String identifier;
    /**
    * 密码凭证(站内的保存密码，站外的不保存或保存token)
    */
    private String certificate;
}
