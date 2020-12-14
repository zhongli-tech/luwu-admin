package net.zhongli.tech.luwu.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局返回结果信息
 * @author lk
 * @create 2020/12/10 18:03 下午
 **/
@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    FAIL(500, "系统异常,请稍后再试"),
    ERROR(400, "系统错误"),
    NOT_FOUND(404, "找不到资源")
    ;

    private final Integer code;

    private final String msg;
}
