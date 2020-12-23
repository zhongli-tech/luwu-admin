package net.zhongli.tech.luwu.admin.config;

import net.zhongli.tech.luwu.admin.core.security.LoginValidateAuthenticationProvider;
import net.zhongli.tech.luwu.admin.core.security.handler.LoginFailureHandler;
import net.zhongli.tech.luwu.admin.core.security.handler.LoginSuccessHandler;
import net.zhongli.tech.luwu.admin.core.security.handler.LogoutHandler;
import net.zhongli.tech.luwu.admin.core.security.handler.PerAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author OZY
 * @Date 2019/07/09 17:44
 * @Description security配置
 * @Version V1.0
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {

    @Resource(name = "druidDataSource")
    private DataSource druidDataSource;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private LogoutHandler logoutHandler;

    @Resource
    private PerAccessDeniedHandler perAccessDeniedHandler;

    @Resource
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Resource
    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

    @Resource
    private FindByIndexNameSessionRepository<S> sessionRepository;


    /**
     * 权限核心配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //基础设置
        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin() //登录表单
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                .and()
                .logout() //登出
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(perAccessDeniedHandler)
                .and()
                .rememberMe()
                .tokenValiditySeconds(12 * 60 * 60) //记住登录12小时
                .tokenRepository(persistentTokenRepository());

        http.sessionManagement()
                .maximumSessions(1)//同时最大在线上
                .maxSessionsPreventsLogin(false)//挤出旧用户
                .sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(sessionInformationExpiredStrategy);//session过期处理

        // 关闭csrf跨域攻击防御
        http.csrf().disable();

        // 允许iframe嵌套
        http.headers().frameOptions().disable();
    }

    /**
     * 配置不拦截规则
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // 所有对外的接口
                "/api/**",
                // 静态资源
                "/static/**",
                // swagger
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                // 错误页面
                "/error/**"
        );
    }

    /**
     * 配置自定义登录认证
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginValidateAuthenticationProvider);
    }


    /**
     * BCrypt加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 记住我功能，持久化的cookie token服务
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(druidDataSource);
        //启动时创建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    /**
     * 注册sessionRegistry Bean
     * @return
     */
    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }


}
