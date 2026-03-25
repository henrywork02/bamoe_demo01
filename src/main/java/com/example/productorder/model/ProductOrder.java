package com.example.productorder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * ProductOrder 데이터 모델
 * 제품 주문 정보를 담는 클래스
 */
public class ProductOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 기본 정보
    private String orderId;              // 주문 ID
    private String customerName;         // 고객명
    private String customerType;         // 고객 유형: "VIP", "REGULAR", "NEW"
    private Date orderDate;              // 주문 일자
    
    // 결제 정보
    private Double totalAmount;          // 총 주문 금액
    private Integer creditScore;         // 신용 점수
    
    // 주문 상태
    private String status;               // 주문 상태
    
    // 주문 항목
    private List<ProductItem> items;     // 주문 항목 리스트
    
    /**
     * 기본 생성자
     */
    public ProductOrder() {
        this.items = new ArrayList<>();
    }
    
    /**
     * 전체 필드 생성자
     */
    public ProductOrder(String orderId, String customerName, String customerType, 
                       List<ProductItem> items, Double totalAmount, Integer creditScore, 
                       Date orderDate, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerType = customerType;
        this.items = items != null ? items : new ArrayList<>();
        this.totalAmount = totalAmount;
        this.creditScore = creditScore;
        this.orderDate = orderDate;
        this.status = status;
    }
    
    // Getters and Setters
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Integer getCreditScore() {
        return creditScore;
    }
    
    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<ProductItem> getItems() {
        return items;
    }
    
    public void setItems(List<ProductItem> items) {
        this.items = items;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return Objects.equals(orderId, that.orderId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
    
    @Override
    public String toString() {
        return "ProductOrder{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerType='" + customerType + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", creditScore=" + creditScore +
                ", status='" + status + '\'' +
                ", items=" + items +
                '}';
    }
}

// Made with Bob
