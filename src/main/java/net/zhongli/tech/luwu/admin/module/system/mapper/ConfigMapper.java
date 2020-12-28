package net.zhongli.tech.luwu.admin.module.system.mapper;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.module.system.entity.ConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lk
 * @create 2020/12/28 3:25 下午
 **/
@Mapper
public interface ConfigMapper extends BaseMapper<ConfigEntity, Long> {
}
