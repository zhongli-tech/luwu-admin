package net.zhongli.tech.luwu.admin.module.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;

/**
 * 系统配置实体
 * @author lk
 * @create 2020/12/25 10:34 上午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigEntity extends BaseEntity {

    private static final long serialVersionUID = 5223868087095911883L;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 版本号
     */
    private String systemVersion;

    /**
     * 备案号
     */
    private String systemRecord;

    /**
     * 系统邮件
     */
    private String emailAccount;

    /**
     * 邮件密码
     */
    private String emailPassword;

    /**
     * 邮件发送服务器
     */
    private String emailHost;

    /**
     * 是否启用注册功能
     */
    private Boolean enableRegister;

    /**
     * 是否启用邮件注册验证码功能
     */
    private Boolean enableCode;

}
