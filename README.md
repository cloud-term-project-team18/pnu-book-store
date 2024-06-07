# pnu-book-store
## 프로젝트 소개
> 부산대 학생을 위한 전공서적 거래 플랫폼.

> 부산대학교 학생들이 더 이상 사용하지 않는 전공 서적을 쉽게 거래하기 위해 사용할 수 있는 플랫폼으로,
> 
> 판매자는 쉽게 전공 서적을 처리할 수 있고, 구매자는 싼 가격으로 전공 서적을 구매할 수 있습니다.

## 멤버 소개 (+ 담당한 파트 소개 적기)
> 김태호(Devops, BackEnd): 사이트 CI/CD, 오토스케일링, 데이터 크롤링, 인프라 담당
> 
> 김주언(BackEnd): 사이트의 물품에 관련한 기능(등록,삭제,구매) 담당
> 
> 문성재(BackEnd): 사이트의 회원가입, 로깅 담당
> 
> 최지광(FrontEnd): 프론트엔드 담당

## 프로젝트 필요성
> 학기 초 전공 서적이 필요한 강의들이 많습니다. 이에 학생들은 많은 돈을 매 학기마다 전공 서적을 구매하는데 사용합니다.
> 
> 어떤 학생은 동아리, 학과 선후배 관계를 통해서 싸게 전공 서적을 구매할 수 있지만, 이에 해당하지 않는 학생의 경우 
> 싼 가격의 중고 서적을 구매하기 어렵습니다.

> 저희는 이러한 문제를 전공서적 판매 사이트 개발로 통해 누구나 싼 값에 전공 서적을 구매할 수 있는 시스템을 
> 구축하고자 합니다.
> 
> 이때, 대부분의 학생들은 필요한 책을 전공과목의 이름과 교수님으로 구분하지 정확한 책의 이름과 저자, 출판사를 외우고
> 구매하려는 학생은 적습니다. 
> 
> 타 서비스는 책의 이름을 알아야만 검색이 가능한 반면, 저희는 이러한 점을 생각하여 저희 판매사이트는 책의 이름을 몰라도 학과,과목명,교수이름만 
> 알면 책을 검색할 수 있도록 만들었습니다.

> 이후, 거래가 결제까지 진행된다면 판매자가 지정된 물품보관소에 서적을 넣고, 비밀번호를 설정, 
> 구매자에게 지정된 위치와 비밀번호를 공유해 비대면으로 거래가 진행될 수 있도록 합니다.

## 관련 기술/논문/특허 조사내용 소개
![사용 기술](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/stack.png)
> **Server**
>  * Spring boot : Web Application Server
>  * NGINX : Web Server
>  * MySQL : Data 저장을 위한 RDBMS
>  * redis : session 정보 저장을 위한 In-Memory DB

> **Devops**
>  * kubernetes : 컨테이너를 쉽고 빠르게 배포/확장하고 관리를 자동화해주는 오픈소스 플랫폼
>  * HELM : 쿠버네티스 어플리케이션 패키징 툴
>  * GitHub Actions : CI(Continuous Integration)
>  * argo : CD(Continuous Delivery)

> **Infra**
>  * Terraform : 코드형 인프라스트럭쳐(IaC) 자동화 도구
>  * Naver Cloud Platform : 클라우드 컴퓨팅 서비스
## 프로젝트 개발 결과물 소개
> **Architecture**
> ![architecture](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/architecture.png)

> **Auto Scaling**
> ![autoscaling](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/AutoScaling.png)

> **이메일 인증 과정**
> ![email_verification](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/email_verification.png)

> **동적 쿼리를 이용한 검색 필터**
> ![search](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/search.gif)

> **CI/CD**
> ![CICD](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/CICD.png)

> **Batch**
> ![batch](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/batch.png)

## 사용방법
>  * 웹 브라우저를 통해 사이트에 접속이 가능하다.
>  * 페이지의 login, signUp 버튼을 통해 로그인, 회원가입이 가능하다.
>  * 로그인 후 상단의 물품 게시판에서 판매 중인 서적을 확인할 수 있다.
>  * 물품 게시판의 검색 폼을 이용해 대학, 학과, 교수, 강좌에 맞는 서적을 검색할 수 있다.
>  * 상단의 물품 등록을 통해 판매하고 싶은 서적을 등록할 수 있다.
>  * 상단의 myPage에서 내 정보와 구매, 판매한 물품을 확인할 수 있다.

## 활용방안 소개
>  * 구매자는 저렴한 가격으로 중고서적을 구매하고, 판매자는 더 이상 사용하지 않는 전공서적을 판매함으로써 경제적 부담을 줄일 수 있다.
