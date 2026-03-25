package com.example.bamoe.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Payment Info 엔티티
 * DMN 결제 발번 결과를 담는 객체
 */
public class PaymentInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 결제 정보
    private String paymentId;
    private Double paymentAmount;
    private Double discountRate;
    private Boolean paymentValid;
    
    // 원본 주문 정보
    private Double orderAmount;
    private String customerId;
    private String customerGrade;
    private String paymentMethod;
    
    // 타임스탬프
    private Date createdAt;
    
    // 생성자
    public PaymentInfo() {
        this.createdAt = new Date();
        this.paymentValid = false;
    }
    
    public PaymentInfo(String paymentId, Double paymentAmount, 
                      Double discountRate, Boolean paymentValid) {
        this();
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.discountRate = discountRate;
        this.paymentValid = paymentValid;
    }
    
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    
    public Double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public Double getDiscountRate() {
        return discountRate;
    }
    
    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }
    
    public Boolean getPaymentValid() {
        return paymentValid;
    }
    
    public void setPaymentValid(Boolean paymentValid) {
        this.paymentValid = paymentValid;
    }
    
    public Double getOrderAmount() {
        return orderAmount;
    }
    
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerGrade() {
        return customerGrade;
    }
    
    public void setCustomerGrade(String customerGrade) {
        this.customerGrade = customerGrade;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 할인 금액 계산
     */
    public Double getDiscountAmount() {
        if (orderAmount != null && discountRate != null) {
            return orderAmount * (discountRate / 100.0);
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        return "PaymentInfo{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", discountRate=" + discountRate +
                ", paymentValid=" + paymentValid +
                ", orderAmount=" + orderAmount +
                ", customerGrade='" + customerGrade + '\'' +
                '}';
    }
}

// Made with Bob
