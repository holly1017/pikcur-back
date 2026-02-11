# PIKCUR 프로젝트 분석 보고서

이 보고서는 PIKCUR 백엔드 프로젝트의 코드베이스와 설정을 분석하여 작성되었습니다.

---

## 1. 프로젝트 목적
**PIKCUR** 프로젝트는 한정판 및 희귀 상품 거래의 신뢰성과 실시간성을 보장하는 **C2C(Customer to Customer) 리셀 및 경매 플랫폼**을 구축하는 것을 목적으로 합니다.
- **거래 투명성 확보**: 입찰 및 낙찰 프로세스를 자동화하여 공정한 거래 환경 제공.
- **실시간 소통**: 경매 상황 및 서비스 공지 사항을 실시간으로 전달하여 사용자 참여 유도.
- **통합 커머스 경험**: 인증, 결제, 배송 추적까지 아우르는 끊김 없는(seamless) 결제 및 물류 인프라 구축.
- **확장성 있는 설계**: 클라우드 네이티브 환경(AWS)을 활용하여 트래픽 증가와 데이터 확장에 유연하게 대응.

## 2. 프로젝트 설명
**PIKCUR (Pikcurchu)**는 프리미엄 수집품, 한정판 스니커즈, 또는 기타 리미티드 에디션 아이템을 거래할 수 있는 **니치 마켓플레이스 및 경매 플랫폼**입니다. 사용자가 상품을 등록하고, 입찰 시스템을 통해 거래하며, 실시간 알림과 안전 결제 시스템을 제공하는 완성도 높은 커머스 백엔드입니다.

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

## 6. 포트폴리오 활용 제안 (Portfolio Tips)
이 프로젝트를 포트폴리오에 담을 때 강조하면 좋은 차별화 포인트입니다:

1. **AOP를 활용한 관심사 분리**:
   - `AlarmAspect`를 통해 알림 생성 로직을 비즈니스 로직에서 분리했습니다. 이는 확장성과 유지보수성을 고려한 설계 능력을 보여줍니다.
2. **클라우드 네이티브 설계**:
   - 단순히 서버를 띄운 것이 아니라 S3, CloudFront, RDS, Redis(TLS) 등 AWS의 핵심 서비스들을 적재적소에 배치하여 프로덕션 수준의 아키텍처를 구현했음을 강조하세요.
3. **실시간성 확보**:
   - 입찰이나 알림 시스템에 WebSocket을 적용하여 동적인 사용자 경험을 고려한 기술적 시도를 언급하면 좋습니다.

---

## 7. 개선 제안 (Improvement Points)
현재 구조에서 한 단계 더 발전시키기 위한 개선 포인트들입니다:

1. **인증 및 보안 강화**:
   - 현재 CORS 설정(`WebConfig`)에 `localhost:3000`이 하드코딩되어 있습니다. 환경 변수(profiles)를 통해 배포 환경에 맞는 도메인을 동적으로 설정하도록 변경하는 것이 좋습니다.
   - JWT 만료 시 Access Token 재발급을 위한 **RefreshToken 도입**을 고려해 보세요.
2. **저장소 전략 일원화**:
   - `build.gradle`에는 S3 의존성이 있고 `application-db.properties`에도 설정이 있지만, `WebConfig`에서는 로컬 디렉토리(`uploads/`)를 직접 서빙하고 있습니다. 배포 환경의 확장성을 위해 모든 정적 파일을 S3로 일원화하고 CloudFront를 통해서만 서빙하도록 개선할 수 있습니다.
3. **예외 처리 패턴 공통화**:
   - `@ControllerAdvice`와 `@ExceptionHandler`를 활용한 전역 예외 처리기(Global Exception Handler)를 도입하여, API 응답 규격(Error Response)을 통일하면 유지보수성이 크게 향상됩니다.
4. **트랜잭션 관리**:
   - 경매(Bid)와 결제(Payment) 등 데이터 일관성이 중요한 로직에서 `@Transactional` 어노테이션을 명확히 사용하고 있는지 재점검하고, 비즈니스 예외 상황에서의 롤백 전략을 강화할 수 있습니다.
5. **테스트 코드 확충**:
   - 현재 프로젝트 구조에 테스트 디렉토리가 있지만, 주요 비즈니스 로직(특히 경매 입찰 로직)에 대한 단위 테스트(Unit Test)를 추가하여 안정성을 검증하는 과정이 필요합니다.
