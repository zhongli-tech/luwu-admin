package net.zhongli.tech.luwu.admin.module.system.service.impl;

import net.zhongli.tech.luwu.admin.common.base.BaseMapper;
import net.zhongli.tech.luwu.admin.common.base.BaseServiceImpl;
import net.zhongli.tech.luwu.admin.common.constants.CommonConstant;
import net.zhongli.tech.luwu.admin.common.constants.RedisKeyConstant;
import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.common.utils.BPwdEncoderUtil;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import net.zhongli.tech.luwu.admin.module.system.constant.EmailContentConstant;
import net.zhongli.tech.luwu.admin.module.system.dto.RegisterDTO;
import net.zhongli.tech.luwu.admin.module.system.entity.ConfigEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.mapper.UserMapper;
import net.zhongli.tech.luwu.admin.module.system.service.ConfigService;
import net.zhongli.tech.luwu.admin.module.system.service.RoleService;
import net.zhongli.tech.luwu.admin.module.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private ConfigService configService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
        List<ConfigEntity> configEntityList = this.configService.findBy(null);
        if (configEntityList.isEmpty()) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_COMMON_FAIL);
        }
        // 验证是否开启注册功能
        if (!configEntityList.get(0).getEnableRegister()) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_DISABLE);
        }
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
        // 是否需要验证验证码
        if (configEntityList.get(0).getEnableCode()) {
            if (null == registerDTO.getCode()) {
                throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_ERROR);
            }
            // 验证验证码是否正确
            String codeKey = String.format(RedisKeyConstant.SYSTEM_REGISTER_EMAIL_CODE, registerDTO.getEmail());
            Boolean codeCheck = this.redisTemplate.hasKey(codeKey);
            if (null == codeCheck || !codeCheck) {
                throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_ERROR);
            }
            Integer code = (Integer) this.redisTemplate.opsForValue().get(codeKey);
            if (null == code) {
                throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_ERROR);
            }
            if (!code.equals(registerDTO.getCode())) {
                throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_FAIL);
            }
            // 删除验证码
            this.redisTemplate.delete(codeKey);
        }
        UserEntity userEntity = new UserEntity();
        // 昵称默认为账号名，不唯一
        userEntity.setNickname(registerDTO.getAccount());
        userEntity.setUsername(registerDTO.getAccount());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(BPwdEncoderUtil.BCryptPassword(registerDTO.getPassword()));
        int result = 0;
        result = this.userMapper.save(userEntity);
        // 设置注册后的默认角色
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(CommonConstant.SYSTEM_DEFAULT_ROLE);
        userEntity.setRole(roleEntity);
        // 插入角色关系
        this.userMapper.saveUserRole(userEntity);
        return result;
    }

    /**
     * 获取邮件验证码
     *
     * @param email
     * @return
     */
    @Override
    public int createEmailCode(String email) {
        // 验证是否重复获取验证码
        Boolean codeCheck = this.redisTemplate.hasKey(String.format(RedisKeyConstant.SYSTEM_REGISTER_EMAIL_CODE, email));
        if (null != codeCheck && codeCheck) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_ALREADY);
        }
        // 记录调用发送邮件验证的 ip 地址，防止同一 ip 地址发送多个邮箱验证码，同样为了防止邮件轰炸
        String ip = HttpUtils.getIpAddress();
        if (null != ip) {
            this.ipSafe(ip, email);
        }
        // 获取 4 位随机数
        long code = Math.round((Math.random() * 9 + 1) * 1000);
        // 插入缓存并且保存 30 分钟
        this.redisTemplate.opsForValue().set(String.format(RedisKeyConstant.SYSTEM_REGISTER_EMAIL_CODE, email), code, 30, TimeUnit.MINUTES);
        // 发送邮件
        this.sendEmailCode(email, (int) code);
        return (int) code;
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
        if (null == userEntity.getRole()) {
            // 如果没有传入，默认为普通角色
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(CommonConstant.SYSTEM_DEFAULT_ROLE);
            userEntity.setRole(roleEntity);

        }
        this.userMapper.saveUserRole(userEntity);
        return result;
    }

    /**
     * ip 地址限制发送验证码
     * @param ip
     * @param email
     */
    private void ipSafe(String ip, String email) {
        String ipKey = String.format(RedisKeyConstant.SYSTEM_REGISTER_EMAIL_IP, ip);
        Boolean existKey = redisTemplate.hasKey(ipKey);
        if (null != existKey && existKey) {
            String existEmail = (String) redisTemplate.opsForValue().get(ipKey);
            if (null != existEmail && !existEmail.equals(email)) {
                // 同一 ip 发送多个邮件
                throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_CODE_ERROR);
            }
        } else {
            // 限制 10 分钟
            redisTemplate.opsForValue().set(ipKey, email, 10, TimeUnit.MINUTES);
        }
    }

    private void sendEmailCode(String email, Integer code) {
        List<ConfigEntity> configEntityList = this.configService.findBy(null);
        if (configEntityList.isEmpty()) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_COMMON_FAIL);
        }
        // 获取系统配置
        ConfigEntity configEntity = configEntityList.get(0);
        // 没有配置邮件信息报错
        if (StringUtils.isEmpty(configEntity.getEmailAccount()) || StringUtils.isEmpty(configEntity.getEmailHost())
            || StringUtils.isEmpty(configEntity.getEmailPassword())) {
            throw new ServiceException(ServiceErrorEnum.SYSTEM_REGISTER_EMAIL_FAIL);
        }
        // 生成上下文
        String content = String.format(EmailContentConstant.SEND_EMAIL_CODE, configEntity.getSystemName(), code);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configEntity.getEmailHost());
        mailSender.setUsername(configEntity.getEmailAccount());
        mailSender.setPassword(configEntity.getEmailPassword());
        // 构建邮件信息并且发送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(configEntity.getEmailAccount());
        message.setTo(email);
        message.setSubject("账号验证码");
        message.setText(content);
        mailSender.send(message);
    }
}
