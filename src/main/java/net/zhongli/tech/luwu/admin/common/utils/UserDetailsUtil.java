package net.zhongli.tech.luwu.admin.common.utils;

import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;

/**
 * 获取授权用户信息
 * @author lk
 * @create 2020/12/14 2:17 下午
 **/
public class UserDetailsUtil {

    /**
     * 获取用户信息
     * @return
     */
    /*public static UserEntity getPrincipal() {
        UserEntity user = null;
        SecurityContext  securityContext = SecurityContextHolder.getContext();
        boolean userExist = null != securityContext && null != securityContext.getAuthentication()
                && null != securityContext.getAuthentication().getDetails();
        if (userExist) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserEntity) {
                user = (UserEntity) principal;
            }
        }
        return user;
    }*/

    public static UserEntity getPrincipal() {
        return null;
    }
}
