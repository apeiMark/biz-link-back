package org.apei.userserver.vo.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description UserAuthVO
 * @Author apeiMark
 * @Date 2024/7/13
 */
@Data
public class UserAuthVO {
    /**
     * 表唯一主键
     */
    private long id;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 1用户名 2邮箱 3手机号 4qq 5微信 6腾讯微博 7新浪微博
     */
    private int identityType;
    /**
     *手机号 邮箱 用户名或第三方应用的唯一标识
     */
    private String identifier;
    /**
     * 0-待审核 1-审核通过 2-审核驳回
     */
    private int status;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
