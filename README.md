# PIKCUR 프로젝트

## 1. 프로젝트 설명
**PIKCUR**는 프리미엄 수집품, 한정판 스니커즈, 또는 기타 리미티드 에디션 아이템을 거래할 수 있는 **니치 마켓플레이스 및 경매 플랫폼**입니다. 사용자가 상품을 등록하고, 입찰 시스템을 통해 거래하며, 실시간 알림과 안전 결제 시스템을 제공합니다.

## 2. 프로젝트 목적
**PIKCUR** 프로젝트는 한정판 및 희귀 상품 거래의 신뢰성과 실시간성을 보장하는 **C2C(Customer to Customer) 리셀 및 경매 플랫폼**을 구축하는 것을 목적으로 합니다.
- **거래 투명성 확보**: 입찰 및 낙찰 프로세스를 자동화하여 공정한 거래 환경 제공.
- **실시간 소통**: 경매 상황 및 서비스 공지 사항을 실시간으로 전달하여 사용자 참여 유도.
- **통합 커머스 경험**: 인증, 결제, 배송 추적까지 아우르는 끊김 없는(seamless) 결제 및 물류 인프라 구축.
- **확장성 있는 설계**: 클라우드 네이티브 환경(AWS)을 활용하여 트래픽 증가와 데이터 확장에 유연하게 대응.

## 3. 주요 기능
- **회원 및 인증**: JWT를 이용한 보안 인증, 이메일 인증, 회원 프로필 및 주소지 관리.
- **스토어 및 상품 관리**: 브랜드별 상품 카탈로그, 개별 스토어 정보 관리 및 조회.
- **경매 및 거래 시스템**: 상품에 대한 **입찰(Bidding)** 기능 및 즉시 구매/판매를 포함한 **트랜잭션** 처리 로직.
- **결제 통합**: **포트원(Iamport)** API를 연동한 실제 결제 프로세스 구현.
- **실시간 알림**: **WebSocket** 및 **STOMP**를 활용하여 입찰 성공, 공지 등의 알림을 실시간으로 전송.
- **물류 추적**: **스윗트래커(SweetTracker)** API 연동을 통한 실시간 배송 현황 조회.
- **고객 지원**: FAQ, 1:1 문의(Q&A), 상품 리뷰 관리.

## 4. 배포 환경 (아키텍처)
본 프로젝트는 **AWS(Amazon Web Services)** 클라우드 환경을 기반으로 설계되었습니다.
- **Was**: Spring Boot 구동 서버.
- **Database**: **AWS RDS (MariaDB)**를 사용하여 데이터의 영속성 및 안정적 관리.
- **Caching/Session**: **AWS ElastiCache (Redis)**를 활용하여 성능 최적화 및 보안 세션 관리(TLS 적용).
- **Storage**: **AWS S3**를 사용하여 상품 이미지 및 첨부파일 관리.
- **CDN**: **AWS CloudFront**를 통해 전 세계 사용자에게 빠르게 콘텐츠 전송.

## 5. 기술 스택
### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.6 (Latest version stack)
- **Database Layer**: MyBatis (Flexible SQL mapping), MariaDB
- **Security**: Spring Security Crypto, JWT (io.jsonwebtoken)

### Infrastructure & External APIs
- **Cloud**: AWS (RDS, S3, CloudFront, ElastiCache)
- **Caching**: Redis
- **Real-time**: WebSocket, Spring Messaging (SimpMessagingTemplate)
- **API Integration**: Iamport (Payment), SweetTracker (Delivery), Gmail SMTP (Email)
- **Documentation**: SpringDoc OpenAPI (Swagger 3)
- **Other**: Lombok, Spring AOP, Jackson

---

## 6. 개선할 점 (Improvement Points)

1. **인증 및 보안 강화**:
   - JWT 만료 시 Access Token 재발급을 위한 **RefreshToken 도입**
2. **테스트 코드 확충**:
   - 주요 비즈니스 로직(특히 경매 입찰 로직)에 대한 단위 테스트(Unit Test)를 추가하여 안정성을 검증하는 과정이 필요
