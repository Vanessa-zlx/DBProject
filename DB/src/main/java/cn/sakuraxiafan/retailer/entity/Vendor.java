package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vendor {
    private String name;

    /**
     *
     * @param name 25
     */
    public Vendor(String name) {
        this.name = name;
    }
}
