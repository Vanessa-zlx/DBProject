package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class Orders {
    private Long no;
    private String vendorName;
    private Long storeNo;
    private Timestamp time;
    private Boolean done;

    /**
     *
     * @param vendorName 25
     * @param done 1
     */
    public Orders(Long no, String vendorName, Long storeNo, Timestamp time, Boolean done) {
        this.no = no;
        this.vendorName = vendorName;
        this.storeNo = storeNo;
        this.time = time;
        this.done = done;
    }
}
