package com.example.productorder.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * ProductItem 데이터 모델
 * 주문 항목 정보를 담는 클래스
 */
public class ProductItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String itemId;               // 항목 ID
    private String productName;          // 제품명
    private Integer quantity;            // 수량
    private Double unitPrice;            // 단가
    private Double amount;               // 금액
    
    /**
     * 기본 생성자
     */
    public ProductItem() {
    }
    
    /**
     * 전체 필드 생성자
     */
    public ProductItem(String itemId, String productName, Integer quantity, 
                      Double unitPrice, Double amount) {
        this.itemId = itemId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }
    
    // Getters and Setters
    
    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem that = (ProductItem) o;
        return Objects.equals(itemId, that.itemId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
    
    @Override
    public String toString() {
        return "ProductItem{" +
                "itemId='" + itemId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", amount=" + amount +
                '}';
    }
}

// Made with Bob
