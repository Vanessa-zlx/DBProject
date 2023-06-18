package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Customer {
    private Long no;
    private String name;
    private String phone;
    private String address;
    private Date registerDate;

    /**
     *
     * @param id customerNo auto incr 1000001+
     * @param name customerName 15
     * @param phone customerPhone 13
     * @param address ,customerAddress 50
     * @param registerDate registerDate
     */
    public Customer(Long id, String name, String phone, String address, Date registerDate) {
        this.no = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.registerDate = registerDate;
    }


}
