package net.zhongli.tech.luwu.admin.module.system.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 注册 dto
 * @author lk
 * @create 2020/12/18 4:20 下午
 **/
@Data
public class RegisterDTO {

    /**
     * 登录账号
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 10, message = "账号长度必须在 {min} - {max} 之间")
    private String account;

    /**
     * 邮箱
     */
    @Email(message = "请输入正确的邮箱地址")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不能小于 {min} 位")
    private String password;

    /**
     * 确认密码
     */
    private String rePassword;

    /**
     * 验证码
     */
    private Integer code;
}
