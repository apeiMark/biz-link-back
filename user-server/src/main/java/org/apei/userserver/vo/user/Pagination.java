package org.apei.userserver.vo.user;

import lombok.Data;

/**
 * @Description 分页参数
 * @Author apeiMark
 * @Date 2024/7/13
 */
@Data
public class Pagination {
    /**
    *当前页号
    */
    private int current;
    /**
    *页大小
    */
    private int pageSize;
    /**
    *数据总数
    */
    private int total;
    /**
     *ID
     */
    private String uid;
    /**
    * 登录方式
    */
    private String identityType;
    /**
    * 登录账号
    */
    private String identifier;
    /**
    * 密码凭证
    */
    private String certificate;
    /**
     * 状态
     */
    private String status;
    /**
    *创建时间范围
    */
    private String[] createTime;
}
