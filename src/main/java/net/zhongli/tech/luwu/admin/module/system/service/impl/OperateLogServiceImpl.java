package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.module.system.entity.OperateLogEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.OperateLogMapper;
import net.zhongli.tech.luwu.admin.module.system.service.OperateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LK
 * @create 2020/4/28 14:38
 **/
@Service
public class OperateLogServiceImpl extends BaseServiceImpl<OperateLogEntity, Long> implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Resource
    public void setBaseMapper(BaseMapper<OperateLogEntity, Long> baseMapper) {
        super.setBaseMapper(operateLogMapper);
    }
}
