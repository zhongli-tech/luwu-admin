package net.zhongli.tech.luwu.admin.module.system.service;

import net.zhongli.tech.luwu.admin.common.base.BaseService;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;
import net.zhongli.tech.luwu.admin.module.system.vo.PermissionTreeVO;

import java.util.List;
import java.util.Map;

/**
 * @author lk
 * @create 2020/12/15 2:49 下午
 **/
public interface PermissionService extends BaseService<PermissionEntity, Long> {

    /**
     * 列表树
     * @return
     */
    List<PermissionTreeVO> queryTreeList(Map<String, Object> parameter);
}
