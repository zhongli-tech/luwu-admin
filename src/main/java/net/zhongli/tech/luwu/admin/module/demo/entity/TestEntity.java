package net.zhongli.tech.luwu.admin.module.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;

/**
 * @author lk
 * @create 2020/12/11 10:35 上午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TestEntity extends BaseEntity {

    private static final long serialVersionUID = 1826894096966988684L;

    private String testName;

}
