package net.zhongli.tech.luwu.admin.common.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * 分页模型
 * @author lk
 * @create 2020/12/10 17:01 下午
 **/
@Data
@ToString
public class Pager {

    /**
     * 第几页，从 1 开始
     */
    private int pageNum;

    /**
     * 每页多少条数据
     */
    private int pageSize;

    /**
     * 排序列名
     */
    private String orderBy;

    /**
     * 一共多少页
     */
    private int pages;

    /**
     * 一共多少条数据
     */
    private long total;

    /**
     * 从第几条数据开始，第一条为 0
     */
    private long startRow;

    /**
     * 返回分页后的数据
     */
    private Object data;

    /**
     * 查询条件
     */
    private Map<String, Object> parameters;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

}
