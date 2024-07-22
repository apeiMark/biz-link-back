package org.apei.fileserver.vo.response;

import lombok.Data;

/**
 * @Description 创建文档库
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Data
public class LibraryResponse {
    /**
     *  文档库ID
     */
    private String id;
    /**
    *  文档库标题
    */
    private String title;
    /**
    * 文档库描述
    */
    private String description;
    /**
    * 创建者ID
    */
    private String createUid;
}
