package org.apei.userserver.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description 用户基本信息表
 * @Author apeiMark
 * @Date 2024/7/12
 */
@Data
public class UserBase {
    /**
    * 用户ID
    */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;
    /**
    *用户昵称
    */
    private String nickName;
    /**
    *用户性别 0-female 1-male
    */
    private Integer gender;
    /**
    *用户生日
    */
    private Data birthday;
    /**
    *用户个人签名
    */
    private String signature;
    /**
    *手机号（唯一）
    */
    private String mobile;
    /**
    *邮箱（唯一）
    */
    private String email;
    /**
    *头像
    */
    private String avatar;
    /**
    *创建时间
    */
    private Timestamp createTime;
    /**
    *更新时间
    */
    private Timestamp updateTime;
}
