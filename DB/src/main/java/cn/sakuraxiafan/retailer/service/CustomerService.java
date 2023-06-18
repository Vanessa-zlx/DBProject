package cn.sakuraxiafan.retailer.service;

import cn.sakuraxiafan.retailer.dao.CustomerDao;
import cn.sakuraxiafan.retailer.dao.PurchaseDao;
import cn.sakuraxiafan.retailer.dao.Purchase_ListDao;
import cn.sakuraxiafan.retailer.dao.impl.CustomerDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.PurchaseDaoImpl;
import cn.sakuraxiafan.retailer.dao.impl.Purchase_ListDaoImpl;
import cn.sakuraxiafan.retailer.entity.Customer;
import cn.sakuraxiafan.retailer.entity.Purchase;
import cn.sakuraxiafan.retailer.entity.Purchase_List;
import cn.sakuraxiafan.util.DBUtil;
import cn.sakuraxiafan.util.Transaction;
import cn.sakuraxiafan.retailer.service.CustomerService;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CustomerService {
    private final CustomerDaoImpl customerDao;

    public CustomerService() {
        customerDao = new CustomerDaoImpl(Customer.class);
    }

    public Customer queryByNo(Long no) {
        return customerDao.queryByNo(no);
    }

    public List<Purchase> getPurchase(Customer customer) {
        Transaction.begin();
        PurchaseDao purchaseDao = new PurchaseDaoImpl(Purchase.class);
        try {
            return purchaseDao.simpleQuery("customerNo", customer.getNo());
        } catch (Exception e) {
            e.printStackTrace();
            Transaction.rollback();
        } finally {
            Transaction.close();
        }
        return null;
    }


    /**
     * 购买
     */
    public boolean buyProduct(Long UPC) {
        Transaction.begin();
        Statement statement = null;
        try {

            String sql = "UPDATE store_list\n" +
                    "set store_list.inventory=store_list.inventory-50\n" +
                    "WHERE store_list.storeNo = 1 AND productUPC="+UPC+" AND store_list.inventory>=50";
            statement=DBUtil.getConnection().createStatement();
            int i = statement.executeUpdate(sql);
            if (i!=1){
                Transaction.rollback();
                return false;
            }

            Purchase purchase = new Purchase(1L, 1L, 1L, new Timestamp(new Date().getTime()), true);
            PurchaseDaoImpl purchaseDao = new PurchaseDaoImpl(Purchase.class);
            if (purchaseDao.simpleQuery("no",1L).size()==0){
                purchaseDao.insert(purchase);
            }


            Purchase_List purchaseList = new Purchase_List(UPC, 1L, 50);
            Purchase_ListDaoImpl purchaseListDao = new Purchase_ListDaoImpl(Purchase_List.class);
            purchaseListDao.simpleDelete("purchaseNo",1L);
            purchaseListDao.insert(purchaseList);

            Transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Transaction.rollback();
            return false;
        } finally {
            Transaction.close();
        }
    }

    public void initTestStore(){
        Statement statement = null;
        String sql = "UPDATE store_list\n" +
                "set store_list.inventory=100\n" +
                "WHERE store_list.storeNo = 1 AND productUPC <= 5";
        try {
            statement=DBUtil.getConnection().createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtil.closeAll(null,statement,null);
        }
    }

    public static void main(String[] args) {
        CustomerService service = new CustomerService();
        service.getPurchase(service.queryByNo(16011L)).forEach(System.out::println);
    }
}
