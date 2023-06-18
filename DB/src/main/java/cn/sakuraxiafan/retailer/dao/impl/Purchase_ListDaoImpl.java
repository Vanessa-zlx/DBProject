package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.Purchase_ListDao;
import cn.sakuraxiafan.retailer.entity.Purchase_List;

public class Purchase_ListDaoImpl extends BaseDaoImpl<Purchase_List> implements Purchase_ListDao {
    public Purchase_ListDaoImpl(Class<Purchase_List> target) {
        super(target);
    }

    public static void main(String[] args) {
        Purchase_ListDaoImpl purchaseListDao = new Purchase_ListDaoImpl(Purchase_List.class);
        System.out.println(purchaseListDao.insert(new Purchase_List(843571L, 1l, 2)));
    }
}
