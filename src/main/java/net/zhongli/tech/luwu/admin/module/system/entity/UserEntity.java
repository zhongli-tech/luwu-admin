package net.zhongli.tech.luwu.admin.module.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;

/**
 * 系统用户
 * @author lk
 * @create 2020/12/14 2:28 下午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 2325817827537546584L;

    /**
     * 用户名
     */
    private String username;

}
