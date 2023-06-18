package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.VendorDao;
import cn.sakuraxiafan.retailer.entity.Vendor;

public class VendorDaoImpl extends BaseDaoImpl<Vendor> implements VendorDao {
    public VendorDaoImpl(Class<Vendor> target) {
        super(target);
    }

    public static void main(String[] args) {
        VendorDaoImpl vendorDao = new VendorDaoImpl(Vendor.class);
        vendorDao.insert(new Vendor("阿宽"));
        vendorDao.insert(new Vendor("康师傅"));
        vendorDao.queryAll().forEach(System.out::println);
    }
}
