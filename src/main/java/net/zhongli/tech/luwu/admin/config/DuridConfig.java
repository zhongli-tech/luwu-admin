
package net.zhongli.tech.luwu.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * @author OZY
 * @Description: durid数据源配置文件
 * @date 2019-07-08 03:25:47
 */


@Configuration
public class DuridConfig {

    public static final Logger logger = LoggerFactory.getLogger(DuridConfig.class);


    /**
     * 配置数据源
     *
     * @return
     */

    @Bean(name = "druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDbType("mysql");
        // 初始化连接大小
        datasource.setInitialSize(2);
        //连接池最小空闲
        datasource.setMinIdle(1);
        //连接池最大使用连接数量
        datasource.setMaxActive(20);
        //连接池最大空闲
        datasource.setMaxWait(6000_0);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(6000_0);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(3000_00);
        datasource.setMaxOpenPreparedStatements(0);
        //验证数据库连接的有效性
        datasource.setValidationQuery("SELECT 'x'");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setPoolPreparedStatements(false);
        try {
            datasource.setFilters("stat");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return datasource;
    }

    /**
     * 监控配置
     *
     * @return
     */

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean<>(
                new StatViewServlet(), "/druid/*"
        );
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "*");
        // IP黑名单(共同存在时，deny优先于allow)
        //servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "root");
        servletRegistrationBean.addInitParameter("loginPassword", "root");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }


    /**
     * 忽略资源
     *
     * @return
     */

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}

