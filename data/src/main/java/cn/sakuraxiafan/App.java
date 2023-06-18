package cn.sakuraxiafan;

import cn.sakuraxiafan.retailer.dao.*;
import cn.sakuraxiafan.retailer.dao.impl.*;
import cn.sakuraxiafan.retailer.entity.*;

import java.sql.SQLException;
import java.util.*;

public class App {
    public static void main(String args[]){
        initBrandVendor();
        initCategory();
        iniStore();
        initProduct();
        initPC();
        initCustomer();
        initSL();
        initPurchase();
        initPL();
    }
    static void initBrandVendor(){
        ArrayList<Vendor> vendors = RandomGen.generateVendor(15);
        ArrayList<Brand> brands = RandomGen.generateBrands(vendors);
        VendorDao vendorDao = new VendorDaoImpl(Vendor.class);
        BrandDaoImpl brandDao = new BrandDaoImpl(Brand.class);
        vendors.forEach(vendorDao::insert);
        brands.forEach(brandDao::insert);
        System.out.println(vendors.size()+" "+brands.size());
    }
    static void initProduct(){
        BrandDaoImpl brandDao = new BrandDaoImpl(Brand.class);
        ProductDao productDao = new ProductDaoImpl(Product.class);
        ArrayList<Product> products = XlsxTest.readProduct(brandDao.queryAll());
        products.forEach(productDao::insert);
        System.out.println("products:"+products.size());
    }
    static void initCategory(){
        CategoryDao categoryDao = new CategoryDaoImpl(Category.class);
        ArrayList<Category> categories = XlsxTest.readCategory();
        System.out.println("categories:"+categories.size());
        categories.forEach(categoryDao::insert);
    }
    static void iniStore(){
        StoreDao storeDao = new StoreDaoImpl(Store.class);
        ArrayList<Store> stores = RandomGen.generateStore();
        stores.forEach(storeDao::insert);
        System.out.println("store:"+stores.size());
    }
    static void initPC(){
        ArrayList<Product_Category> productCategories = RandomGen.generatePC();
        Product_CategoryDao productCategoryDao = new Product_CategoryDaoImpl(Product_Category.class);
        productCategories.forEach(productCategoryDao::insert);
        System.out.println("productCategories:"+productCategories.size());
    }
    static void initCustomer(){
        CustomerDao customerDao = new CustomerDaoImpl(Customer.class);
        ArrayList<Customer> customers = XlsxTest.readCustomer();
        customers.forEach(customerDao::insert);
        System.out.println("customers:"+customers.size());
    }
    static void initSL(){
        Store_ListDao storeListDao = new Store_ListDaoImpl(Store_List.class);
        ArrayList<Store_List> storeLists = RandomGen.generateSL();
        storeLists.forEach(storeListDao::insert);
        System.out.println("storeLists:"+storeLists.size());
    }
    static void initPurchase(){
        PurchaseDao purchaseDao = new PurchaseDaoImpl(Purchase.class);
        ArrayList<Purchase> purchases = XlsxTest.readPurchase();
        purchases.forEach(purchaseDao::insert);
        System.out.println("purchases:"+purchases.size());

    }
    static void initPL(){
        Purchase_ListDao purchaseListDao = new Purchase_ListDaoImpl(Purchase_List.class);
        ArrayList<Purchase_List> purchaseLists = XlsxTest.readPL();
        purchaseLists.forEach(purchaseListDao::insert);
        System.out.println(purchaseLists.size());
    }
}