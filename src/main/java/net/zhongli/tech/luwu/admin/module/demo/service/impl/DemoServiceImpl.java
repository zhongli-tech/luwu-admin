package net.zhongli.tech.luwu.admin.module.demo.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.module.demo.entity.DemoEntity;
import net.zhongli.tech.luwu.admin.module.demo.mapper.DemoMapper;
import net.zhongli.tech.luwu.admin.module.demo.service.DemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lk
 * @create 2020/12/10 4:21 下午
 **/
@Service
public class DemoServiceImpl extends BaseServiceImpl<DemoEntity, Long> implements DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Resource
    public void setBaseMapper(BaseMapper<DemoEntity, Long> baseMapper) {
        super.setBaseMapper(demoMapper);
    }
}
