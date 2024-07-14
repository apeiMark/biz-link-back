package org.apei.userserver.entity.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description 用户授权实体类
 * @Author apeiMark
 * @Date 2024/7/12
 */
@Data
public class UserAuth {
    /**
    * 表唯一主键
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long uid;
    /**
    * 1用户名 2邮箱 3手机号 4qq 5微信 6腾讯微博 7新浪微博
    */
    private Integer identityType;
    /**
    *手机号 邮箱 用户名或第三方应用的唯一标识
    */
    private String identifier;
    /**
    *密码凭证(站内的保存密码，站外的不保存或保存token)
    */
    private String certificate;
    /**
    * 0-待审核 1-审核通过 2-审核驳回
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Timestamp createTime;
    /**
    * 更新时间
    */
    private Timestamp updateTime;
}
