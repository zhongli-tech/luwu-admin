package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.PermissionMapper;
import net.zhongli.tech.luwu.admin.module.system.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lk
 * @create 2020/12/17 2:34 下午
 **/
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionEntity, Long> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    public void setBaseMapper(BaseMapper<PermissionEntity, Long> baseMapper) {
        super.setBaseMapper(permissionMapper);
    }
}
