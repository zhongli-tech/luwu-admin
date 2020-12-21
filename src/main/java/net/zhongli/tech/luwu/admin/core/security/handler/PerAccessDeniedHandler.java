package net.zhongli.tech.luwu.admin.core.security.handler;

import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: OZY
 * @createTime: 2019-08-14 14:50
 * @description: 无权限拦截handler处理
 * @version: 1.0.0
 */
@Component
public class PerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if(HttpUtils.isAjaxRequest(request)) {
            //ajax请求
            //无权限拦截handler处理
            throw new ServiceException(ServiceErrorEnum.SYSTEM_NO_AUTHORITY);

        } else {
            //非ajax请求
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

    }

}
