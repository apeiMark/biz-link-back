package org.apei.fileserver.vo.library;

import lombok.Data;

/**
 * @Description 下拉框选择器
 * @Author apeiMark
 * @Date 2024/7/22
 */
@Data
public class SelectOptionData {
    /**
    * 显示文本
    */
    private String label;
    /**
    * 值
    */
    private String value;
}
