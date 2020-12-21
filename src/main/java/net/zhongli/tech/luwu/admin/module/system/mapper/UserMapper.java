package net.zhongli.tech.luwu.admin.module.system.mapper;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lk
 * @create 2020/12/16 4:32 下午
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity, Long> {

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
}
