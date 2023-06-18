package cn.sakuraxiafan.retailer.dao;

import cn.sakuraxiafan.retailer.dao.impl.BaseDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.CustomerDaoImpl;
import cn.sakuraxiafan.retailer.entity.Customer;

import java.util.List;

public interface CustomerDao extends BaseDao<Customer>{
    /**
     * 按用户编号查询用户信息
     * @param no 用户编号
     * @return 用户对象
     */
    public Customer queryByNo(Long no);

}
