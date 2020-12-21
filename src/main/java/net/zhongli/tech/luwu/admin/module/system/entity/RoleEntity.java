package net.zhongli.tech.luwu.admin.module.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @author LK
 * @create 2020/3/18 17:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleEntity extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 6617262125208643905L;
    /**
     * 角色 key
     */
    @NotBlank(message = "角色标识不能为空")
    private String roleKey;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 描述
     */
    private String introduction;

    /**
     * 所属资源
     */
    private List<PermissionEntity> permissions;


    @Override
    public String getAuthority() {
        return roleKey;
    }
}
