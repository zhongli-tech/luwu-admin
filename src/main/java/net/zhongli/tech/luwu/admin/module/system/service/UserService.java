
package net.zhongli.tech.luwu.admin.module.system.service;

import net.zhongli.tech.luwu.admin.common.base.BaseService;
import net.zhongli.tech.luwu.admin.module.system.dto.RegisterDTO;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * @author lk
 * @create 2020/12/15 2:43 下午
 **/

public interface UserService extends BaseService<UserEntity, Long>, UserDetailsService {

    /**
     * 通过账户名或者邮箱找到账户
     * @param username 用户名/email
     * @return
     */
    UserEntity findByUsernameOrEmail(String username);

    /**
     * 保存用户角色关联表
     * @param userEntity 用户实体
     * @return
     */
    int saveUserRole(UserEntity userEntity);

    /**
     * 删除用户角色关联表
     * @param userId 用户 id
     * @return
     */
    int deleteUserRole(Long userId);

    /**
     * 注册方法
     * @param registerDTO
     * @return
     */
    int register(RegisterDTO registerDTO);

    /**
     * 获取邮件验证码
     * @param email
     * @return
     */
    int createEmailCode(String email);
}

