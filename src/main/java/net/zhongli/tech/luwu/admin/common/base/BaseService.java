package net.zhongli.tech.luwu.admin.common.base;

import net.zhongli.tech.luwu.admin.common.dto.Pager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础业务逻辑处理接口
 * @author lk
 * @create 2020/12/10 2:29 下午
 **/
public interface BaseService<T, ID extends Serializable> {

    /**
     * 保存单个实体
     *
     * @param t 保存实体
     * @return
     */
    int save(T t);

    /**
     * 批量保存实体
     *
     * @param t 实体集合
     * @return
     */
    int saveBatch(List<T> t);

    /**
     * 通过 id 查询单个实体
     *
     * @param id 主键
     * @return
     */
    T findById(ID id);

    /**
     * 通过条件查找实体
     *
     * @param parameter 查询条件
     * @return
     */
    List<T> findBy(Map<String, Object> parameter);

    /**
     * 通过查询条件查询列表
     *
     * @param parameter 查询条件
     * @return
     */
    List<T> queryList(Map<String, Object> parameter);

    /**
     * 带分页的条件查询列表，使用 mybatis 自带 RowBound 分页，适合数据量小的时候使用
     * @param pager
     * @return
     */
    Pager queryListWithRow(Pager pager);

    /**
     * 带分页的条件查询列表，使用逻辑分页，适合数据量小的时候使用
     * @param pager
     * @return
     */
    Pager queryList(Pager pager);

    /**
     * 更新实体
     *
     * @param t 实体
     * @return
     */
    int update(T t);

    /**
     * 通过 id 删除
     *
     * @param id 主键
     * @return
     */
    int deleteById(ID id);

    /**
     * 通过 ids 批量删除
     *
     * @param ids 删除主键集合
     * @return
     */
    int deleteByIds(List<ID> ids);
}
