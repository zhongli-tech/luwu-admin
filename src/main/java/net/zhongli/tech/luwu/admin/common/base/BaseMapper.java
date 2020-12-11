package net.zhongli.tech.luwu.admin.common.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础持久层
 * 封装对数据的基本操作
 * @author lk
 * @create 2020/12/10 2:18 下午
 **/
public interface BaseMapper <T, ID extends Serializable> {

    /**
     * 单实体保存
     * @param t 保存实体
     * @return
     */
    int save(T t);

    /**
     * 多实体批量保存
     * @param t 实体集合
     * @return
     */
    int saveBatch(List<T> t);

    /**
     * 通过主键查找实体
     * @param id 主键
     * @return
     */
    T findById(@Param("id") ID id);

    /**
     * 通过查询条件查找实体集合
     * @param parameter 查询条件
     * @return
     */
    List<T> findBy(Map<String, Object> parameter);

    /**
     * 通过查询条件返回列表
     * @param parameter
     * @return
     */
    List<T> queryList(Map<String, Object> parameter);

    /**
     * 通过条件查询返回 mybatis 自带分页后的列表
     * @param parameter
     * @param rowBounds
     * @return
     */
    List<T> queryList(Map<String, Object> parameter, RowBounds rowBounds);

    /**
     * 更新实体
     * @param t 更新实体
     * @return
     */
    int update(T t);

    /**
     * 通过主键删除实体
     * @param id 主键
     * @return
     */
    int deleteById(@Param("id") ID id);

    /**
     * 统计总数
     * @param parameter
     * @return
     */
    int count(Map<String, Object> parameter);
}
