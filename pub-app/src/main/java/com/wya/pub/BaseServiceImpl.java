package com.wya.pub;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 15:41
 * @Description: No Description
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract BaseMapper<T> getMapper();

    /**
     * 通过id查询数据
     *
     * @param id
     *            数据标识
     * @return 数据对象
     */
    @Override
    public T get(Long id) {
        return this.getMapper().get(id);
    }


    /**
     * 插入
     *
     * @param t
     *            插入的对象
     * @return 插入数据条数
     */
    @Override
    public int insert(T t) {
        return this.getMapper().insert(t);
    }


    /**
     * 批量插入
     *
     * @param list
     *            数据列表
     * @return 插入数据条数
     */
    @Override
    public int insertList(List<T> list){
        return this.getMapper().insertList(list);
    }


    /**
     * 修改
     *
     * @param t
     *            修改的数据
     * @return 修改的数据条数
     */
    @Override
    public void update(T t){
        this.getMapper().update(t);
    }


    /**
     * 删除
     *
     * @param id
     *            数据标识
     * @return 删除的数据条数
     */
    @Override
    public void delete(Long id) {
        this.getMapper().delete(id);
    }


    /**
     * 查询所有数据
     *
     * @return 数据列表
     */
    @Override
    public List<T> getAll() {
        return this.getMapper().getAll();
    }


    /**
     * 通过条件查询数据列表
     *
     * @param t
     *            查询条件
     * @return 数据列表
     */
    @Override
    public List<T> getList(T t) {
        return this.getMapper().getList(t);
    }



    /**
     * 通过条件查询数据列表--分页
     *
     * @param t
     *            查询条件
     * @return 数据列表
     */
    @Override
    public PageInfo<T> pageList(T t, int pageNum, int pageSize, boolean count) {
        PageHelper.startPage(pageNum, pageSize, count);
        List<T> list = this.getMapper().getList(t);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
