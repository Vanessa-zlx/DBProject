package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.ProductDao;
import cn.sakuraxiafan.retailer.entity.Product;

public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
    public ProductDaoImpl(Class<Product> target){
        super(target);
    }

    public static void main(String[] args) {
        ProductDaoImpl productDao = new ProductDaoImpl(Product.class);
//        System.out.println(Integer.MAX_VALUE);
        productDao.insert(new Product(843571L,"冰红茶","瓶装","1L","康师傅冰红茶"));
        productDao.queryAll().forEach(System.out::println);
    }
}
