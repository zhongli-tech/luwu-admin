package net.zhongli.tech.luwu.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常错误枚举
 * @author lk
 * @create 2020/12/10 2:46 下午
 **/
@Getter
@AllArgsConstructor
public enum ServiceErrorEnum {

    SYSTEM_DEFAULT_SUCCESS(200, "操作成功"),
    SYSTEM_DEFAULT_FAIL(500, "操作失败");

    private final Integer code;

    private final String msg;

    public static ServiceErrorEnum codeOf(Integer code) {
        for (ServiceErrorEnum state : values()) {
            if (state.getCode().equals(code)) {
                return state;
            }
        }
        return null;
    }
}
