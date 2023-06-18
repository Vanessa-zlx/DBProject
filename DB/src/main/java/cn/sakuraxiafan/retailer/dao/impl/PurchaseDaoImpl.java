package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.PurchaseDao;
import cn.sakuraxiafan.retailer.entity.Purchase;

import java.sql.Timestamp;
import java.util.Date;

public class PurchaseDaoImpl extends BaseDaoImpl<Purchase> implements PurchaseDao {
    public PurchaseDaoImpl(Class<Purchase> target) {
        super(target);
    }

    public static void main(String[] args) {
        PurchaseDaoImpl purchaseDao = new PurchaseDaoImpl(Purchase.class);
        System.out.println(purchaseDao.insert(new Purchase(null, 1L, 1000001L, new Timestamp(new Date().getTime()), false)));
    }
}
