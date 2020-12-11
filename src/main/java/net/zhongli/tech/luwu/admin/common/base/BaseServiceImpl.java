package net.zhongli.tech.luwu.admin.common.base;

import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础业务逻辑层实现类
 * @author lk
 * @create 2020/12/10 2:34 下午
 **/
@Slf4j
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 基础 mapper
     */
    private BaseMapper<T, ID> baseMapper;

    /**
     * 调用的是对应实际的 mapper
     * @param baseMapper
     */
    public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 保存单个实体
     *
     * @param t 保存实体
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int save(T t) {
        try {
            return this.baseMapper.save(t);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】save error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 批量保存实体
     *
     * @param t 实体集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int saveBatch(List<T> t) {
        try {
            return this.baseMapper.saveBatch(t);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】saveBatch error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 通过 id 查询单个实体
     *
     * @param id 主键
     * @return
     */
    @Override
    public T findById(ID id) {
        try {
            return this.baseMapper.findById(id);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】findById error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 通过条件查找实体
     *
     * @param parameter 查询条件
     * @return
     */
    @Override
    public List<T> findBy(Map<String, Object> parameter) {
        try {
            return this.baseMapper.findBy(parameter);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】findBy error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 通过查询条件查询列表
     *
     * @param parameter 查询条件
     * @return
     */
    @Override
    public List<T> queryList(Map<String, Object> parameter) {
        try {
            return this.baseMapper.queryList(parameter);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】queryList error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 带分页的条件查询列表
     * 使用 mybatis 自带 RowBound 分页，适合数据量小的时候使用，需要配合 count 方法
     *
     * @param pager
     * @return
     */
    @Override
    public Pager queryListWithRow(Pager pager) {
        int total = this.baseMapper.count(pager.getParameters());
        pager.setTotal(total);
        // 计算总页数
        int pages = (int) Math.ceil(total * 1.0 / pager.getPageSize());
        pager.setPages(pages);
        // 计算偏移量
        int startRow = (pager.getPageNum() - 1) * pager.getPageSize();
        pager.setStartRow(startRow);
        List<T> tList = this.baseMapper.queryList(pager.getParameters(), new RowBounds(startRow, pager.getPageSize()));
        pager.setData(tList);
        return pager;
    }

    /**
     * 带分页的条件查询列表，使用逻辑分页，适合数据量小的时候使用
     *
     * @param pager
     * @return
     */
    @Override
    public Pager queryList(Pager pager) {
        List<T> tList = this.baseMapper.queryList(pager.getParameters());
        int total = tList.size();
        pager.setTotal(total);
        // 计算总页数
        int pages = (int) Math.ceil(total * 1.0 / pager.getPageSize());
        pager.setPages(pages);
        // 计算偏移量
        int startRow = (pager.getPageNum() - 1) * pager.getPageSize();
        pager.setStartRow(startRow);
        // 从第几条数据开始
        int firstIndex = startRow > total ? total - 1 : startRow;
        // 到第几条数据结束
        int lastIndex = pager.getPageNum() * pager.getPageSize();
        lastIndex = Math.min(lastIndex, total);
        pager.setData(tList.subList(firstIndex, lastIndex));
        return pager;
    }

    /**
     * 更新实体
     *
     * @param t 实体
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int update(T t) {
        try {
            return this.baseMapper.update(t);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】update error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 通过 id 删除
     *
     * @param id 主键
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int deleteById(ID id) {
        try {
            return this.baseMapper.deleteById(id);
        } catch (Exception e) {
            log.error("\n【Luwu-Service】deleteById error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }

    /**
     * 通过 ids 批量删除
     *
     * @param ids 删除主键集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int deleteByIds(List<ID> ids) {
        try {
            ids.forEach(id -> this.baseMapper.deleteById(id));
            return ids.size();
        } catch (Exception e) {
            log.error("\n【Luwu-Service】deleteByIds error,e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
    }
}
