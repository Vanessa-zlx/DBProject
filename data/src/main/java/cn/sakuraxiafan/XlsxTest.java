package cn.sakuraxiafan;

import cn.sakuraxiafan.retailer.dao.ProductDao;
import cn.sakuraxiafan.retailer.dao.StoreDao;
import cn.sakuraxiafan.retailer.dao.impl.ProductDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.StoreDaoImpl;
import cn.sakuraxiafan.retailer.entity.*;
import cn.sakuraxiafan.util.StringUtil;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class XlsxTest {
    private static Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

//        ArrayList<Product> products = readProduct(RandomGen.generateBrands(RandomGen.generateVendor(50)));
//        products.forEach(System.out::println);
//        System.out.println(products.size());
        ArrayList<Customer> customers = readCustomer();
        customers.forEach(System.out::println);
        System.out.println(customers.size());

        long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);
    }
    public static ArrayList<Product> readProduct(List<Brand> brands){
        int brandNum = -1,brandCount=0;
        ArrayList<Product> products = new ArrayList<>();
        HashSet<String> names = new HashSet<>();
        Workbook wk = StreamingReader.builder().rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(XlsxTest.class.getClassLoader().getResourceAsStream("data.xlsx"));//只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        for (Row row : sheet) {
            rowCount++;
            if (rowCount==1){
                continue;
            }
            String productName = row.getCell(2).getStringCellValue();
            double price = row.getCell(5).getNumericCellValue();
            if (price<=0){
                continue;
            }
            if (rowCount>=800){
                break;
            }
            if (!names.contains(productName)){
                if (brandCount==0){
                    brandNum=(brandNum+1)%brands.size();
                    brandCount=random.nextInt(5,7);
                }
                Product product = new Product(null, productName, null, "normal", brands.get(brandNum).getName());
                if (productName.contains("SET ")){
                    product.setPackaging("set");
                    product.setSize(null);
                }else {
                    if (products.size()>480){
                        if (random.nextBoolean()){
                            products.add(product.withSize("ultra"));
                        }
                        if (random.nextBoolean()){
                            products.add(product.withSize("small"));
                        }
                    }
                }
                products.add(product);
                names.add(productName);
                brandCount--;
            }
        }
        System.out.println("rowCount:"+rowCount);
        return products;
    }
    public static ArrayList<Customer> readCustomer(){
        String str="abcdefghijklmnopqrstuvwxyz";
        String numbers="0123456789";
        ArrayList<String> states = TxtRead.readStates();
        ArrayList<Customer> customers = new ArrayList<>();
        HashSet<Long> IDs = new HashSet<>();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(XlsxTest.class.getClassLoader().getResourceAsStream("data.xlsx"));//只能打开XLSX格式的文件
        customers.add(new Customer(1L,null,null,null,null));
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        for (Row row : sheet) { //遍历所有的行
            rowCount++;
            if (rowCount==1){
                continue;
            }
            if (rowCount>=800){
                break;
            }
            double price = row.getCell(5).getNumericCellValue();
            Cell idCell = row.getCell(6);
            if (idCell==null){
                continue;
            }
            Long id = (long) idCell.getNumericCellValue();
            if (price<=0){
                continue;
            }
            if (!IDs.contains(id)){
                IDs.add(id);
                Calendar calendar = Calendar.getInstance(Locale.CHINA);
                calendar.set(Calendar.YEAR,2008);
                if (random.nextBoolean()){
                    calendar.set(Calendar.YEAR,2009);
                }
                calendar.set(Calendar.MONTH,random.nextInt(12));
                calendar.set(Calendar.DAY_OF_MONTH,random.nextInt(28));
                Customer customer = new Customer(id, null,null,null, calendar.getTime());
                if (rowCount>=50){
                    customer.setAddress(states.get(random.nextInt(states.size()))+
                            RandomGen.getRandomString(str,10));
                    customer.setPhone(RandomGen.getRandomString(numbers, 13));
                    customer.setName(StringUtil.upperFirst(RandomGen.getRandomString(str, 6)));
                }
                customers.add(customer);
            }
        }
        return customers;
    }
    public static HashMap<String, BigDecimal> readPrice(){
        HashMap<String, BigDecimal> prices = new HashMap<>();
        HashSet<String> names = new HashSet<>();
        Workbook wk = StreamingReader.builder().rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(XlsxTest.class.getClassLoader().getResourceAsStream("data.xlsx"));//只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        for (Row row : sheet) {
            rowCount++;
            if (rowCount==1){
                continue;
            }
            if (rowCount>=800){
                break;
            }
            String productName = row.getCell(2).getStringCellValue();
            double price = row.getCell(5).getNumericCellValue();
            if (price<=0){
                continue;
            }
            if (!names.contains(productName)){
                String strPrice = row.getCell(5).getStringCellValue();
                prices.put(productName,new BigDecimal(strPrice));
                names.add(productName);
            }
        }
        return prices;
    }
    public static ArrayList<Category>  readCategory(){
        ArrayList<Category> types = new ArrayList<>();
        Workbook wk;
        try {
            wk = new XSSFWorkbook(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("category.xlsx")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        String super1="";
        String super2="";
        for (Row row : sheet) {
            rowCount++;
            if (rowCount==1){
                continue;
            }
            String a = row.getCell(0).getStringCellValue().replace("\n","")
                    .replace(" ","");
            String b = row.getCell(1).getStringCellValue().replace("\n","")
                    .replace(" ","");
            String c = row.getCell(2).getStringCellValue().replace("\n","")
                    .replace(" ","");
            if (!a.equals("")){
                types.add(new Category(a,null));
                super1=a;
            }
            if (!b.equals("")){
                types.add(new Category(b,super1));
                super2=b;
            }
            if (!c.equals("")){
                types.add(new Category(c,super2));
            }
        }
        return types;
    }
    public static ArrayList<Purchase> readPurchase(){
        HashSet<Long> ids = new HashSet<>();
        ArrayList<Purchase> purchases = new ArrayList<>();
        StoreDao storeDao = new StoreDaoImpl(Store.class);
        List<Store> stores = storeDao.queryAll();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(XlsxTest.class.getClassLoader().getResourceAsStream("data.xlsx"));//只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        int storeNum = 0;
        for (Row row : sheet) { //遍历所有的行
            rowCount++;
            if (rowCount==1){
                continue;
            }
            if (rowCount>=800){
                break;
            }
            double price = row.getCell(5).getNumericCellValue();
            if (price<=0){
                continue;
            }
            Long invoice = (long) row.getCell(0).getNumericCellValue();
            if (!ids.contains(invoice)){
                ids.add(invoice);
                Date date = row.getCell(4).getDateCellValue();
                Long customerID = (long) row.getCell(6).getNumericCellValue();
                purchases.add(new Purchase(invoice,stores.get(storeNum).getNo(),customerID,new Timestamp(date.getTime()),true));
                storeNum = (storeNum+1)%stores.size();
            }
        }
        return purchases;
    }
    public static ArrayList<Purchase_List> readPL(){
        ProductDao productDao = new ProductDaoImpl(Product.class);
        ArrayList<Purchase_List> purchaseLists = new ArrayList<>();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(XlsxTest.class.getClassLoader().getResourceAsStream("data.xlsx"));//只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);
        int rowCount = 0;
        for (Row row : sheet) { //遍历所有的行
            rowCount++;
            if (rowCount==1){
                continue;
            }
            if (rowCount>=800){
                break;
            }
            double price = row.getCell(5).getNumericCellValue();
            if (price<=0){
                continue;
            }
            Long invoice = (long) row.getCell(0).getNumericCellValue();
            int quantity = Math.abs((int) row.getCell(3).getNumericCellValue());
            String productName = row.getCell(2).getStringCellValue();
            List<Product> products = productDao.simpleQuery("name", productName);
            assert products.size()>=1;
            int index=0;
            if (products.size()>1){
                index=random.nextInt(0,products.size());
            }
            purchaseLists.add(new Purchase_List(products.get(index).getUPC(),invoice,quantity));
        }
        return purchaseLists;
    }

}
