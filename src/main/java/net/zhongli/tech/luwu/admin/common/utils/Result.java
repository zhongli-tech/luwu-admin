package net.zhongli.tech.luwu.admin.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果封装
 * @author lk
 * @create 2020/12/10 3:28 下午
 **/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 5732769105467891202L;

    private Integer code;

    private String message;

    private T data;
}
