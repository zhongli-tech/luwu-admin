package net.zhongli.tech.luwu.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author OZY
 * @Date 2019-08-23 15:34
 * @Description spring session配置
 * @Version V1.0
 **/

@Configuration
// session超时时间 4小时
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 4)
public class HttpSessionConfig {



}
