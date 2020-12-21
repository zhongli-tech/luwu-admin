package net.zhongli.tech.luwu.admin.core.security.handler;

import com.alibaba.fastjson.JSONObject;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author OZY
 * @Date 2019/07/09 21:44
 * @Description 登陆失败处理handler
 * @Version V1.0
 **/
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //封装登录失败信息返回
        Result<String> jsonResult = ResultUtil.fail(exception.getMessage());
        //写出json
        HttpUtils.writeJson(response,  JSONObject.toJSONString(jsonResult));
    }



}
