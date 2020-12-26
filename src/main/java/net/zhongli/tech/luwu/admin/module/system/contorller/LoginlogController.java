package net.zhongli.tech.luwu.admin.module.system.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.Model;
import net.zhongli.tech.luwu.admin.common.base.BaseController;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.LoginlogEntity;
import net.zhongli.tech.luwu.admin.module.system.service.LoginlogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志控制器
 * @author lk
 * @create 2020/12/23 5:07 下午
 **/
@Controller
@RequestMapping("/system/login_log")
public class LoginlogController extends BaseController {

    @Resource
    private LoginlogService loginlogService;

    /**
     * 登录日志页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:loginlog:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/loginlog/index";
    }

    /**
     * 列表页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:loginlog:list')")
    @GetMapping("/list.html")
    public String list() {
        return "/loginlog/list";
    }

    /**
     * 获取日志分页列表
     * @param pager
     * @return
     */
    @PreAuthorize("hasAuthority('system:loginlog:list:post')")
    @PostMapping("/list")
    @ResponseBody
    public Pager loginLogList(@RequestBody Pager pager) {
        Page<Object> page = PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<LoginlogEntity> loginlogEntityList = this.loginlogService.queryList(pager.getParameters());
        return ResultUtil.page(page, loginlogEntityList);
    }

    /**
     * 通过 id 查找日志
     * @param loginLogId
     * @return
     */
    @PreAuthorize("hasAuthority('system:loginlog:permission')")
    @GetMapping("/{loginLogId}")
    @ResponseBody
    public Result<LoginlogEntity> findById(@PathVariable("loginLogId") Long loginLogId) {
        LoginlogEntity loginlogEntity = this.loginlogService.findById(loginLogId);
        return ResultUtil.success(loginlogEntity);
    }
}
