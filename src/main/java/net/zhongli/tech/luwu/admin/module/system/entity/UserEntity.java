package net.zhongli.tech.luwu.admin.module.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.zhongli.tech.luwu.admin.common.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * 系统用户
 * @author lk
 * @create 2020/12/14 2:28 下午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 2325817827537546584L;

    /**
     * 用户名
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, message = "账号不能小于 {min} 位")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 5, message = "密码不能小于 {min} 位")
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 介绍、描述
     */
    private String introduction;

    /**
     * 电话
     */
    @Min(value = 11, message = "请使用正确的电话格式")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "请使用正确的邮件格式")
    private String email;

    /**
     * 昵称，用于显示
     */
    private String nickname;

    /**
     * 账号是否被锁定
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean accountNonLocked = false;

    /**
     * 启用账号
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean enabled = true;

    /**
     * 用户角色
     */
    private RoleEntity role;

    @JSONField(serialize = false)
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 账号是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * 凭证是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
