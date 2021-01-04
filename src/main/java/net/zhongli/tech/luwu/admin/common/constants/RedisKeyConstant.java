package net.zhongli.tech.luwu.admin.common.constants;

/**
 * redis key 常用变量
 * @author lk
 * @create 2020/12/14 11:19 上午
 **/
public interface RedisKeyConstant {

    /**
     * redis 二级缓存 key 策略
     */
    String MYBATIS_REDIS_CACHE = "MYBATIS_REDIS_CACHE_%s_%s";

    /**
     * 系统注册的邮件验证码
     */
    String SYSTEM_REGISTER_EMAIL_CODE = "SYSTEM_REGISTER_EMAIL_CODE:%s";

    /**
     * 记录 email 发送验证码的 ip 地址
     */
    String SYSTEM_REGISTER_EMAIL_IP = "SYSTEM_REGISTER_EMAIL_IP:%s";

}
