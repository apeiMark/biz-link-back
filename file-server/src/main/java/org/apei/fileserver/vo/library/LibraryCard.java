package org.apei.fileserver.vo.library;

/**
 * @Description 文档库卡片
 * @Author apeiMark
 * @Date 2024/7/21
 */
@lombok.Data
public class LibraryCard {
    /**
    * 文档库ID
    */
    private String id;
    /**
    * 文档库标题
    */
    private String title;
    /**
    * 文档库创建时间
    */
    private String createTime;
    /**
    * 文档库描述
    */
    private String description;
    /**
    * 文档库数据
    */
    private Data data;
}
@lombok.Data
class Data {
    /**
    * 标签
    */
    private String lable;
    /**
    * 值
    */
    private String value;
}
