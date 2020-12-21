package net.zhongli.tech.luwu.admin.core.security.handler;

import com.alibaba.fastjson.JSONObject;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.LoginlogEntity;
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.service.LoginlogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: OZY
 * @Description: 登陆成功处理 handler
 * @date 2017/10/16 21:10
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private LoginlogService loginlogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // 获得授权后可得到用户信息
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        // 记录登陆日志
        LoginlogEntity loginlogEntity = new LoginlogEntity();
        loginlogEntity.setIp(HttpUtils.getIpAddress(request));
        loginlogEntity.setUserEntity(userEntity);
        loginlogService.save(loginlogEntity);


        // 封装登录成功信息返回
        Result<Object> jsonResult = ResultUtil.success();
        //写出json
        HttpUtils.writeJson(response,  JSONObject.toJSONString(jsonResult));
    }
}
