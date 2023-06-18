package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Purchase {
    private Long no;
    private Long storeNo;
    private Long customerNo;
    private Timestamp time;
    private Boolean onlinePurchase;

    public Purchase(Long no, Long storeNo, Long customerNo, Timestamp time, Boolean onlinePurchase) {
        this.no = no;
        this.storeNo = storeNo;
        this.customerNo = customerNo;
        this.time = time;
        this.onlinePurchase = onlinePurchase;
    }
}
