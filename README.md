![Auto Assign](https://github.com/kdt-proj1-team/demo-repository/actions/workflows/auto-assign.yml/badge.svg)

![Proof HTML](https://github.com/kdt-proj1-team/demo-repository/actions/workflows/proof-html.yml/badge.svg)

# Team Project 연습용 Repository
이곳의 각자의 브랜치를 만들어서 CLI로 코드를 커밋하고 main으로 pull request 날려주세요
### 종훈: feature/board1
### 윤진: feature/board2
### 동엽: feature/admin-dashboard1
### 미랑: feature/admin-dashboard2
### 찬영: feature/mypage
### 미르: feature/login
브랜치 생성 후, 해당 브랜치에 파일 작성 후 add-commit-push 부탁드립니다.
push 후 main으로(master 아님) pull request 보내주세요.


### **Git 브랜치 명명법(Best Practices)**
Git 브랜치 이름을 효율적으로 정하는 것은 협업과 코드 관리의 가독성을 높이는 데 매우 중요합니다. 다음은 일반적으로 사용되는 브랜치 명명 규칙과 예시입니다.

연습용 브랜치는
'feature/자신이 맡을 기능명' 으로 작성부탁드립니다.

---

## **1. 브랜치 기본 구조**
📌 **일반적인 형식**:  
```
[브랜치 유형]/[작업 내용]-[이슈 번호(선택)]
```
- 브랜치 유형: `feature`, `bugfix`, `hotfix`, `release`, `develop`, `main` 등  
- 작업 내용: 기능이나 수정의 내용을 간략하게 표현  
- 이슈 번호: 해당 작업이 관련된 이슈가 있다면 추가 (선택 사항)

---

## **2. 주요 브랜치 유형과 예시**
### **1) 메인 브랜치**
- `main` (또는 `master`): 제품으로 배포되는 안정적인 코드
- `develop`: 개발자들이 공동으로 작업하는 브랜치

🔹 **예시**  
```
main
develop
```

---

### **2) 기능 개발 브랜치 (Feature)**
- 새로운 기능을 개발할 때 사용
- `feature/[기능명]-[이슈번호]`
- 개발이 끝나면 `develop` 브랜치로 병합

🔹 **예시**  
```
feature/user-login-123
feature/payment-integration-456
```

---

### **3) 버그 수정 브랜치 (Bugfix)**
- 기존 기능의 버그를 수정할 때 사용
- `bugfix/[버그설명]-[이슈번호]`
- 수정 완료 후 `develop` 브랜치로 병합

🔹 **예시**  
```
bugfix/fix-login-error-789
bugfix/cart-item-missing-234
```

---

### **4) 긴급 수정 브랜치 (Hotfix)**
- `main`에서 발견된 긴급한 문제를 수정할 때 사용
- `hotfix/[수정내용]-[이슈번호]`
- 수정 완료 후 `main`과 `develop` 모두에 병합

🔹 **예시**  
```
hotfix/security-patch-987
hotfix/payment-fix-543
```

---

### **5) 배포 브랜치 (Release)**
- 배포 준비를 위한 브랜치
- `release/[버전]`
- 최종 검토 및 테스트 후 `main`과 `develop`으로 병합

🔹 **예시**  
```
release/v1.0.0
release/v2.3.4
```

---

## **3. 추가적인 브랜치 명명 규칙**
✅ **소문자 사용 & 단어는 하이픈(-)으로 구분**  
```
올바름: feature/user-authentication  
잘못됨: Feature_UserAuthentication
```

✅ **너무 길거나 모호한 이름은 피하기**  
```
올바름: bugfix/login-error  
잘못됨: bugfix/fix-bug-that-happens-when-logging-in-and-clicking-home
```

✅ **작업 단위별로 이슈 번호 포함 (선택적)**  
```
feature/add-profile-picture-1234
```

✅ **특정 팀 또는 목적별 네임스페이스 추가 가능**  
```
teamA/feature/new-dashboard
mobile/feature/push-notifications
```

---

## **4. 정리**
| 브랜치 유형  | 명명 규칙 | 설명 |
|-------------|------------------------|----------------|
| **메인 브랜치** | `main`, `develop` | 배포 및 공동 개발 브랜치 |
| **기능 개발** | `feature/[기능명]-[이슈번호]` | 새로운 기능 개발 |
| **버그 수정** | `bugfix/[버그설명]-[이슈번호]` | 개발 단계에서 버그 수정 |
| **긴급 수정** | `hotfix/[수정내용]-[이슈번호]` | 운영 중 긴급 수정 |
| **배포 준비** | `release/[버전]` | 배포를 위한 최종 준비 |


