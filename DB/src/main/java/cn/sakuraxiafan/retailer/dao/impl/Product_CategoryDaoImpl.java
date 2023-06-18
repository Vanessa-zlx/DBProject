package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.Product_CategoryDao;
import cn.sakuraxiafan.retailer.entity.Product_Category;

public class Product_CategoryDaoImpl extends BaseDaoImpl<Product_Category> implements Product_CategoryDao {
    public Product_CategoryDaoImpl(Class<Product_Category> target) {
        super(target);
    }

    public static void main(String[] args) {
        Product_CategoryDaoImpl productCategoryDao = new Product_CategoryDaoImpl(Product_Category.class);
        productCategoryDao.insert(new Product_Category(843571L,"饮料"));
    }
}
