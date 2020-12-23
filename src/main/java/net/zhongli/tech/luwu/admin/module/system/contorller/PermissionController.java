package net.zhongli.tech.luwu.admin.module.system.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.Model;
import net.zhongli.tech.luwu.admin.common.annotation.OperateLog;
import net.zhongli.tech.luwu.admin.common.base.BaseController;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.enums.OperateLogTypes;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;
import net.zhongli.tech.luwu.admin.module.system.service.PermissionService;
import net.zhongli.tech.luwu.admin.module.system.vo.PermissionTreeVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限控制器
 * @author lk
 * @create 2020/12/22 5:17 下午
 **/
@Controller
@RequestMapping("/system/permissions")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;

    /**
     * 权限页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/permission/index";
    }

    /**
     * 列表页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:list')")
    @GetMapping("/list.html")
    public String list() {
        return "/permission/list";
    }

    /**
     * 表单页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:form')")
    @GetMapping("/form.html")
    public String form() {
        return "/permission/form";
    }

    /**
     * 获取权限分页列表
     * @param pager
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:list:post')")
    @PostMapping("/list")
    @ResponseBody
    public Result<Pager> permissionList(@RequestBody Pager pager) {
        Page<Object> page = PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<PermissionEntity> permissionEntityList = this.permissionService.queryList(pager.getParameters());
        return ResultUtil.page(page, permissionEntityList);
    }

    /**
     * 获取权限列表
     * @param parameters
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:get')")
    @GetMapping
    @ResponseBody
    public Result<List<PermissionEntity>> permissionList(@RequestParam Map<String, Object> parameters) {
        List<PermissionEntity> permissionEntityList = this.permissionService.findBy(parameters);
        return ResultUtil.success(permissionEntityList);
    }

    /**
     * 通过 id 查找权限信息
     * @param permissionId
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:permission')")
    @GetMapping("/{permissionId}")
    @ResponseBody
    public Result<PermissionEntity> findById(@PathVariable("permissionId") Long permissionId) {
        PermissionEntity permissionEntity = this.permissionService.findById(permissionId);
        return ResultUtil.success(permissionEntity);
    }

    /**
     * 添加权限
     * @param permissionEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:post')")
    @PostMapping
    @ResponseBody
    @OperateLog(description = "添加权限信息", operateType = OperateLogTypes.SAVE)
    public Result<PermissionEntity> save(@RequestBody PermissionEntity permissionEntity) {
        this.permissionService.save(permissionEntity);
        return ResultUtil.success(permissionEntity);
    }

    /**
     * 修改权限信息
     * @param permissionEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:put')")
    @PutMapping
    @ResponseBody
    @OperateLog(description = "修改权限信息", operateType = OperateLogTypes.UPDATE)
    public Result<PermissionEntity> update(@RequestBody PermissionEntity permissionEntity) {
        this.permissionService.update(permissionEntity);
        return ResultUtil.success(permissionEntity);
    }

    /**
     * 通过 id 删除权限
     * @param permissionId
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:delete')")
    @DeleteMapping("/{permissionId}")
    @ResponseBody
    @OperateLog(description = "删除权限信息", operateType = OperateLogTypes.DELETE)
    public Result<String> update(@PathVariable("permissionId") Long permissionId) {
        this.permissionService.deleteById(permissionId);
        return ResultUtil.success();
    }

    /**
     * 权限资源树列表
     * @param parameter
     * @return
     */
    @PreAuthorize("hasAuthority('system:permissions:treeList')")
    @GetMapping("/tree")
    @ResponseBody
    public Result<List<PermissionTreeVO>> permissionTreeList(@RequestBody Map<String, Object> parameter) {
        List<PermissionTreeVO>  permissionTreeVOList = this.permissionService.queryTreeList(parameter);
        return ResultUtil.success(permissionTreeVOList);
    }
}
