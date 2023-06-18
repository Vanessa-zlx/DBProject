package cn.sakuraxiafan.retailer.dao;

import java.util.List;

public interface BaseDao<T> {

    /**
     * 添加
     * @param t 对象
     * @return 受影响的列
     */
    public int insert(T t);
    /**
     * 单条件删除
     * @param fieldName 字段名
     * @param value 字段值
     * @return 受影响的列
     */
    public int simpleDelete(String fieldName, Object value);
    /**
     * 根据一个字段（主码）更新所有字段
     * @param fieldName 字段名（主码）
     * @param value 字段值
     * @param t 更新对象值
     * @return 受影响的行（1/0）
     */
    public int simpleUpdate(String fieldName, Object value, T t);
    /**
     * 单条件查询
     * @param fieldName 查询字段
     * @param value 查找值
     * @return 新构造的对象
     */
    public List<T> simpleQuery(String fieldName, Object value);

    /**
     * 多条件查询
     * @param fieldName 字段列表
     * @param value 字段值
     * @return 对象list
     */
    public List<T> multiQuery(String[] fieldName, Object[] value);

    /**
     * 查询所有项
     * @return 对象list
     */
    public List<T> queryAll();
}
