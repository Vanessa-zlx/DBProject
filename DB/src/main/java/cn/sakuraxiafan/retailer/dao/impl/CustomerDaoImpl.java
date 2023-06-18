package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.CustomerDao;
import cn.sakuraxiafan.retailer.entity.Customer;

import java.util.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao{
    public CustomerDaoImpl(Class<Customer> target) {
        super(target);
    }
    @Override
    public Customer queryByNo(Long no) {
        List<Customer> customers = simpleQuery("no", no);
        if (customers.size()==1){
            return customers.get(0);
        }
        return null;
    }
    public static void main(String[] args) {
        CustomerDaoImpl customerDao = new CustomerDaoImpl(Customer.class);
        customerDao.insert(new Customer(null,"alice","1234","成华大道",new Date()));
//        System.out.println(customerDao.queryByNo(343));
        customerDao.queryAll().forEach(System.out::println);
        // TODO: 2023/6/14 将初始化代码写到一起
        // TODO: 2023/6/14 pdm里添加bigint的auto_increment
    }
}
