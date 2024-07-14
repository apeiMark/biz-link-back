package org.apei.bizcommon.entity;

import lombok.Data;

@Data
public class Result {

    private boolean flag;
    private Integer code;
    private String msg;
    private Object data;

    public Result() {
    }

    //增删改
    public Result(boolean flag, Integer code, String msg) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    //
    public Result(boolean flag, Integer code, String msg, Object data) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}

