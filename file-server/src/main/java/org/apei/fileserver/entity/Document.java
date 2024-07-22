package org.apei.fileserver.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description 文档实体
 * @Author apeiMark
 * @Date 2024/7/22
 */
@Data
public class Document {
    /**
    * 文档ID
    */
    private long id;
    /**
    * 文档名称
    */
    private String name;
    /**
    * 文档大小/MB
    */
    private Integer documentSize;
    /**
    * 文档类型：PDF、TXT、PPT、DOCX、XLSX、DOC、PPTX、OTHERS
    */
    private String documentType;
    /**
    * 归属文档库ID
    */
    private long libraryId;
    /**
    * 文档下载地址
    */
    private String src;
    /**
    * 创建时间
    */
    private Timestamp createTime;
}
