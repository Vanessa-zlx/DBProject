package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.BrandDao;
import cn.sakuraxiafan.retailer.entity.Brand;

public class BrandDaoImpl extends BaseDaoImpl<Brand> implements BrandDao{
    public BrandDaoImpl(Class<Brand> target) {
        super(target);
    }

    public static void main(String[] args) {
        BrandDao brandDao = new BrandDaoImpl(Brand.class);
        brandDao.insert(new Brand("阿宽红油面皮","阿宽"));
        brandDao.insert(new Brand("康师傅冰红茶","康师傅"));
//        brandDao.insert(new Brand("冰红茶","康帅傅"));
        brandDao.queryAll().forEach(System.out::println);
    }
}
