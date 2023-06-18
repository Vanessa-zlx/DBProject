package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.entity.Orders;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

public class OrdersDaoImpl extends BaseDaoImpl<Orders> {
    public OrdersDaoImpl(Class<Orders> target) {
        super(target);
    }

    public static void main(String[] args) {
        OrdersDaoImpl orderDao = new OrdersDaoImpl(Orders.class);
        Calendar orderTime = Calendar.getInstance(Locale.CHINA);
        orderTime.set(Calendar.DAY_OF_MONTH,4);
        orderTime.set(Calendar.HOUR_OF_DAY,15);
        orderTime.set(Calendar.MINUTE,23);
        orderTime.set(Calendar.SECOND,45);
        System.out.println(orderTime.getTime());
        System.out.println(orderDao.insert(new Orders(null, "康师傅", 2L, new Timestamp(orderTime.getTime().getTime()), true)));
        orderDao.queryAll().forEach(System.out::println);
    }
}
