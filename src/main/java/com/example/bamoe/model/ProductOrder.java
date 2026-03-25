package com.example.bamoe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Product Order 엔티티
 * 고객의 주문 정보를 담는 최상위 객체
 */
public class ProductOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 기본 정보
    private String productOrderId;
    private String customerId;
    private String customerName;
    private String customerGrade;  // VIP, GOLD, SILVER, BRONZE
    private Double customerCreditLimit;
    
    // 주문 정보
    private Date orderDate;
    private Double orderAmount;
    private String paymentMethod;  // CREDIT_CARD, BANK_TRANSFER, CASH
    private String orderStatus;    // PENDING, VALIDATED, PAYMENT_GENERATED, etc.
    
    // 관계
    private List<ServiceOrder> serviceOrders;
    
    // 프로세스 관련
    private String validationComments;
    private String approvalComments;
    
    // 타임스탬프
    private Date createdAt;
    private Date updatedAt;
    
    // 생성자
    public ProductOrder() {
        this.serviceOrders = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    
    public ProductOrder(String productOrderId, String customerId, String customerName, 
                       String customerGrade, Double orderAmount, String paymentMethod) {
        this();
        this.productOrderId = productOrderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerGrade = customerGrade;
        this.orderAmount = orderAmount;
        this.paymentMethod = paymentMethod;
        this.orderDate = new Date();
        this.orderStatus = "PENDING";
    }
    
    // Getters and Setters
    public String getProductOrderId() {
        return productOrderId;
    }
    
    public void setProductOrderId(String productOrderId) {
        this.productOrderId = productOrderId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerGrade() {
        return customerGrade;
    }
    
    public void setCustomerGrade(String customerGrade) {
        this.customerGrade = customerGrade;
    }
    
    public Double getCustomerCreditLimit() {
        return customerCreditLimit;
    }
    
    public void setCustomerCreditLimit(Double customerCreditLimit) {
        this.customerCreditLimit = customerCreditLimit;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Double getOrderAmount() {
        return orderAmount;
    }
    
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        this.updatedAt = new Date();
    }
    
    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }
    
    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
    
    public void addServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.add(serviceOrder);
        serviceOrder.setProductOrderId(this.productOrderId);
    }
    
    public String getValidationComments() {
        return validationComments;
    }
    
    public void setValidationComments(String validationComments) {
        this.validationComments = validationComments;
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
        return "ProductOrder{" +
                "productOrderId='" + productOrderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerGrade='" + customerGrade + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", serviceOrdersCount=" + (serviceOrders != null ? serviceOrders.size() : 0) +
                '}';
    }
}

// Made with Bob
