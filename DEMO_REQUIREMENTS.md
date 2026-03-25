# Product Order 워크플로우 데모 프로젝트 요구사항

## 1. 프로젝트 개요

### 1.1 데모 목적 및 범위
본 프로젝트는 IBM BAMOE (Business Automation Manager Open Edition) Extension의 Accelerator를 활용하여 제품 주문 처리 워크플로우를 구현하는 데모입니다. 복잡한 비즈니스 프로세스를 BPMN과 DMN을 통해 모델링하고, 병렬 처리 및 의사결정 자동화를 시연합니다.

**주요 특징**:
- 제품 주문(Product Order) 접수부터 완료까지의 전체 라이프사이클 관리
- DMN 기반 자동 결제 검증
- 병렬 서비스 주문(Service Order) 처리
- 각 서비스 주문 내 병렬 리소스 주문(Resource Order) 처리

### 1.2 IBM BAMOE Accelerator 소개
IBM BAMOE Accelerator는 비즈니스 자동화 프로젝트를 빠르게 시작할 수 있도록 사전 구성된 프로젝트 템플릿을 제공합니다.

**주요 기능**:
- Maven/Quarkus 기반 프로젝트 구조 자동 생성
- BPMN 및 DMN 에디터 통합
- 로컬 개발 환경 설정 자동화
- REST API 엔드포인트 자동 생성

**개발자가 집중할 영역**:
1. BPMN 파일 (비즈니스 프로세스 정의)
2. DMN 파일 (비즈니스 룰/결정 테이블)
3. Java 파일 (데이터 모델 및 비즈니스 로직)

---

## 2. 필요한 에셋 목록

### 2.1 BPMN 파일

#### 파일 정보
- **파일명**: `product-order-workflow.bpmn`
- **위치**: `src/main/resources/`
- **프로세스 ID**: `productOrderProcess`
- **프로세스 이름**: `Product Order Workflow`

#### BPMN 요소 목록

##### 2.1.1 시작/종료 이벤트
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `StartEvent_1` | Product Order 접수 | Start Event | 프로세스 시작점 |
| `EndEvent_Success` | Product Order 완료 | End Event | 정상 완료 |
| `EndEvent_PaymentFailed` | 결제 실패 종료 | End Event | 결제 실패 시 조기 종료 |

##### 2.1.2 태스크 (Tasks)
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `Task_ValidateOrder` | Product Order 검증 | Service Task | 주문 유효성 검증 |
| `Task_DistributeSO` | Service Order 분배 | Service Task | SO 생성 및 할당 |
| `Task_FinalApproval` | Product Order 최종 승인 | User Task | 최종 승인 처리 |

##### 2.1.3 게이트웨이 (Gateways)
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `Gateway_PaymentCheck` | 결제 결과 | Exclusive Gateway (XOR) | 결제 성공/실패 분기 |
| `Gateway_SOParallelStart` | SO 병렬 시작 | Parallel Gateway (AND) | SO1, SO2 병렬 시작 |
| `Gateway_SOParallelEnd` | SO 완료 대기 | Parallel Gateway (AND) | SO1, SO2 완료 동기화 |

##### 2.1.4 서브프로세스 (Sub-processes)

**Service Order 1 처리**
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `SubProcess_SO1` | Service Order 1 처리 | Sub-Process | SO1 전체 프로세스 |
| `Task_SO1_Start` | SO1 시작 | Service Task | SO1 초기화 |
| `Gateway_RO1_ParallelStart` | RO 병렬 시작 | Parallel Gateway | RO1-1, RO1-2 병렬 시작 |
| `Task_RO1_1` | Resource Order 1-1 | Service Task | 리소스 주문 1-1 처리 |
| `Task_RO1_2` | Resource Order 1-2 | Service Task | 리소스 주문 1-2 처리 |
| `Gateway_RO1_ParallelEnd` | RO 완료 대기 | Parallel Gateway | RO1-1, RO1-2 완료 동기화 |
| `Task_SO1_Approval` | SO1 승인 | User Task | SO1 승인 처리 |
| `Task_SO1_Complete` | SO1 완료 | Service Task | SO1 완료 처리 |

**Service Order 2 처리**
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `SubProcess_SO2` | Service Order 2 처리 | Sub-Process | SO2 전체 프로세스 |
| `Task_SO2_Start` | SO2 시작 | Service Task | SO2 초기화 |
| `Gateway_RO2_ParallelStart` | RO 병렬 시작 | Parallel Gateway | RO2-1, RO2-2 병렬 시작 |
| `Task_RO2_1` | Resource Order 2-1 | Service Task | 리소스 주문 2-1 처리 |
| `Task_RO2_2` | Resource Order 2-2 | Service Task | 리소스 주문 2-2 처리 |
| `Gateway_RO2_ParallelEnd` | RO 완료 대기 | Parallel Gateway | RO2-1, RO2-2 완료 동기화 |
| `Task_SO2_Complete` | SO2 완료 | Service Task | SO2 완료 처리 |

##### 2.1.5 비즈니스 룰 태스크 (Business Rule Task)
| 요소 ID | 요소 이름 | 타입 | 설명 |
|---------|-----------|------|------|
| `Task_PaymentDecision` | 결제 발급 | Business Rule Task | DMN 결정 호출 |

**속성 설정**:
- **Implementation**: DMN
- **Decision Ref**: `paymentDecision`
- **Input Variable**: `productOrder`
- **Output Variable**: `paymentResult`

##### 2.1.6 시퀀스 플로우 (Sequence Flows)
| From | To | 조건 |
|------|-----|------|
| `StartEvent_1` | `Task_ValidateOrder` | - |
| `Task_ValidateOrder` | `Task_PaymentDecision` | - |
| `Task_PaymentDecision` | `Gateway_PaymentCheck` | - |
| `Gateway_PaymentCheck` | `Task_DistributeSO` | `${paymentResult.approved == true}` |
| `Gateway_PaymentCheck` | `EndEvent_PaymentFailed` | `${paymentResult.approved == false}` |
| `Task_DistributeSO` | `Gateway_SOParallelStart` | - |
| `Gateway_SOParallelStart` | `SubProcess_SO1` | - |
| `Gateway_SOParallelStart` | `SubProcess_SO2` | - |
| `SubProcess_SO1` | `Gateway_SOParallelEnd` | - |
| `SubProcess_SO2` | `Gateway_SOParallelEnd` | - |
| `Gateway_SOParallelEnd` | `Task_FinalApproval` | - |
| `Task_FinalApproval` | `EndEvent_Success` | - |

##### 2.1.7 프로세스 변수 (Process Variables)
| 변수명 | 타입 | 설명 |
|--------|------|------|
| `productOrder` | `com.example.model.ProductOrder` | 제품 주문 정보 |
| `paymentResult` | `com.example.model.PaymentResult` | 결제 결과 |
| `serviceOrders` | `List<com.example.model.ServiceOrder>` | 서비스 주문 목록 |

---

### 2.2 DMN 파일

#### 파일 정보
- **파일명**: `payment-decision.dmn`
- **위치**: `src/main/resources/`
- **Decision ID**: `paymentDecision`
- **Decision 이름**: `Payment Decision`

#### 결정 테이블 구조

##### 입력 변수 (Input)
| 입력 ID | 입력 이름 | 타입 | 표현식 |
|---------|-----------|------|--------|
| `input_amount` | Order Amount | number | `productOrder.totalAmount` |
| `input_customer` | Customer Type | string | `productOrder.customerType` |
| `input_credit` | Credit Score | number | `productOrder.creditScore` |

##### 출력 변수 (Output)
| 출력 ID | 출력 이름 | 타입 |
|---------|-----------|------|
| `output_approved` | Approved | boolean |
| `output_reason` | Reason | string |

##### 결정 로직 규칙 (Decision Rules)

| Rule | Order Amount | Customer Type | Credit Score | Approved | Reason |
|------|--------------|---------------|--------------|----------|--------|
| 1 | < 100000 | - | >= 600 | true | "자동 승인 - 소액 주문" |
| 2 | >= 100000 | "VIP" | >= 700 | true | "자동 승인 - VIP 고객" |
| 3 | >= 100000 | "REGULAR" | >= 750 | true | "자동 승인 - 우수 신용" |
| 4 | >= 100000 | "REGULAR" | < 750 | false | "거부 - 신용 점수 부족" |
| 5 | >= 100000 | "NEW" | - | false | "거부 - 신규 고객 고액 주문" |
| 6 | - | - | < 600 | false | "거부 - 낮은 신용 점수" |

**Hit Policy**: FIRST (첫 번째 매칭 규칙 적용)

---

### 2.3 Java 파일

#### 2.3.1 데이터 모델 클래스

##### ProductOrder.java
**위치**: `src/main/java/com/example/model/ProductOrder.java`

```java
package com.example.model;

import java.util.Date;
import java.util.List;

public class ProductOrder {
    
    // 기본 정보
    private String orderId;              // 주문 ID
    private String customerId;           // 고객 ID
    private String customerType;         // 고객 유형 (VIP, REGULAR, NEW)
    private Date orderDate;              // 주문 일시
    
    // 결제 정보
    private Double totalAmount;          // 총 주문 금액
    private Integer creditScore;         // 신용 점수
    private String paymentMethod;        // 결제 수단
    
    // 주문 상태
    private String status;               // 주문 상태 (PENDING, VALIDATED, PAYMENT_APPROVED, PAYMENT_FAILED, COMPLETED)
    
    // 제품 정보
    private List<ProductItem> items;     // 주문 제품 목록
    
    // 서비스 주문 참조
    private List<String> serviceOrderIds; // 생성된 서비스 주문 ID 목록
    
    // Getters and Setters
    // Constructor
    // toString()
}
```

##### ProductItem.java
**위치**: `src/main/java/com/example/model/ProductItem.java`

```java
package com.example.model;

public class ProductItem {
    
    private String productId;            // 제품 ID
    private String productName;          // 제품명
    private Integer quantity;            // 수량
    private Double unitPrice;            // 단가
    private Double totalPrice;           // 총액
    
    // Getters and Setters
    // Constructor
    // toString()
}
```

##### PaymentResult.java
**위치**: `src/main/java/com/example/model/PaymentResult.java`

```java
package com.example.model;

import java.util.Date;

public class PaymentResult {
    
    private Boolean approved;            // 승인 여부
    private String reason;               // 승인/거부 사유
    private String transactionId;        // 거래 ID
    private Date processedDate;          // 처리 일시
    
    // Getters and Setters
    // Constructor
    // toString()
}
```

##### ServiceOrder.java
**위치**: `src/main/java/com/example/model/ServiceOrder.java`

```java
package com.example.model;

import java.util.Date;
import java.util.List;

public class ServiceOrder {
    
    // 기본 정보
    private String serviceOrderId;       // 서비스 주문 ID
    private String productOrderId;       // 상위 제품 주문 ID
    private String serviceType;          // 서비스 유형 (SO1, SO2)
    private Date createdDate;            // 생성 일시
    
    // 상태 정보
    private String status;               // 상태 (CREATED, IN_PROGRESS, APPROVED, COMPLETED)
    
    // 리소스 주문 참조
    private List<String> resourceOrderIds; // 생성된 리소스 주문 ID 목록
    
    // 승인 정보 (SO1만 해당)
    private Boolean requiresApproval;    // 승인 필요 여부
    private String approver;             // 승인자
    private Date approvedDate;           // 승인 일시
    
    // Getters and Setters
    // Constructor
    // toString()
}
```

##### ResourceOrder.java
**위치**: `src/main/java/com/example/model/ResourceOrder.java`

```java
package com.example.model;

import java.util.Date;

public class ResourceOrder {
    
    // 기본 정보
    private String resourceOrderId;      // 리소스 주문 ID
    private String serviceOrderId;       // 상위 서비스 주문 ID
    private String resourceType;         // 리소스 유형 (RO1-1, RO1-2, RO2-1, RO2-2)
    private Date createdDate;            // 생성 일시
    
    // 리소스 상세
    private String resourceName;         // 리소스명
    private String resourceSpec;         // 리소스 사양
    
    // 상태 정보
    private String status;               // 상태 (CREATED, PROCESSING, COMPLETED, FAILED)
    private Date completedDate;          // 완료 일시
    
    // Getters and Setters
    // Constructor
    // toString()
}
```

#### 2.3.2 비즈니스 로직 클래스 (선택사항)

##### OrderValidationService.java
**위치**: `src/main/java/com/example/service/OrderValidationService.java`

```java
package com.example.service;

import com.example.model.ProductOrder;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderValidationService {
    
    public boolean validateOrder(ProductOrder order) {
        // 주문 유효성 검증 로직
        // - 필수 필드 확인
        // - 금액 검증
        // - 제품 재고 확인 등
        return true;
    }
}
```

##### ServiceOrderDistributionService.java
**위치**: `src/main/java/com/example/service/ServiceOrderDistributionService.java`

```java
package com.example.service;

import com.example.model.ProductOrder;
import com.example.model.ServiceOrder;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ServiceOrderDistributionService {
    
    public List<ServiceOrder> distributeServiceOrders(ProductOrder productOrder) {
        // 제품 주문을 기반으로 서비스 주문 생성
        // - SO1, SO2 생성
        // - 각 SO에 리소스 주문 할당
        return List.of();
    }
}
```

---

## 3. BAMOE Accelerator 프로젝트 설정

### 3.1 VS Code에서 BAMOE Extension 사용

#### 전제 조건
- VS Code 설치
- IBM BAMOE Extension 설치
- Java 11+ 설치
- Maven 3.8+ 설치

#### Extension 설치
1. VS Code Extensions 마켓플레이스에서 "IBM BAMOE" 검색
2. "IBM Business Automation Manager Open Edition" 설치
3. VS Code 재시작

### 3.2 Accelerator를 통한 프로젝트 생성 단계

#### Step 1: Accelerator 실행
1. VS Code 명령 팔레트 열기 (`Cmd+Shift+P` 또는 `Ctrl+Shift+P`)
2. "BAMOE: Create New Project" 입력 및 선택
3. 또는 BAMOE 사이드바에서 "Create New Project" 클릭

#### Step 2: 프로젝트 설정
- **Project Name**: `bamoe-product-order-demo`
- **Group ID**: `com.example`
- **Artifact ID**: `product-order-workflow`
- **Version**: `1.0.0-SNAPSHOT`
- **Package**: `com.example`
- **Project Type**: Quarkus
- **Target Directory**: 원하는 위치 선택

#### Step 3: 프로젝트 생성 확인
Accelerator가 자동으로 생성하는 항목:
- Maven `pom.xml` (필요한 의존성 포함)
- Quarkus 설정 파일 (`application.properties`)
- 기본 디렉토리 구조
- 샘플 BPMN/DMN 파일 (선택사항)

### 3.3 기본 프로젝트 구조

```
bamoe-product-order-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── model/              # 데이터 모델
│   │   │           │   ├── ProductOrder.java
│   │   │           │   ├── ProductItem.java
│   │   │           │   ├── PaymentResult.java
│   │   │           │   ├── ServiceOrder.java
│   │   │           │   └── ResourceOrder.java
│   │   │           └── service/            # 비즈니스 로직
│   │   │               ├── OrderValidationService.java
│   │   │               └── ServiceOrderDistributionService.java
│   │   └── resources/
│   │       ├── product-order-workflow.bpmn # BPMN 프로세스
│   │       ├── payment-decision.dmn        # DMN 결정 테이블
│   │       └── application.properties      # Quarkus 설정
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── ProcessTest.java    # 프로세스 테스트
├── pom.xml                                 # Maven 설정
└── README.md                               # 프로젝트 문서
```

---

## 4. 구현 체크리스트

### Phase 1: 프로젝트 초기화
- [ ] VS Code에 IBM BAMOE Extension 설치
- [ ] BAMOE Accelerator로 프로젝트 생성
- [ ] 프로젝트 구조 확인
- [ ] Maven 빌드 테스트 (`mvn clean compile`)

### Phase 2: 데이터 모델 구현
- [ ] `ProductOrder.java` 작성
- [ ] `ProductItem.java` 작성
- [ ] `PaymentResult.java` 작성
- [ ] `ServiceOrder.java` 작성
- [ ] `ResourceOrder.java` 작성
- [ ] 모든 클래스에 Getters/Setters 추가
- [ ] 모든 클래스에 생성자 추가

### Phase 3: DMN 결정 테이블 작성
- [ ] `payment-decision.dmn` 파일 생성
- [ ] 입력 변수 정의 (Order Amount, Customer Type, Credit Score)
- [ ] 출력 변수 정의 (Approved, Reason)
- [ ] 6개 결정 규칙 작성
- [ ] Hit Policy를 FIRST로 설정
- [ ] DMN 파일 유효성 검증

### Phase 4: BPMN 프로세스 작성
- [ ] `product-order-workflow.bpmn` 파일 생성
- [ ] 시작 이벤트 추가 (Product Order 접수)
- [ ] Product Order 검증 태스크 추가
- [ ] 결제 발급 비즈니스 룰 태스크 추가 (DMN 연결)
- [ ] 결제 결과 XOR 게이트웨이 추가
- [ ] 결제 실패 종료 이벤트 추가
- [ ] Service Order 분배 태스크 추가
- [ ] SO 병렬 게이트웨이 추가 (시작/종료)
- [ ] Service Order 1 서브프로세스 작성
  - [ ] SO1 시작 태스크
  - [ ] RO 병렬 게이트웨이 (시작/종료)
  - [ ] Resource Order 1-1, 1-2 태스크
  - [ ] SO1 승인 사용자 태스크
  - [ ] SO1 완료 태스크
- [ ] Service Order 2 서브프로세스 작성
  - [ ] SO2 시작 태스크
  - [ ] RO 병렬 게이트웨이 (시작/종료)
  - [ ] Resource Order 2-1, 2-2 태스크
  - [ ] SO2 완료 태스크
- [ ] Product Order 최종 승인 사용자 태스크 추가
- [ ] 종료 이벤트 추가 (Product Order 완료)
- [ ] 모든 시퀀스 플로우 연결
- [ ] 조건부 플로우에 조건식 추가
- [ ] BPMN 파일 유효성 검증

### Phase 5: 프로세스 변수 매핑
- [ ] BPMN 프로세스 변수 정의
  - [ ] `productOrder` (ProductOrder 타입)
  - [ ] `paymentResult` (PaymentResult 타입)
  - [ ] `serviceOrders` (List<ServiceOrder> 타입)
- [ ] 비즈니스 룰 태스크 입/출력 매핑 확인
- [ ] 서브프로세스 변수 전달 확인

### Phase 6: 비즈니스 로직 구현 (선택사항)
- [ ] `OrderValidationService.java` 작성
- [ ] `ServiceOrderDistributionService.java` 작성
- [ ] 서비스 클래스를 BPMN 태스크에 연결

### Phase 7: 로컬 테스트
- [ ] Quarkus Dev Mode 실행 (`mvn quarkus:dev`)
- [ ] Swagger UI 접속 확인 (`http://localhost:8080/q/swagger-ui`)
- [ ] 정상 플로우 테스트 (결제 성공 시나리오)
- [ ] 예외 플로우 테스트 (결제 실패 시나리오)
- [ ] 병렬 처리 검증 (SO1, SO2 동시 실행)
- [ ] 로그 확인 및 디버깅

### Phase 8: 문서화
- [ ] README.md 작성
- [ ] API 사용 예제 추가
- [ ] 테스트 시나리오 문서화
- [ ] 트러블슈팅 가이드 작성

---

## 5. 데이터 모델 상세 정의

### 5.1 ProductOrder 클래스

| 필드명 | 타입 | 필수 | 설명 | 예시 값 |
|--------|------|------|------|---------|
| `orderId` | String | Y | 주문 고유 ID | "PO-2026-001" |
| `customerId` | String | Y | 고객 ID | "CUST-12345" |
| `customerType` | String | Y | 고객 유형 | "VIP", "REGULAR", "NEW" |
| `orderDate` | Date | Y | 주문 일시 | 2026-03-25T10:30:00Z |
| `totalAmount` | Double | Y | 총 주문 금액 | 150000.00 |
| `creditScore` | Integer | Y | 신용 점수 | 750 |
| `paymentMethod` | String | Y | 결제 수단 | "CREDIT_CARD", "BANK_TRANSFER" |
| `status` | String | Y | 주문 상태 | "PENDING", "VALIDATED", "PAYMENT_APPROVED", "PAYMENT_FAILED", "COMPLETED" |
| `items` | List<ProductItem> | Y | 주문 제품 목록 | - |
| `serviceOrderIds` | List<String> | N | 서비스 주문 ID 목록 | ["SO1-001", "SO2-001"] |

### 5.2 ProductItem 클래스

| 필드명 | 타입 | 필수 | 설명 | 예시 값 |
|--------|------|------|------|---------|
| `productId` | String | Y | 제품 ID | "PROD-001" |
| `productName` | String | Y | 제품명 | "Enterprise Software License" |
| `quantity` | Integer | Y | 수량 | 5 |
| `unitPrice` | Double | Y | 단가 | 30000.00 |
| `totalPrice` | Double | Y | 총액 | 150000.00 |

### 5.3 PaymentResult 클래스

| 필드명 | 타입 | 필수 | 설명 | 예시 값 |
|--------|------|------|------|---------|
| `approved` | Boolean | Y | 승인 여부 | true, false |
| `reason` | String | Y | 승인/거부 사유 | "자동 승인 - VIP 고객" |
| `transactionId` | String | N | 거래 ID | "TXN-2026-001" |
| `processedDate` | Date | Y | 처리 일시 | 2026-03-25T10:31:00Z |

### 5.4 ServiceOrder 클래스

| 필드명 | 타입 | 필수 | 설명 | 예시 값 |
|--------|------|------|------|---------|
| `serviceOrderId` | String | Y | 서비스 주문 ID | "SO1-001" |
| `productOrderId` | String | Y | 상위 제품 주문 ID | "PO-2026-001" |
| `serviceType` | String | Y | 서비스 유형 | "SO1", "SO2" |
| `createdDate` | Date | Y | 생성 일시 | 2026-03-25T10:32:00Z |
| `status` | String | Y | 상태 | "CREATED", "IN_PROGRESS", "APPROVED", "COMPLETED" |
| `resourceOrderIds` | List<String> | N | 리소스 주문 ID 목록 | ["RO1-1-001", "RO1-2-001"] |
| `requiresApproval` | Boolean | Y | 승인 필요 여부 | true (SO1), false (SO2) |
| `approver` | String | N | 승인자 | "manager@example.com" |
| `approvedDate` | Date | N | 승인 일시 | 2026-03-25T10:35:00Z |

### 5.5 ResourceOrder 클래스

| 필드명 | 타입 | 필수 | 설명 | 예시 값 |
|--------|------|------|------|---------|
| `resourceOrderId` | String | Y | 리소스 주문 ID | "RO1-1-001" |
| `serviceOrderId` | String | Y | 상위 서비스 주문 ID | "SO1-001" |
| `resourceType` | String | Y | 리소스 유형 | "RO1-1", "RO1-2", "RO2-1", "RO2-2" |
| `createdDate` | Date | Y | 생성 일시 | 2026-03-25T10:33:00Z |
| `resourceName` | String | Y | 리소스명 | "Server Instance" |
| `resourceSpec` | String | N | 리소스 사양 | "4 vCPU, 16GB RAM" |
| `status` | String | Y | 상태 | "CREATED", "PROCESSING", "COMPLETED", "FAILED" |
| `completedDate` | Date | N | 완료 일시 | 2026-03-25T10:34:00Z |

---

## 6. 테스트 시나리오

### 6.1 정상 플로우 (결제 성공)

#### 시나리오 1: VIP 고객 고액 주문
**입력 데이터**:
```json
{
  "orderId": "PO-2026-001",
  "customerId": "CUST-VIP-001",
  "customerType": "VIP",
  "orderDate": "2026-03-25T10:30:00Z",
  "totalAmount": 250000.00,
  "creditScore": 800,
  "paymentMethod": "CREDIT_CARD",
  "items": [
    {
      "productId": "PROD-001",
      "productName": "Enterprise Software License",
      "quantity": 10,
      "unitPrice": 25000.00,
      "totalPrice": 250000.00
    }
  ]
}
```

**예상 결과**:
1. ✅ Product Order 검증 통과
2. ✅ DMN 결정: `approved = true`, `reason = "자동 승인 - VIP 고객"`
3. ✅ Service Order 2개 생성 (SO1, SO2)
4. ✅ SO1, SO2 병렬 실행
5. ✅ 각 SO 내 Resource Order 2개씩 병렬 실행
6. ✅ SO1 승인 완료
7. ✅ 최종 승인 후 프로세스 완료

#### 시나리오 2: 일반 고객 소액 주문
**입력 데이터**:
```json
{
  "orderId": "PO-2026-002",
  "customerId": "CUST-REG-001",
  "customerType": "REGULAR",
  "orderDate": "2026-03-25T11:00:00Z",
  "totalAmount": 50000.00,
  "creditScore": 650,
  "paymentMethod": "BANK_TRANSFER",
  "items": [
    {
      "productId": "PROD-002",
      "productName": "Basic Software License",
      "quantity": 2,
      "unitPrice": 25000.00,
      "totalPrice": 50000.00
    }
  ]
}
```

**예상 결과**:
1. ✅ Product Order 검증 통과
2. ✅ DMN 결정: `approved = true`, `reason = "자동 승인 - 소액 주문"`
3. ✅ 프로세스 정상 완료

### 6.2 예외 플로우 (결제 실패)

#### 시나리오 3: 신규 고객 고액 주문 거부
**입력 데이터**:
```json
{
  "orderId": "PO-2026-003",
  "customerId": "CUST-NEW-001",
  "customerType": "NEW",
  "orderDate": "2026-03-25T12:00:00Z",
  "totalAmount": 300000.00,
  "creditScore": 700,
  "paymentMethod": "CREDIT_CARD",
  "items": [
    {
      "productId": "PROD-001",
      "productName": "Enterprise Software License",
      "quantity": 12,
      "unitPrice": 25000.00,
      "totalPrice": 300000.00
    }
  ]
}
```

**예상 결과**:
1. ✅ Product Order 검증 통과
2. ❌ DMN 결정: `approved = false`, `reason = "거부 - 신규 고객 고액 주문"`
3. ⚠️ 프로세스 조기 종료 (EndEvent_PaymentFailed)
4. ⚠️ Service Order 생성되지 않음

#### 시나리오 4: 낮은 신용 점수
**입력 데이터**:
```json
{
  "orderId": "PO-2026-004",
  "customerId": "CUST-REG-002",
  "customerType": "REGULAR",
  "orderDate": "2026-03-25T13:00:00Z",
  "totalAmount": 80000.00,
  "creditScore": 550,
  "paymentMethod": "CREDIT_CARD",
  "items": [
    {
      "productId": "PROD-003",
      "productName": "Standard Software License",
      "quantity": 4,
      "unitPrice": 20000.00,
      "totalPrice": 80000.00
    }
  ]
}
```

**예상 결과**:
1. ✅ Product Order 검증 통과
2. ❌ DMN 결정: `approved = false`, `reason = "거부 - 낮은 신용 점수"`
3. ⚠️ 프로세스 조기 종료

### 6.3 병렬 처리 검증

#### 검증 포인트
1. **SO1과 SO2 병렬 실행**:
   - SO1과 SO2가 동시에 시작되는지 확인
   - 두 서브프로세스가 독립적으로 실행되는지 확인
   - 두 서브프로세스 모두 완료될 때까지 메인 프로세스가 대기하는지 확인

2. **각 SO 내 RO 병렬 실행**:
   - SO1: RO1-1과 RO1-2가 동시에 실행되는지 확인
   - SO2: RO2-1과 RO2-2가 동시에 실행되는지 확인
   - 모든 RO가 완료될 때까지 SO가 대기하는지 확인

3. **타이밍 검증**:
   - 로그 타임스탬프를 통해 병렬 실행 확인
   - 예상 실행 시간과 실제 실행 시간 비교

#### 테스트 방법
```bash
# Quarkus Dev Mode 실행
mvn quarkus:dev

# 별도 터미널에서 REST API 호출
curl -X POST http://localhost:8080/product-order-workflow \
  -H "Content-Type: application/json" \
  -d @test-data/vip-customer.json

# 로그에서 병렬 실행 확인
# 예상 로그 패턴:
# [10:32:00] SO1 시작
# [10:32:00] SO2 시작 (동시)
# [10:32:01] RO1-1 시작
# [10:32:01] RO1-2 시작 (동시)
# [10:32:01] RO2-1 시작
# [10:32:01] RO2-2 시작 (동시)
```

---

## 7. 추가 참고 사항

### 7.1 BAMOE 개발 도구
- **BPMN Editor**: VS Code 내장 그래픽 에디터
- **DMN Editor**: VS Code 내장 결정 테이블 에디터
- **Process Instance Viewer**: 실행 중인 프로세스 모니터링
- **Swagger UI**: REST API 테스트 인터페이스

### 7.2 유용한 Maven 명령어
```bash
# 프로젝트 빌드
mvn clean install

# Quarkus Dev Mode (Hot Reload)
mvn quarkus:dev

# 테스트 실행
mvn test

# 패키징 (JAR 생성)
mvn package

# Docker 이미지 빌드
mvn package -Dquarkus.container-image.build=true
```

### 7.3 REST API 엔드포인트
BAMOE Accelerator가 자동 생성하는 엔드포인트:

```
POST   /product-order-workflow          # 프로세스 시작
GET    /product-order-workflow          # 프로세스 인스턴스 목록
GET    /product-order-workflow/{id}     # 특정 인스턴스 조회
DELETE /product-order-workflow/{id}     # 인스턴스 삭제
POST   /product-order-workflow/{id}/Task_FinalApproval  # 사용자 태스크 완료
```

### 7.4 트러블슈팅 팁
1. **BPMN 파일이 인식되지 않는 경우**:
   - `src/main/resources/` 경로 확인
   - 파일 확장자가 `.bpmn`인지 확인
   - Maven 빌드 후 재시도

2. **DMN 결정이 실행되지 않는 경우**:
   - Business Rule Task의 Decision Ref 확인
   - DMN 파일의 Decision ID와 일치하는지 확인
   - 입력 변수 매핑 확인

3. **병렬 게이트웨이가 작동하지 않는 경우**:
   - 게이트웨이 타입이 Parallel Gateway인지 확인
   - 시작 게이트웨이와 종료 게이트웨이가 쌍으로 있는지 확인
   - 모든 분기가 올바르게 연결되었는지 확인

---

## 8. 다음 단계

이 요구사항 문서를 기반으로 다음 작업을 진행하세요:

1. ✅ BAMOE Accelerator로 프로젝트 생성
2. ✅ 데이터 모델 클래스 작성 (Java)
3. ✅ DMN 결정 테이블 작성
4. ✅ BPMN 프로세스 다이어그램 작성
5. ✅ 로컬 환경에서 테스트
6. ✅ 문서화 및 데모 준비

**문서 버전**: 1.0  
**최종 수정일**: 2026-03-25  
**작성자**: Documentation Writer Mode