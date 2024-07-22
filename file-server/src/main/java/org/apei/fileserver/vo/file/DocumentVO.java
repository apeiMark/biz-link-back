package org.apei.fileserver.vo.file;

import lombok.Data;

/**
 * @Description 文档实体
 * @Author apeiMark
 * @Date 2024/7/22
 */
@Data
public class DocumentVO {
    /**
    * 文档ID
    */
    private String id;
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
    private String libraryId;
    /**
    * 文档下载地址
    */
    private String src;
    /**
    * 创建时间
    */
    private String createTime;
}
