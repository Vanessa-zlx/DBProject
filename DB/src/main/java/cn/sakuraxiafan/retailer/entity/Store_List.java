package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class Store_List {

    private Long storeNo;
    private Long productUPC;
    private Integer inventory;
    private BigDecimal sellPrice;

    /**
     *
     * @param inventory 库存
     * @param sellPrice 6 4 售价
     */
    public Store_List(Long storeNo, Long productUPC, Integer inventory, BigDecimal sellPrice) {
        this.storeNo = storeNo;
        this.productUPC = productUPC;
        this.inventory = inventory;
        this.sellPrice = sellPrice;
    }
}
