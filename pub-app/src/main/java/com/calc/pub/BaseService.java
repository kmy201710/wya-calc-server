package com.calc.pub;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 15:41
 * @Description: No Description
 */
public interface BaseService<T> {

    /**
     * 通过id查询数据
     *
     * @param id
     *            数据标识
     * @return 数据对象
     */
    public T get(Long id);


    /**
     * 插入
     *
     * @param t
     *            插入的对象
     * @return 插入数据条数
     */
    public int insert(T t);


    /**
     * 批量插入
     *
     * @param list
     *            数据列表
     * @return 插入数据条数
     */
    public int insertList(List<T> list);


    /**
     * 修改
     *
     * @param t
     *            修改的数据
     * @return 修改的数据条数
     */
    public void update(T t);


    /**
     * 删除
     *
     * @param id
     *            数据标识
     * @return 删除的数据条数
     */
    public void delete(Long id);


    /**
     * 查询所有数据
     *
     * @return 数据列表
     */
    public List<T> getAll();


    /**
     * 通过条件查询数据列表
     *
     * @param t
     *            查询条件
     * @return 数据列表
     */
    public List<T> getList(T t);


    /**
     * 通过条件查询数据列表--分页
     *
     * @param t
     *            查询条件
     * @return 数据列表
     */
    public PageInfo<T> pageList(T t, int pageNum, int pageSize, boolean count);

    public void save(T t);
}
