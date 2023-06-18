package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.StoreDao;
import cn.sakuraxiafan.retailer.entity.Store;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StoreDaoImpl extends BaseDaoImpl<Store> implements StoreDao {
    public StoreDaoImpl(Class<Store> target) {
        super(target);
    }

    public static void main(String[] args) {
        StoreDaoImpl storeDao = new StoreDaoImpl(Store.class);
        Calendar openingTime = Calendar.getInstance(Locale.CHINA);
        openingTime.set(Calendar.HOUR_OF_DAY,9);
        openingTime.set(Calendar.MINUTE,0);
        openingTime.set(Calendar.SECOND,0);

        Calendar closingTime = Calendar.getInstance(Locale.CHINA);
        closingTime.set(Calendar.HOUR_OF_DAY,21);
        closingTime.set(Calendar.MINUTE,30);
        closingTime.set(Calendar.SECOND,0);

        System.out.println(new Time(openingTime.getTime().getTime()));
        System.out.println(new Time(closingTime.getTime().getTime()));
        System.out.println(storeDao.insert(new Store(null, "zkjfkda",
                new Time(openingTime.getTime().getTime()), new Time(closingTime.getTime().getTime()))));
        storeDao.queryAll().forEach(System.out::println);
    }
}
