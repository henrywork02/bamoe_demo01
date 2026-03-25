package com.example.bamoe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Order 엔티티
 * Product Order를 구성하는 서비스 단위 주문
 */
public class ServiceOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 기본 정보
    private String serviceOrderId;
    private String productOrderId;
    
    // 서비스 정보
    private String serviceType;        // SERVICE_TYPE_1, SERVICE_TYPE_2
    private String serviceDescription;
    private Double serviceAmount;
    private String serviceStatus;      // PENDING, IN_PROGRESS, COMPLETED, FAILED
    
    // 관계
    private List<ResourceOrder> resourceOrders;
    
    // 프로세스 관련
    private String approvalComments;
    
    // 타임스탬프
    private Date createdAt;
    private Date updatedAt;
    
    // 생성자
    public ServiceOrder() {
        this.resourceOrders = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.serviceStatus = "PENDING";
    }
    
    public ServiceOrder(String serviceOrderId, String productOrderId, 
                       String serviceType, Double serviceAmount) {
        this();
        this.serviceOrderId = serviceOrderId;
        this.productOrderId = productOrderId;
        this.serviceType = serviceType;
        this.serviceAmount = serviceAmount;
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
    
    public String getServiceDescription() {
        return serviceDescription;
    }
    
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    
    public Double getServiceAmount() {
        return serviceAmount;
    }
    
    public void setServiceAmount(Double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }
    
    public String getServiceStatus() {
        return serviceStatus;
    }
    
    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        this.updatedAt = new Date();
    }
    
    public List<ResourceOrder> getResourceOrders() {
        return resourceOrders;
    }
    
    public void setResourceOrders(List<ResourceOrder> resourceOrders) {
        this.resourceOrders = resourceOrders;
    }
    
    public void addResourceOrder(ResourceOrder resourceOrder) {
        this.resourceOrders.add(resourceOrder);
        resourceOrder.setServiceOrderId(this.serviceOrderId);
    }
    
    public String getApprovalComments() {
        return approvalComments;
    }
    
    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
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
        return "ServiceOrder{" +
                "serviceOrderId='" + serviceOrderId + '\'' +
                ", productOrderId='" + productOrderId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", serviceAmount=" + serviceAmount +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", resourceOrdersCount=" + (resourceOrders != null ? resourceOrders.size() : 0) +
                '}';
    }
}

// Made with Bob
