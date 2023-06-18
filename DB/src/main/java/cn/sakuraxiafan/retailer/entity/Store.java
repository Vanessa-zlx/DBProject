package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
public class Store {
    private Long no;
    private String location;
    private Time openingTime;
    private Time closingTime;

    /**
     *
     * @param location 50
     */
    public Store(Long no, String location, Time openingTime, Time closingTime) {
        this.no = no;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
