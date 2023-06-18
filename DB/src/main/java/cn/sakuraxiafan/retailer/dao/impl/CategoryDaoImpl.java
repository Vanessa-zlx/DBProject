package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.CategoryDao;
import cn.sakuraxiafan.retailer.entity.Brand;
import cn.sakuraxiafan.retailer.entity.Category;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {
    public CategoryDaoImpl(Class<Category> target) {
        super(target);
    }

    public static void main(String[] args) {
//        CategoryDaoImpl categoryDao = new CategoryDaoImpl(Category.class);
//        categoryDao.insert(new Category("食品",null));
//        categoryDao.insert(new Category("方便食品","食品"));
//        categoryDao.insert(new Category("方便面皮","方便食品"));
//        categoryDao.queryAll().forEach(System.out::println);

    }
}
