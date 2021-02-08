package net.zhongli.tech.luwu.admin.module.index.controller;

import net.zhongli.tech.luwu.admin.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author OZY
 * @Date 2020/12/12 09:30
 * @Description Index控制层
 * @Version V1.0
 **/
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    /**
     * 登录页面跳转
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 默认布局页跳转
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 首页跳转
     * @return
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/index";
    }

}
