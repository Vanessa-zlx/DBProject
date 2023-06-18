package cn.sakuraxiafan.retailer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Orders_List {
    private Long productUPC;
    private Long orderNo;
    private Integer orderAmount;
    private BigDecimal costPrice;

    /**
     *
     * @param costPrice 8 4 进价(单价)
     */
    public Orders_List(Long productUPC, Long orderNo, Integer orderAmount, BigDecimal costPrice) {
        this.productUPC = productUPC;
        this.orderNo = orderNo;
        this.orderAmount = orderAmount;
        this.costPrice = costPrice;
    }
}
