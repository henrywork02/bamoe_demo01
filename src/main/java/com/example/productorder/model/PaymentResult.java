package com.example.productorder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * PaymentResult 데이터 모델
 * 결제 결과 정보를 담는 클래스
 */
public class PaymentResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Boolean approved;            // 승인 여부
    private String reason;               // 승인/거부 사유
    private String transactionId;        // 거래 ID
    private Date processedDate;          // 처리 일시
    
    /**
     * 기본 생성자
     */
    public PaymentResult() {
    }
    
    /**
     * 전체 필드 생성자
     */
    public PaymentResult(Boolean approved, String reason, String transactionId, Date processedDate) {
        this.approved = approved;
        this.reason = reason;
        this.transactionId = transactionId;
        this.processedDate = processedDate;
    }
    
    // Getters and Setters
    
    public Boolean getApproved() {
        return approved;
    }
    
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public Date getProcessedDate() {
        return processedDate;
    }
    
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentResult that = (PaymentResult) o;
        return Objects.equals(transactionId, that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
    
    @Override
    public String toString() {
        return "PaymentResult{" +
                "approved=" + approved +
                ", reason='" + reason + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", processedDate=" + processedDate +
                '}';
    }
}

// Made with Bob
