package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.RoleMapper;
import net.zhongli.tech.luwu.admin.module.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lk
 * @create 2020/12/17 2:32 下午
 **/
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity, Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    public void setBaseMapper(BaseMapper<RoleEntity, Long> baseMapper) {
        super.setBaseMapper(roleMapper);
    }

    /**
     * 插入角色资源关系表
     *
     * @param roleEntity
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int saveRolePermission(RoleEntity roleEntity) {
        return this.roleMapper.saveRolePermission(roleEntity);
    }

    /**
     * 删除角色资源关系表
     *
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int deleteUserRole(Long roleId) {
        return this.roleMapper.deleteUserRole(roleId);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int update(RoleEntity roleEntity) {
        int result = this.roleMapper.update(roleEntity);
        // 删除角色资源关系
        this.roleMapper.deleteUserRole(roleEntity.getId());
        // 重新插入角色资源关系
        if (null != roleEntity.getPermissions() && !roleEntity.getPermissions().isEmpty()) {
            this.roleMapper.saveRolePermission(roleEntity);
        }
        return result;
    }
}
