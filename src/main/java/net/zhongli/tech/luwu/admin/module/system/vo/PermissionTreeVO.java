package net.zhongli.tech.luwu.admin.module.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.zhongli.tech.luwu.admin.module.system.entity.PermissionEntity;

import java.util.List;

/**
 * 权限资源树
 * @author lk
 * @create 2020/12/23 4:40 下午
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionTreeVO extends PermissionEntity {

    List<PermissionTreeVO> children;
}
