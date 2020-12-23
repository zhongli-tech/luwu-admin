package net.zhongli.tech.luwu.admin.common.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: OZY
 * @createTime: 2019-07-20 15:33
 * @description: 操作日志的操作类型枚举
 * @version: 1.0.0
 */
@Getter
@AllArgsConstructor
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperateLogTypes {

    SAVE("添加"),
    UPDATE("修改"),
    DELETE("删除"),
    FIND("查询"),
    EXECUTE("采集"),
    /**
     * 资源就是跳转，下载文件，对文件操作啥的，或者登陆登出、分配权限啥的
     */
    RESOURCE("资源");

    private String operateType;

    @JsonCreator
    public static OperateLogTypes nameOf(@JsonProperty("operateType") String operateType) {
        for (OperateLogTypes state : values()) {
            if (operateType.equals(state.getOperateType())) {
                return state;
            }
        }
        return null;
    }

}
