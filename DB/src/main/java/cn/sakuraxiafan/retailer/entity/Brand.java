package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Brand {
    private String name;
    private String company;

    /**
     *
     * @param name 15
     * @param company 25
     */
    public Brand(String name, String company) {
        this.name = name;
        this.company = company;
    }
}
