package net.zhongli.tech.luwu.admin.module.system.service;

import net.zhongli.tech.luwu.admin.common.base.BaseService;
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;

/**
 * @author lk
 * @create 2020/12/16 4:34 下午
 **/
public interface RoleService extends BaseService<RoleEntity, Long> {

    /**
     * 插入角色资源关系表
     * @param roleEntity
     * @return
     */
    int saveRolePermission(RoleEntity roleEntity);

    /**
     * 删除角色资源关系表
     * @param roleId
     * @return
     */
    int deleteUserRole(Long roleId);
}
