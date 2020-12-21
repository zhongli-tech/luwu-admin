package net.zhongli.tech.luwu.admin.core.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author OZY
 * @Date 2019/07/23 21:52
 * @Description 登出handle
 * @Version V1.0
 **/
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 跳转登出页面
        PrintWriter out = response.getWriter();
        response.sendRedirect("/login");
        out.flush();
        out.close();
    }

}
