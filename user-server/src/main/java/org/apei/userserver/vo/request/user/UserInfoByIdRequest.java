package org.apei.userserver.vo.request.user;

import lombok.Data;

/**
 * @Description 请求用户个人信息
 * @Author apeiMark
 * @Date 2024/7/20
 */
@Data
public class UserInfoByIdRequest {
    /**
    * 用户uid
    */
    private String uid;
}
