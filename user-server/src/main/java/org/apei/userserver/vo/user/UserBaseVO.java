package org.apei.userserver.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description
 * @Author apeiMark
 * @Date 2024/7/20
 */
@Data
public class UserBaseVO {
    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private String uid;
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
    private String birthday;
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
