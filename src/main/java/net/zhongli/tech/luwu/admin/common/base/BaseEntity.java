package net.zhongli.tech.luwu.admin.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 * @author lk
 * @create 2020/12/10 2:07 下午
 **/
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1969042693610873010L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 更新日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    /**
     * 创建用户名
     */
    private String createBy;
}
