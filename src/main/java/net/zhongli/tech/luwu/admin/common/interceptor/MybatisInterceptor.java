package net.zhongli.tech.luwu.admin.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.constants.CommonConstant;
import net.zhongli.tech.luwu.admin.common.constants.StringConstant;
import net.zhongli.tech.luwu.admin.common.utils.UserDetailsUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author lk
 * @create 2020/12/14 2:26 下午
 **/
@Slf4j
@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class
        })
})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 方法一
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
        String id = mappedStatement.getId();
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        BoundSql boundSql = statementHandler.getBoundSql();

        //获取到原始sql语句
        String sql = boundSql.getSql();
        // 只针对插入操作修改
        String newSql = sql;
        if (sqlCommandType.equals(SqlCommandType.INSERT.name())) {
            newSql = this.setCreateBy(sql);
        }

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 拼接创建用户 id
     * @param tarSql
     * @return
     */
    private String setCreateBy(String tarSql) {
        String[] sqlArray = tarSql.split(";");
        StringBuilder runSql = new StringBuilder();
        for (String sql : sqlArray) {
            String resultSql;
            UserEntity userEntity = UserDetailsUtil.getPrincipal();
            String userName = CommonConstant.SYSTEM_USERNAME;
            if (null != userEntity) {
                userName = userEntity.getUsername();
            }
            String leftInsertSql = sql.substring(StringConstant.STRING_BEGIN_ZERO,
                    sql.indexOf("(") + StringConstant.STRING_DISPLACE_ONE);
            String rightInsertSql = sql.substring(sql.indexOf("(") + StringConstant.STRING_DISPLACE_ONE,
                    sql.indexOf(")") + StringConstant.STRING_DISPLACE_ONE);
            String insertSql = leftInsertSql + "\ncreate_by," + rightInsertSql;
            String valueSql;
            String val = "";
            if (sql.contains("VALUES")) {
                valueSql = sql.substring(sql.indexOf("VALUES"));
                val = "VALUES";
            } else if (sql.contains("values")) {
                valueSql = sql.substring(sql.indexOf("values"));
                val = "values";
            } else if (sql.contains("VALUE")) {
                valueSql = sql.substring(sql.indexOf("VALUE"));
                val = "VALUE";
            } else {
                valueSql = sql.substring(sql.indexOf("value"));
                val = "value";
            }
            String leftValueSql = valueSql.substring(StringConstant.STRING_BEGIN_ZERO,
                    valueSql.indexOf("(") + StringConstant.STRING_DISPLACE_ONE);
            String rightValueSql = valueSql.substring(valueSql.indexOf("(") + StringConstant.STRING_DISPLACE_ONE,
                    valueSql.indexOf(")") + StringConstant.STRING_DISPLACE_ONE);
            int count = countStr(valueSql, ")");
            StringBuilder realValueSql = new StringBuilder();
            if (count == 1) {
                realValueSql.append(leftValueSql).append("\n'").append(userName).append("',").append(rightValueSql);
            } else {
                realValueSql.append(leftValueSql).append("\n'").append(userName).append("',").append(rightValueSql).append(",");
                for (int i = 1; i < count; i++) {
                    realValueSql.append(leftValueSql.replaceAll(val, "")).append("\n'").append(userName).append("',").append(rightValueSql);
                    if (i != count - 1) {
                        realValueSql.append(",");
                    }

                }
            }
            resultSql = insertSql + realValueSql;
            runSql.append(resultSql).append(";");
        }

        return runSql.toString();
    }

    /**
     * 统计字符重复次数
     * @param str
     * @param sToFind
     * @return
     */
    private int countStr(String str, String sToFind) {
        int num = 0;
        while (str.contains(sToFind)) {
            str = str.substring(str.indexOf(sToFind) + sToFind.length());
            num ++;
        }
        return num;
    }
}