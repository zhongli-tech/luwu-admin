package net.zhongli.tech.luwu.admin.module.system.contorller;

import io.swagger.models.Model;
import net.zhongli.tech.luwu.admin.common.base.BaseController;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.system.entity.ConfigEntity;
import net.zhongli.tech.luwu.admin.module.system.service.ConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统信息配置
 * @author lk
 * @create 2020/12/28 3:29 下午
 **/
@Controller
@RequestMapping("/system/config")
public class ConfigController extends BaseController {

    @Resource
    private ConfigService configService;

    /**
     * 系统信息页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('system:config:index')")
    @GetMapping("/index.html")
    public String index(Model model) {
        return "/config/index";
    }

    /**
     * 表单页面
     * @return
     */
    @PreAuthorize("hasAuthority('system:config:form')")
    @GetMapping("/form.html")
    public String form() {
        return "/config/form";
    }

    /**
     * 获取系统配置
     * @return
     */
    @PreAuthorize("hasAuthority('system:config:get')")
    @GetMapping
    @ResponseBody
    public Result<ConfigEntity> findBy() {
        List<ConfigEntity> configEntityList = this.configService.findBy(null);
        return configEntityList.isEmpty() ? ResultUtil.success() : ResultUtil.success(configEntityList.get(0));
    }

    /**
     * 更新系统配置
     * @param configEntity
     * @return
     */
    @PreAuthorize("hasAuthority('system:config:get')")
    @PutMapping
    @ResponseBody
    public Result<ConfigEntity> update(@RequestBody ConfigEntity configEntity) {
        if (null != configEntity.getId()) {
            // 更新
            this.configService.update(configEntity);
        } else {
            // 初始化并且保存
            this.configService.save(configEntity);
        }
        return ResultUtil.success(configEntity);
    }
}
