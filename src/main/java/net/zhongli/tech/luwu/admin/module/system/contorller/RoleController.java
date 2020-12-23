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
import net.zhongli.tech.luwu.admin.module.system.entity.RoleEntity;
import net.zhongli.tech.luwu.admin.module.system.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 * @author lk
 * @create 2020/12/22 3:32 下午
 **/
@Controller
@RequestMapping("/system/roles")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    /**
     * 角色页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/role/index";
    }

    /**
     * 列表页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:list')")
    @GetMapping("/list.html")
    public String list() {
        return "/role/list";
    }

    /**
     * 表单页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:form')")
    @GetMapping("/form.html")
    public String form() {
        return "/role/form";
    }

    /**
     * 获取角色分页列表
     * @param pager
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:list:post')")
    @PostMapping("/list")
    @ResponseBody
    public Result<Pager> roleList(@RequestBody Pager pager) {
        Pager roleEntityPager = this.roleService.queryList(pager);
        return ResultUtil.page(roleEntityPager);
    }

    /**
     * 获取角色列表
     * @param parameters
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:get')")
    @GetMapping
    @ResponseBody
    public Result<List<RoleEntity>> roleList(@RequestParam Map<String, Object> parameters) {
        List<RoleEntity> roleEntityList = this.roleService.findBy(parameters);
        return ResultUtil.success(roleEntityList);
    }

    /**
     * 通过 id 获取角色
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:role')")
    @GetMapping("/{roleId}")
    @ResponseBody
    public Result<RoleEntity> findById(@PathVariable("roleId") Long roleId) {
        RoleEntity roleEntity = this.roleService.findById(roleId);
        return ResultUtil.success(roleEntity);
    }

    /**
     * 添加角色
     * @param roleEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:post')")
    @PostMapping
    @ResponseBody
    @OperateLog(description = "添加角色信息", operateType = OperateLogTypes.SAVE)
    public Result<RoleEntity> save(@RequestBody RoleEntity roleEntity) {
        this.roleService.save(roleEntity);
        return ResultUtil.success(roleEntity);
    }

    /**
     * 修改角色
     * @param roleEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:put')")
    @PutMapping
    @ResponseBody
    @OperateLog(description = "修改角色信息", operateType = OperateLogTypes.UPDATE)
    public Result<RoleEntity> update(@RequestBody RoleEntity roleEntity) {
        this.roleService.update(roleEntity);
        return ResultUtil.success();
    }

    /**
     * 通过角色 id 删除角色
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAuthority('system:roles:delete')")
    @DeleteMapping("/{roleId}")
    @ResponseBody
    @OperateLog(description = "删除角色信息", operateType = OperateLogTypes.DELETE)
    public Result<String> deleteById(@PathVariable("roleId") Long roleId) {
        this.roleService.deleteById(roleId);
        return ResultUtil.success();
    }

}
