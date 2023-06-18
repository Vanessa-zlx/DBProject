package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.entity.Orders_List;

import java.math.BigDecimal;

public class Orders_ListDaoImpl extends BaseDaoImpl<Orders_List> {
    public Orders_ListDaoImpl(Class<Orders_List> target) {
        super(target);
    }

    public static void main(String[] args) {
        Orders_ListDaoImpl ordersListDao = new Orders_ListDaoImpl(Orders_List.class);
        ordersListDao.insert(new Orders_List(843571L,143L,1000,new BigDecimal("2.5")));
        ordersListDao.queryAll().forEach(System.out::println);
    }
}
