package cn.sakuraxiafan;

import cn.sakuraxiafan.retailer.dao.CustomerDao;
import cn.sakuraxiafan.retailer.dao.impl.CustomerDaoImpl;
import cn.sakuraxiafan.retailer.entity.Customer;
import cn.sakuraxiafan.retailer.service.CustomerService;
import cn.sakuraxiafan.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import static cn.sakuraxiafan.App.*;

public class CustomerMode {
    private static final CustomerDao customerDao = new CustomerDaoImpl(Customer.class);
    private static Customer currentCustomer = null;
    public static void process(){
        clearScreen();
        boolean flag = true;
        while (flag){
            System.out.println("user "+(currentCustomer==null?"unknown": currentCustomer.getNo())+"\n"+properties.getProperty("customerMode"));
            String input = null;
            while (input==null){
                input = getInput("[0-3]+");
                if (input==null){
                    System.out.println(properties.getProperty("inputError"));
                }else if (input.equals("0")){
                    return;
                }else {
                    int choice = Integer.parseInt(input);
                    switch (choice){
                        case 0 -> flag=false;
                        case 1 -> login();
                        case 2 -> Check();
                        case 3 -> Shopping();
                    }
                }
            }
            clearScreen();
        }
    }
    private static void login(){
        clearScreen();
        while (true){
            System.out.println(properties.getProperty("login"));
            String input = null;
            while (input==null){
                input = getInput("[1-9][0-9]*");//输入要求
                if (input==null){
                    System.out.println(properties.getProperty("inputError"));
                }else if (input.equals("0")){
                    return;
                }else {
                    //处理代码
                    Long inputID = Long.parseLong(input);
                    Customer c = customerDao.queryByNo(inputID);
                    if (c!=null){
                        currentCustomer = c;
                        System.out.println("login success!");
                        return;
                    }else {
                        System.out.println(properties.getProperty("inputError"));
                    }
                }
            }
        }
    }
    private static void Check(){
        clearScreen();
        System.out.println(properties.getProperty("purchaseList"));
        String sql = "SELECT p.purchaseNo,p.purchaseTime,p.storeNo,product.productName,pl.purchaseAmount,sl.sellPrice,sl.sellPrice*pl.purchaseAmount FROM purchase p, purchase_list pl,store_list sl,product\n" +
                "\tWHERE pl.purchaseNo=p.purchaseNo AND sl.storeNo=p.storeNo AND sl.productUPC=pl.productUPC AND product.productUPC=pl.productUPC\n" +
                "\tAND p.customerNo=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = DBUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1,currentCustomer.getNo());
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                long shoppingID = resultSet.getLong(1);
                Date date = resultSet.getDate(2);
                long store = resultSet.getLong(3);
                int amount = resultSet.getInt(5);
                BigDecimal price = resultSet.getBigDecimal(6);
                BigDecimal total = resultSet.getBigDecimal(7);
                String productName = resultSet.getString(4).trim();
                System.out.println(shoppingID+"\t\t"+date+"\t\t"+store+"\t\t"+amount+"\t\t"+price+"\t"+total+"\t"+productName);
            }
            System.out.println(properties.getProperty("back"));;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.closeAll(null,preparedStatement,resultSet);
        }
        String input = null;
        while (input==null) {
            input = getInput("[0]");
            if (input == null) {
                System.out.println(properties.getProperty("inputError"));
            } else if (input.equals("0")) {
                return;
            }else{
                System.out.println(properties.getProperty("inputError"));
            }
        }
    }
    private static void Shopping(){
        clearScreen();
        new CustomerService().initTestStore();
        System.out.println(properties.getProperty("storeList"));
        String sql = "SELECT product.productName,inventory,sellPrice,store_list.productUPC FROM store_list ,product\n" +
                "\tWHERE product.productUPC=store_list.productUPC AND storeNo = 1 AND store_list.productUPC <=5";
        Statement statement = null;
        ResultSet resultSet = null;

        ArrayList<Long> upc = new ArrayList<>();
        while (true){
            try {
                statement=DBUtil.getConnection().createStatement();
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()){
                    String productName = resultSet.getString(1).trim();
                    int inventory = resultSet.getInt(2);
                    BigDecimal price = resultSet.getBigDecimal(3);
                    long productUPC = resultSet.getLong(4);
                    if (upc.size()<5){
                        upc.add(productUPC);
                    }
                    System.out.println(inventory+"\t\t\t"+price+"\t\t"+productName);
                }
                System.out.println("Input the column(1-5) of the product you want buy"+properties.getProperty("back"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DBUtil.closeAll(null,statement,resultSet);
            }
            String input = null;
            while (input==null) {
                input = getInput("[0-5]");//输入要求
                if (input == null) {
                    System.out.println(properties.getProperty("inputError"));
                } else if (input.equals("0")) {
                    return;
                }else{
                    int n = Integer.parseInt(input);
                    new CustomerService().buyProduct(upc.get(n-1));
                }
            }
        }

    }
}
