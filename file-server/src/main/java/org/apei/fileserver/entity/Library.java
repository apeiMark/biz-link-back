package org.apei.fileserver.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description 文档库
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Data
public class Library {
    /**
    * 文档库ID
    */
    private Long id;
    /**
    * 文档库标题
    */
    private String title;
    /**
    * 文档库描述
    */
    private String description;
    /**
    * 文档库创建者ID
    */
    private Long createrUid;
    /**
    * 文档库总文档数目
    */
    private Integer total;
    /**
    * 文档库参与人数
    */
    private Integer participants;
    /**
    * 文档创建时间
    */
    private Timestamp createTime;
}
