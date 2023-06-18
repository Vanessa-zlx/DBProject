package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Purchase_List {
    private Long productUPC;
    private Long purchaseNo;
    private Integer purchaseAmount;

    public Purchase_List(Long productUPC, Long purchaseNo, Integer purchaseAmount) {
        this.productUPC = productUPC;
        this.purchaseNo = purchaseNo;
        this.purchaseAmount = purchaseAmount;
    }
}
