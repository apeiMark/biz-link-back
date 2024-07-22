package org.apei.fileserver.vo.file;

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
     * 文档ID
     */
    private String id;
    /**
     * 文档名称
     */
    private String name;
    /**
     * 文档类型：PDF、TXT、PPT、DOCX、XLSX、DOC、PPTX、OTHERS
     */
    private String documentType;
    /**
     * 归属文档库ID
     */
    private String libraryId;
    /**
     * 创建时间
     */
    private String[] createTime;
}
