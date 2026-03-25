package com.example.bamoe.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Resource Order 엔티티
 * Service Order를 구성하는 리소스 단위 주문
 */
public class ResourceOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 기본 정보
    private String resourceOrderId;
    private String serviceOrderId;
    
    // 리소스 정보
    private String resourceType;        // RESOURCE_TYPE_A, RESOURCE_TYPE_B
    private String resourceDescription;
    private Integer quantity;
    private Double unitPrice;
    private Double totalAmount;
    private String resourceStatus;      // PENDING, ALLOCATED, COMPLETED, FAILED
    
    // 타임스탬프
    private Date createdAt;
    private Date updatedAt;
    
    // 생성자
    public ResourceOrder() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.resourceStatus = "PENDING";
    }
    
    public ResourceOrder(String resourceOrderId, String serviceOrderId, 
                        String resourceType, Integer quantity, Double unitPrice) {
        this();
        this.resourceOrderId = resourceOrderId;
        this.serviceOrderId = serviceOrderId;
        this.resourceType = resourceType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = quantity * unitPrice;
    }
    
    // Getters and Setters
    public String getResourceOrderId() {
        return resourceOrderId;
    }
    
    public void setResourceOrderId(String resourceOrderId) {
        this.resourceOrderId = resourceOrderId;
    }
    
    public String getServiceOrderId() {
        return serviceOrderId;
    }
    
    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getResourceDescription() {
        return resourceDescription;
    }
    
    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        calculateTotalAmount();
    }
    
    public Double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        calculateTotalAmount();
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    private void calculateTotalAmount() {
        if (quantity != null && unitPrice != null) {
            this.totalAmount = quantity * unitPrice;
        }
    }
    
    public String getResourceStatus() {
        return resourceStatus;
    }
    
    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
        this.updatedAt = new Date();
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "ResourceOrder{" +
                "resourceOrderId='" + resourceOrderId + '\'' +
                ", serviceOrderId='" + serviceOrderId + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalAmount=" + totalAmount +
                ", resourceStatus='" + resourceStatus + '\'' +
                '}';
    }
}

// Made with Bob
