package net.zhongli.tech.luwu.admin.core.security;

import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author OZY
 * @Date 2019/07/14 22:29
 * @Description 自定义登陆验证
 * @Version V1.0
 **/
@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登录验证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取输入username
        String username = authentication.getName();
        String rawPassword = (String) authentication.getCredentials();

        // 查询用户是否存在
        UserEntity userEntity = userService.findByUsernameOrEmail(username);

        if (null == userEntity) {
            // 用不不存在，提示账号或密码异常，防止扫用户
            throw new AuthenticationCredentialsNotFoundException(ServiceErrorEnum.SYSTEM_USER_LOGIN_ERROR.getMsg());
        } else if (userEntity.isEnabled()) {
            throw new DisabledException("该账户已被停用，请联系管理员");

        } else if (userEntity.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");

        } else if (userEntity.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");

        } else if (userEntity.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }

        // 验证密码
        if (!passwordEncoder.matches(rawPassword, userEntity.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException(ServiceErrorEnum.SYSTEM_USER_LOGIN_ERROR.getMsg());
        }

        //设置角色权限信息
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (null != userEntity.getRole() && null != userEntity.getRole().getPermissions()) {
            for (PermissionEntity resourceEntity : userEntity.getRole().getPermissions()) {
                // 资源key作为权限标识
                grantedAuthorities.add(new SimpleGrantedAuthority(resourceEntity.getPermissionKey()));
            }
        }

        userEntity.setAuthorities(grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userEntity, rawPassword, grantedAuthorities);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        // 确保authentication能转成该类
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
