package net.zhongli.tech.luwu.admin.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author LK
 * @create 2020/3/19 14:59
 **/
public class BPwdEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 用BCryptPasswordEncoder
     * @param password
     * @return
     */
    public static String  BCryptPassword(String password){
        return encoder.encode(password);
    }

    /**
     *
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }
}
