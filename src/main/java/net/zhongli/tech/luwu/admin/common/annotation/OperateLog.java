package net.zhongli.tech.luwu.admin.common.annotation;




import net.zhongli.tech.luwu.admin.common.enums.OperateLogTypes;

import java.lang.annotation.*;

/**
 * @author OZY
 * @Description: 操作日志记录注解，该注解是用于记录操作日志的，只能注解在controller层
 * @date 2018/1/11 9:36
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//只能注解方法
public @interface OperateLog {

    /**
     * 方法描述
     * @return
     */
    String description() default "";

    /**
     * 操作类型 类型详情请查看 {@link OperateLogTypes}
     * 默认为 查询类型
     * @return
     */
    OperateLogTypes operateType() default OperateLogTypes.FIND;



}
