package com.example.productorder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * ServiceOrder 데이터 모델
 * 서비스 주문 정보를 담는 클래스
 */
public class ServiceOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String serviceOrderId;       // 서비스 주문 ID
    private String productOrderId;       // 제품 주문 ID
    private String serviceType;          // 서비스 유형
    private String status;               // 상태
    private List<ResourceOrder> resourceOrders; // 리소스 주문 리스트
    private Date createdDate;            // 생성 일시
    
    /**
     * 기본 생성자
     */
    public ServiceOrder() {
        this.resourceOrders = new ArrayList<>();
    }
    
    /**
     * 전체 필드 생성자
     */
    public ServiceOrder(String serviceOrderId, String productOrderId, String serviceType, 
                       String status, List<ResourceOrder> resourceOrders, Date createdDate) {
        this.serviceOrderId = serviceOrderId;
        this.productOrderId = productOrderId;
        this.serviceType = serviceType;
        this.status = status;
        this.resourceOrders = resourceOrders != null ? resourceOrders : new ArrayList<>();
        this.createdDate = createdDate;
    }
    
    // Getters and Setters
    
    public String getServiceOrderId() {
        return serviceOrderId;
    }
    
    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }
    
    public String getProductOrderId() {
        return productOrderId;
    }
    
    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<ResourceOrder> getResourceOrders() {
        return resourceOrders;
    }
    
    public void setResourceOrders(List<ResourceOrder> resourceOrders) {
        this.resourceOrders = resourceOrders;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceOrder that = (ServiceOrder) o;
        return Objects.equals(serviceOrderId, that.serviceOrderId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(serviceOrderId);
    }
    
    @Override
    public String toString() {
        return "ServiceOrder{" +
                "serviceOrderId='" + serviceOrderId + '\'' +
                ", productOrderId='" + productOrderId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", status='" + status + '\'' +
                ", resourceOrders=" + resourceOrders +
                ", createdDate=" + createdDate +
                '}';
    }
}

// Made with Bob
