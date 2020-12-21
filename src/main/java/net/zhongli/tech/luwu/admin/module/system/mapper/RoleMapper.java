package net.zhongli.tech.luwu.admin.module.system.mapper;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lk
 * @create 2020/12/16 4:32 下午
 **/
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity, Long> {

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
