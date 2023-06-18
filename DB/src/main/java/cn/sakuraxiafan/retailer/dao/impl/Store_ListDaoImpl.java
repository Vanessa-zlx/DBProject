package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.Store_ListDao;
import cn.sakuraxiafan.retailer.entity.Store_List;

import java.math.BigDecimal;

public class Store_ListDaoImpl extends BaseDaoImpl<Store_List> implements Store_ListDao {
    public Store_ListDaoImpl(Class<Store_List> target) {
        super(target);
    }

    public static void main(String[] args) {
        Store_ListDaoImpl storeListDao = new Store_ListDaoImpl(Store_List.class);
        storeListDao.insert(new Store_List(1L,843571L,1000,new BigDecimal("4")));
    }
}
