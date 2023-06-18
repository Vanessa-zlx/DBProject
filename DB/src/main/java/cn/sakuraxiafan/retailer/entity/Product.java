package cn.sakuraxiafan.retailer.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
public class Product {
    private Long UPC;
    private String name;
    private String packaging;
    @With(AccessLevel.PUBLIC)
    private String size;
    private String brand;

    /**
     *
     * @param UPC int 2147483647
     * @param name 15
     * @param packaging 10
     * @param size 10
     * @param brand 15
     */
    public Product(Long UPC, String name, String packaging, String size, String brand) {
        this.UPC = UPC;
        this.name = name;
        this.packaging = packaging;
        this.size = size;
        this.brand = brand;
    }
}
