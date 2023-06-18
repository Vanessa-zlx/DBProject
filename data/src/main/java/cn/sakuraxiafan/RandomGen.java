package cn.sakuraxiafan;

import cn.sakuraxiafan.retailer.dao.CategoryDao;
import cn.sakuraxiafan.retailer.dao.ProductDao;
import cn.sakuraxiafan.retailer.dao.StoreDao;
import cn.sakuraxiafan.retailer.dao.Store_ListDao;
import cn.sakuraxiafan.retailer.dao.impl.CategoryDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.ProductDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.StoreDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.Store_ListDaoImpl;
import cn.sakuraxiafan.retailer.entity.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

public class RandomGen {
    private static Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) {
        ArrayList<Vendor> vendors = generateVendor(50);
        ArrayList<Brand> brands = generateBrands(vendors);
//        vendors.forEach(System.out::println);
//        brands.forEach(System.out::println);
        System.out.println(vendors.size()+" "+brands.size());
    }
    public static ArrayList<Store> generateStore(){
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<String> states = TxtRead.readStates();
        for (String state : states) {
            int storeInState = random.nextInt(1, 3);
            Calendar opening = Calendar.getInstance(Locale.CHINA);
            opening.set(Calendar.HOUR_OF_DAY, 6);
            opening.set(Calendar.MINUTE, 30);
            opening.set(Calendar.SECOND,0);
            Calendar closing = Calendar.getInstance(Locale.CHINA);
            closing.set(Calendar.HOUR_OF_DAY, 21);
            closing.set(Calendar.MINUTE, 30);
            closing.set(Calendar.SECOND,0);
            if (random.nextBoolean()) {
                opening.set(Calendar.MINUTE, 0);
                opening.set(Calendar.SECOND,0);
            }
            if (random.nextBoolean()) {
                closing.set(Calendar.MINUTE, 0);
                closing.set(Calendar.SECOND,0);
            }
            for (int j = 0; j < storeInState; j++) {
                stores.add(new Store(null, state,
                        new Time(opening.getTime().getTime()), new Time(closing.getTime().getTime())));
            }
        }
        return stores;
    }
    public static ArrayList<Vendor> generateVendor(int n){
        HashSet<String> names = new HashSet<>();
        ArrayList<Vendor> vendors = new ArrayList<>();
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        while (names.size()<n){
            String s = getRandomString(str, 5);
            names.add(s);
        }
        names.forEach(item->{
            vendors.add(new Vendor(item));
        });
        return vendors;
    }
    public static ArrayList<Brand> generateBrands(ArrayList<Vendor> vendors){
        HashSet<String> names = new HashSet<>();
        ArrayList<Brand> brands = new ArrayList<>();
        String str="abcdefghijklmnopqrstuvwxyz";
        vendors.forEach(item->{
            int i = random.nextInt(2,5);
            for (int j = 0; j < i; j++) {
                brands.add(new Brand(getRandomString(str,random.nextInt(4,7)),item.getName()));
            }
        });
        return brands;
    }
    public static ArrayList<Product_Category> generatePC(){
        ArrayList<Product_Category> productCategories = new ArrayList<>();
        ProductDao productDao = new ProductDaoImpl(Product.class);
        CategoryDao categoryDao = new CategoryDaoImpl(Category.class);
        List<Product> products = productDao.queryAll();
        List<Category> categories = categoryDao.queryAll();
        String tempName = products.get(0).getName();
        int categoryNum = 0;
        for (Product product : products) {
            String name = product.getName();
            if (!name.equals(tempName)){
                categoryNum=(categoryNum+1)%categories.size();
                tempName=name;
            }
            productCategories.add(new Product_Category(product.getUPC(),categories.get(categoryNum).getName()));
        }
        return productCategories;
    }
    public static ArrayList<Store_List> generateSL(){
        StoreDao storeDao = new StoreDaoImpl(Store.class);
        ProductDao productDao = new ProductDaoImpl(Product.class);
        List<Product> products = productDao.queryAll();
        HashMap<String, BigDecimal> prices = XlsxTest.readPrice();
        ArrayList<Store_List> storeLists = new ArrayList<>();
        storeDao.queryAll().forEach(store->{
            products.forEach(product->{
                String factor = "1";
                if (product.getSize()!=null){
                    if (product.getSize().equals("ultra")){
                        factor="1.2";
                    }else if (product.getSize().equals("small")){
                        factor="0.8";
                    }
                }
                storeLists.add(new Store_List(store.getNo(),product.getUPC(),100,
                        prices.get(product.getName()).multiply(new BigDecimal(factor))));
            });
        });
        return storeLists;
    }
    public static String getRandomString(String str,int length){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
