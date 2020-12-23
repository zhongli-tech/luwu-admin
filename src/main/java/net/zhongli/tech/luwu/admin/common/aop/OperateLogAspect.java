package net.zhongli.tech.luwu.admin.common.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.annotation.OperateLog;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import net.zhongli.tech.luwu.admin.common.utils.UserDetailsUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.OperateLogEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.service.OperateLogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author LK
 * @create 2020/4/28 14:36
 **/
@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Resource
    private OperateLogService operateLogService;

    @Pointcut("@annotation(net.zhongli.tech.luwu.admin.common.annotation.OperateLog)")
    public void loggerPointCut() {

    }

    @Before("loggerPointCut()")
    public void saveLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperateLogEntity logEntity = new OperateLogEntity();
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        if (null != operateLog) {
            // 注解上的描述
            logEntity.setDescription(operateLog.description());
            logEntity.setOperateLogTypes(operateLog.operateType());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logEntity.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        try {
            for (Object o : args) {
                if (o instanceof MultipartFile || o instanceof MultipartFile[]) {
                    params += " 上传文件 ";
                } else {
                    params += JSON.toJSONString(o);
                }
            }
        } catch (Exception e) {
            log.error("【操作日志】日志参数获取异常捕捉，e={}", e.getMessage(), e);
        }
        if (!StringUtils.isEmpty(params)) {
            logEntity.setParams(params);
        }
        // 设置IP地址
        logEntity.setIp(HttpUtils.getIpAddress());
        // 用户名
        UserEntity userEntity = UserDetailsUtil.getPrincipal();
        if (null != userEntity) {
            logEntity.setUsername(userEntity.getUsername());
        }
       operateLogService.save(logEntity);
    }
}
