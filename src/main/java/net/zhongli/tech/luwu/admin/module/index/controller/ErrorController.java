package net.zhongli.tech.luwu.admin.module.index.controller;


import net.zhongli.tech.luwu.admin.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常配置页面
 * @author lk
 * @create 2020/12/16 3:05 下午
 **/
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {

    @GetMapping("/404")
    public ModelAndView pageNoFound() {
        return new ModelAndView("error/404");
    }

    @GetMapping("/kickout")
    public String kickout(Model model) {
        model.addAttribute("message", "您已被管理员踢出，请重新登录");
        return "error/kickout";
    }
}
