package net.zhongli.tech.luwu.admin.module.demo.contorller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.zhongli.tech.luwu.admin.common.base.BaseController;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import net.zhongli.tech.luwu.admin.common.utils.ResultUtil;
import net.zhongli.tech.luwu.admin.module.demo.entity.DemoEntity;
import net.zhongli.tech.luwu.admin.module.demo.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author lk
 * @create 2020/12/9 3:15 下午
 **/
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Resource
    private DemoService demoService;

    //private final String indexPrefix = "/demo";

    @GetMapping("/index.html")
    public String index() {
        //return this.toPage(indexPrefix,"/index");
        return "/demo/index";
    }

    @ApiOperation(value = "测试方法", notes = "传入名字返回方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", required = true, dataType = "name")
    })
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String demo(@PathVariable("name")String name) {
        return "hello world: " + name;
    }

    @GetMapping("/test")
    @ResponseBody
    public Result<String> test() {
       throw new ServiceException("测试异常");
    }

    @GetMapping("/save")
    @ResponseBody
    public Result<DemoEntity> save() {

        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setDemoName("测试 demo");
        this.demoService.save(demoEntity);
        return ResultUtil.success(demoEntity);
    }

    @GetMapping("/demos")
    @ResponseBody
    public Result<Pager> demoList() {
        /*Page<Object> page = PageHelper.startPage(1, 10);
        page.setCountColumn("DISTINCT d.id");
        List<DemoEntity> demoEntityList = this.demoService.findBy(null);*/
        Pager pager = new Pager();
        pager.setPageNum(1);
        pager.setPageSize(10);
        pager = this.demoService.queryList(pager);
        return ResultUtil.page(pager);
    }
}
