package net.zhongli.tech.luwu.admin.module.demo.mapper;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.module.demo.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lk
 * @create 2020/12/10 4:20 下午
 **/
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity, Long> {
}
