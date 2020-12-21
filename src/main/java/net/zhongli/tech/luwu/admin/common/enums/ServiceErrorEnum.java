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
    SYSTEM_DEFAULT_FAIL(500, "操作失败"),
    SYSTEM_NO_AUTHORITY(403, "无权限"),
    SYSTEM_SESSION_KICK(203, "您已被管理员踢出，请重新登录"),
    SYSTEM_USER_LOGIN_ERROR(10001, "账号或密码异常，请重试"),
    SYSTEM_USER_PASSWORD(10002, "两次输入的密码不一致"),
    SYSTEM_USER_EXIST(10003, "账号已存在")
    ;

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
