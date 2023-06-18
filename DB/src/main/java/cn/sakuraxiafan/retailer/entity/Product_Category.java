package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product_Category {
    private Long productUPC;
    private String typeName;

    /**
     *
     * @param typeName 10
     */
    public Product_Category(Long productUPC, String typeName) {
        this.productUPC = productUPC;
        this.typeName = typeName;
    }
}
