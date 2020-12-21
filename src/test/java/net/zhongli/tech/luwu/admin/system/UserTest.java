package net.zhongli.tech.luwu.admin.system;

import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.module.system.dto.RegisterDTO;
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.service.RoleService;
import net.zhongli.tech.luwu.admin.module.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lk
 * @create 2020/12/18 4:49 下午
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTest {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Test
    public void addUserTest() {
        RegisterDTO dto = new RegisterDTO();
        dto.setAccount("Luwu");
        dto.setEmail("official@zhongli-tech.net");
        dto.setPassword("123456");
        dto.setRePassword("123456");
        int i = this.userService.register(dto);
        log.info("插入账号成功，i={}", i);
    }

    @Test
    public void addRoleTest() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("超级管理员");
        roleEntity.setRoleKey("admin");
        roleEntity.setIntroduction("系统权限最高的管理账号");
        this.roleService.save(roleEntity);
    }

    @Test
    public void addUserRoleTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        userEntity.setRole(roleEntity);
        this.userService.saveUserRole(userEntity);
    }
}
