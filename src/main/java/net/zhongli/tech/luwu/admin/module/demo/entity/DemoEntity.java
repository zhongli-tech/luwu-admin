package net.zhongli.tech.luwu.admin.module.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;

import java.util.List;

/**
 * @author lk
 * @create 2020/12/10 4:18 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoEntity extends BaseEntity {

    private static final long serialVersionUID = 582122934066986302L;

    private String demoName;

    private List<TestEntity> tests;
}
