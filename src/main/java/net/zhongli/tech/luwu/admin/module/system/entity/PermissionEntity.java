package net.zhongli.tech.luwu.admin.module.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;
import net.zhongli.tech.luwu.admin.module.system.enums.PermissionTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author: OZY
 * @createTime: 2020-03-27 11:19
 * @description: 权限表
 * @version: 1.0.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionEntity extends BaseEntity {

    /**
     * 所属父权限
     */
    private PermissionEntity parent;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    private String permissionKey;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String permissionName;

    /**
     * 权限类型
     * {@link PermissionTypeEnum}
     */
    @NotNull(message = "权限类型不能为空")
    private Integer permissionType;

    /**
     * 请求方法类型
     */
    @NotBlank(message = "请求方法类型不能为空")
    private String requestType;

    /**
     * 路由地址
     */
    @NotBlank(message = "路由地址不能为空")
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序序号
     */
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    /**
     * 是否隐藏
     * true:隐藏
     * false:显示
     */
    private Boolean hidden;

    /**
     * 子节点个数只有一个的时候是否显示根节点
     * true:显示
     * false:不显示
     */
    private Boolean alwaysShow;

    /**
     * 是否外链
     * true:是
     * false:不是
     */
    private Boolean externalLinks;

}
