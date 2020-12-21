package net.zhongli.tech.luwu.admin.module.system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: OZY
 * @createTime: 2020-03-27 11:43
 * @description: 权限类型枚举
 * @version: 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {

    ROUTES("路由"),

    REQUEST("请求"),

    DATA("数据");

    private String name;

    @JsonCreator
    public static PermissionTypeEnum nameOf(@JsonProperty("name") String name) {
        for (PermissionTypeEnum state : values()) {
            if (name.equals(state.getName())) {
                return state;
            }
        }
        return null;
    }
}
