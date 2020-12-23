package net.zhongli.tech.luwu.admin.module.system.entity;


import lombok.Data;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;
import net.zhongli.tech.luwu.admin.common.enums.OperateLogTypes;

/**
 * @author LK
 * @create 2020/4/28 14:41
 **/
@Data
public class OperateLogEntity extends BaseEntity {

    /**
     * 操作账号
     */
    private String username;

    /**
     * 注解上的描述
     */
    private String description;

    /**
     * 注解上的类型
     */
    private OperateLogTypes operateLogTypes;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * ip 地址
     */
    private String ip;
}
