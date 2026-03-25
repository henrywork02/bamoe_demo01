package com.example.productorder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * ResourceOrder 데이터 모델
 * 리소스 주문 정보를 담는 클래스
 */
public class ResourceOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String resourceOrderId;      // 리소스 주문 ID
    private String serviceOrderId;       // 서비스 주문 ID
    private String resourceType;         // 리소스 유형
    private String resourceName;         // 리소스명
    private String status;               // 상태
    private Date assignedDate;           // 할당 일시
    
    /**
     * 기본 생성자
     */
    public ResourceOrder() {
    }
    
    /**
     * 전체 필드 생성자
     */
    public ResourceOrder(String resourceOrderId, String serviceOrderId, String resourceType, 
                        String resourceName, String status, Date assignedDate) {
        this.resourceOrderId = resourceOrderId;
        this.serviceOrderId = serviceOrderId;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.status = status;
        this.assignedDate = assignedDate;
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
    
    public String getResourceName() {
        return resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getAssignedDate() {
        return assignedDate;
    }
    
    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceOrder that = (ResourceOrder) o;
        return Objects.equals(resourceOrderId, that.resourceOrderId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(resourceOrderId);
    }
    
    @Override
    public String toString() {
        return "ResourceOrder{" +
                "resourceOrderId='" + resourceOrderId + '\'' +
                ", serviceOrderId='" + serviceOrderId + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", status='" + status + '\'' +
                ", assignedDate=" + assignedDate +
                '}';
    }
}

// Made with Bob
