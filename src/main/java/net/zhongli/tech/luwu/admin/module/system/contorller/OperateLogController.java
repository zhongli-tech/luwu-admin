package net.zhongli.tech.luwu.admin.module.system.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.Model;
import net.zhongli.tech.luwu.admin.common.base.BaseController;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.OperateLogEntity;
import net.zhongli.tech.luwu.admin.module.system.service.OperateLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 操作日志控制器
 * @author lk
 * @create 2020/12/23 4:59 下午
 **/
@Controller
@RequestMapping("/system/operate_log")
public class OperateLogController extends BaseController {

    @Resource
    private OperateLogService operateLogService;

    /**
     * 操作日志页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:operatelog:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/operatelog/index";
    }

    /**
     * 列表页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:operatelog:list')")
    @GetMapping("/list.html")
    public String list() {
        return "/operatelog/list";
    }

    /**
     * 获取日志分页列表
     * @param pager
     * @return
     */
    @PreAuthorize("hasAuthority('system:operatelog:list:post')")
    @PostMapping("/list")
    @ResponseBody
    public Result<Pager> operateLogList(@RequestBody Pager pager) {
        Page<Object> page = PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<OperateLogEntity> operateLogEntityList = this.operateLogService.queryList(pager.getParameters());
        return ResultUtil.page(page, operateLogEntityList);
    }

    /**
     * 日志列表
     * @param parameters
     * @return
     */
    @PreAuthorize("hasAuthority('system:operatelog:get')")
    @GetMapping
    @ResponseBody
    public Result<List<OperateLogEntity>> operateLogList(@RequestParam Map<String, Object> parameters) {
        List<OperateLogEntity> permissionEntityList = this.operateLogService.findBy(parameters);
        return ResultUtil.success(permissionEntityList);
    }

    /**
     * 通过 id 查找日志
     * @param operateLogId
     * @return
     */
    @PreAuthorize("hasAuthority('system:operatelog:permission')")
    @GetMapping("/{operateLogId}")
    @ResponseBody
    public Result<OperateLogEntity> findById(@PathVariable("operateLogId") Long operateLogId) {
        OperateLogEntity operateLogEntity = this.operateLogService.findById(operateLogId);
        return ResultUtil.success(operateLogEntity);
    }
}
