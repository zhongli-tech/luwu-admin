package net.zhongli.tech.luwu.admin.module.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;

/**
 * 登录入职
 * @author lk
 * @create 2020/12/16 3:17 下午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginlogEntity extends BaseEntity {

    private static final long serialVersionUID = 2383461758622783012L;

    private String ip;

    private UserEntity userEntity;
}
