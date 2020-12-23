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
import net.zhongli.tech.luwu.admin.module.system.entity.UserEntity;
import net.zhongli.tech.luwu.admin.module.system.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * @author lk
 * @create 2020/12/22 2:32 下午
 **/
@Controller
@RequestMapping("/system/users")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 用户页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/user/index";
    }

    /**
     * 列表页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:list')")
    @GetMapping("/list.html")
    public String list(){
        return "/user/list";
    }

    /**
     * 表单页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:form')")
    @GetMapping("/form.html")
    public String form() {
        return "/user/form";
    }

    /**
     * 获取用户分页列表
     * @param pager
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:list:post')")
    @PostMapping("/list")
    @ResponseBody
    public Result<Pager> userList(@RequestBody Pager pager) {
        Page<Object> page = PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        List<UserEntity> userEntityList = this.userService.queryList(pager.getParameters());
        return ResultUtil.page(page, userEntityList);
    }

    /**
     * 获取用户列表
     * @param parameters
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:get')")
    @GetMapping
    @ResponseBody
    public Result<List<UserEntity>> userList(@RequestParam Map<String, Object> parameters) {
        List<UserEntity> userEntityList = this.userService.findBy(parameters);
        return ResultUtil.success(userEntityList);
    }

    /**
     * 通过用户 id 查找用户信息
     * @param userId
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:user')")
    @GetMapping("/{userId}")
    @ResponseBody
    public Result<UserEntity> findById(@PathVariable("userId") Long userId) {
        UserEntity userEntity = this.userService.findById(userId);
        return ResultUtil.success(userEntity);
    }

    /**
     * 添加用户方法
     * @param userEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:post')")
    @PostMapping
    @ResponseBody
    @OperateLog(description = "添加用户信息", operateType = OperateLogTypes.SAVE)
    public Result<UserEntity> save(@RequestBody @Validated UserEntity userEntity) {
        this.userService.save(userEntity);
        return ResultUtil.success(userEntity);
    }

    /**
     * 修改用户方法
     * @param userEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:put')")
    @PutMapping
    @ResponseBody
    @OperateLog(description = "修改用户信息", operateType = OperateLogTypes.UPDATE)
    public Result<UserEntity> update(@RequestBody @Validated UserEntity userEntity) {
        this.userService.update(userEntity);
        return ResultUtil.success();
    }

    /**
     * 通过用户 id 删除用户
     * @param userId
     * @return
     */
    @PreAuthorize("hasAuthority('system:users:delete')")
    @DeleteMapping("/{userId}")
    @ResponseBody
    @OperateLog(description = "修改用户信息", operateType = OperateLogTypes.DELETE)
    public Result<String> deleteById(@PathVariable("userId") Long userId) {
        this.userService.deleteById(userId);
        return ResultUtil.success();
    }
}
