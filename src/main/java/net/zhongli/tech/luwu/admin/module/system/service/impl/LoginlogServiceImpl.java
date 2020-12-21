package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.module.system.entity.LoginlogEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.LoginlogMapper;
import net.zhongli.tech.luwu.admin.module.system.service.LoginlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lk
 * @create 2020/12/17 2:37 下午
 **/
@Service
public class LoginlogServiceImpl extends BaseServiceImpl<LoginlogEntity, Long> implements LoginlogService {

    @Resource
    private LoginlogMapper loginlogMapper;

    @Resource
    public void setBaseMapper(BaseMapper<LoginlogEntity, Long> baseMapper) {
        super.setBaseMapper(loginlogMapper);
    }
}
