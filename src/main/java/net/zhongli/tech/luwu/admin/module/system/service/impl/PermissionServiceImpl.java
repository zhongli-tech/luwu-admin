package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.PermissionMapper;
import net.zhongli.tech.luwu.admin.module.system.service.PermissionService;
import net.zhongli.tech.luwu.admin.module.system.vo.PermissionTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 列表树
     *
     * @param parameter
     * @return
     */
    @Override
    public List<PermissionTreeVO> queryTreeList(Map<String, Object> parameter) {
        // 从根资源开始拼接
        parameter.put("parentId", null);
        List<PermissionEntity> permissionEntityList = this.permissionMapper.queryList(parameter);
        // 处理子节点
        return this.findChildrenPermission(permissionEntityList, parameter);
    }

    /**
     * 递归拼接子节点
     * @param permissionList
     * @param parameters
     * @return
     */
    private List<PermissionTreeVO> findChildrenPermission(List<PermissionEntity> permissionList, Map<String, Object> parameters) {
        // 封装数据
        List<PermissionTreeVO> voList = new ArrayList<>(permissionList.size());
        PermissionTreeVO vo;
        for (PermissionEntity permissionEntity : permissionList) {
            vo = new PermissionTreeVO();
            BeanUtils.copyProperties(permissionEntity, vo);
            voList.add(vo);
        }
        // 查询是否存在子菜单
        for (PermissionTreeVO permissionTreeVO : voList) {
            parameters.put("parentId", permissionTreeVO.getId());
            List<PermissionEntity> childList = this.permissionMapper.queryList(parameters);
            if (!childList.isEmpty()) {
                permissionTreeVO.setChildren(this.findChildrenPermission(childList, parameters));
            }
        }
        return voList;
    }
}
