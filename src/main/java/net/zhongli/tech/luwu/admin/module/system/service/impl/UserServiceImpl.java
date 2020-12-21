package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.common.utils.BPwdEncoderUtil;
import net.zhongli.tech.luwu.admin.module.system.dto.RegisterDTO;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.UserMapper;
import net.zhongli.tech.luwu.admin.module.system.service.RoleService;
import net.zhongli.tech.luwu.admin.module.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lk
 * @create 2020/12/16 4:37 下午
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleService roleService;

    @Resource
    public void setBaseMapper(BaseMapper<UserEntity, Long> baseMapper) {
        super.setBaseMapper(userMapper);
    }

    @Override
    public UserEntity findByUsernameOrEmail(String username) {
        UserEntity userEntity = this.userMapper.findByUsernameOrEmail(username);
        if (null != userEntity && null != userEntity.getRole()) {
            userEntity.setRole(this.roleService.findById(userEntity.getRole().getId()));
        }
        return userEntity;
    }

    /**
     * 保存用户角色关联表
     *
     * @param userEntity 用户实体
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int saveUserRole(UserEntity userEntity) {
        return this.userMapper.saveUserRole(userEntity);
    }

    /**
     * 删除用户角色关联表
     *
     * @param userId 用户 id
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int deleteUserRole(Long userId) {
        return this.userMapper.deleteUserRole(userId);
    }

    /**
     * 注册方法
     *
     * @param registerDTO
     * @return
     */
    @Override
    public int register(RegisterDTO registerDTO) {
        // @TODO 验证是否开启注册功能
        // 检查两次输入的密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getRePassword())) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_USER_PASSWORD);
        }
        // 检查账号或 email 是否有重复
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("username", registerDTO.getAccount());
        List<UserEntity> tempUserList = this.userMapper.findBy(queryMap);
        if (!tempUserList.isEmpty()) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_USER_EXIST);
        }
        queryMap.clear();
        queryMap.put("email", registerDTO.getEmail());
        tempUserList = this.userMapper.findBy(queryMap);
        if (!tempUserList.isEmpty()) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_USER_EXIST);
        }
        // @TODO 是否需要验证验证码
        UserEntity userEntity = new UserEntity();
        // 昵称默认为账号名，不唯一
        userEntity.setNickname(registerDTO.getAccount());
        userEntity.setUsername(registerDTO.getAccount());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(BPwdEncoderUtil.BCryptPassword(registerDTO.getPassword()));
        int result = 0;
        result = this.userMapper.save(userEntity);
        // @TODO 是否设置注册后的默认角色
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("username", s);
        List<UserEntity> userEntityList = this.userMapper.findBy(queryMap);
        if (!userEntityList.isEmpty()) {
            for (UserEntity userEntity : userEntityList) {
                if (null != userEntity.getRole()) {
                    userEntity.setRole(this.roleService.findById(userEntity.getRole().getId()));
                }
            }
            return userEntityList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int update(UserEntity userEntity) {
        int result = this.userMapper.update(userEntity);
        // 删除用户角色关系
        this.userMapper.deleteUserRole(userEntity.getId());
        // 重新插入用户角色关系
        if (null != userEntity.getRole()) {
            this.userMapper.saveUserRole(userEntity);
        }
        return result;
    }
}
