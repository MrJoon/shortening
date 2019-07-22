### 프로젝트 소개
**URL Shortening Service**  
URL을 입력받아 8자 이내의 단축 URL로 변경해 주고,
Shortening Key를 이용하여 기존 URL로 리다이렉트 해주는  서비스  

---
### 개발 환경
+ Java 1.8
+ Spring Boot 2.1.6.RELEASE
+ H2 Database 1.4.199
+ gradle 5.4.1
+ Spring Data JPA 2.1.9
+ lombok 1.18.8
+ thymeleaf 3.0.11
+ JUnit 4.12
---
### 실행 방법
전제 조건으로 jdk 1.8이 설치 되어 있어야 하며, 8000포트가 사용중이 아니며, 방화벽이 오픈되어 있어야 합니다.

> ※ 8000포트가 사용중일 경우  
./src/main/rescoures/application.yml 파일을 수정 후 빌드를 진행  

    server:
      port: 8000 <- 수정  


1. Windows  
> git에서 Checkout 받은 경로에서 cmd or PowerShell 실행  
./gradlew.bat clean jar build 실행  

    C:\shortening> ./gradlew.bat clean jar build  
> 빌드 완료 후  Excuteable jar 실행

    C:\shortening> java -jar ./build/libs/shortening.jar

2. Linux  
> gradlew파일에 실행 권한 부여 후 빌드, Excuteable jar 실행  

    [root@localhost shortening]# chmod 755 gradlew
    [root@localhost shortening]# ./gradlew clean jar build  
    [root@localhost shortening]# java -jar ./build/libs/shortening.jar  
---
### 기능
기본 설정된 8000포트를 기준으로 작성하였습니다. 
1. **URL -> Shortening Key**  
1.1 *http://localhost:8000* 로 접속  
1.2 줄이고 싶은 url을 입력 후 변경 클릭  
1.3 정상적으로 변경이 완료되면 좌측에 url, 우측에 Shortening Key(1~8자리 랜덤) 노출, 오류 발생 시 하단에 아래와 같은 메세지 노출  
- 공백, null, url 내 공백이 있을 경우 -> '*생성 중 오류가 발생하였습니다. 다시 시도해주시기 바랍니다.*' 메세지 노출  
- Shortening Key 할당 초과 및 중복체크 제한 횟수 초과일 경우 -> '*생성 시간이 초과되거나 더이상 생성 할 수 없습니다.*' 메세지 노출  
- Shortening Key는 Base62기준, 각각의 확률로 1~8자리 랜덤 생성.  
단, 모든 Key가 할당되게 되면 중복 Key 체크에서 loop에 빠지기 때문에 제한을 두었음.  
./src/main/rescoures/application.yml    
>

    property:  
	  findShorturlLimit: 100000 <- 기본값 10만  

1.4 1.3 생성 시 하기의 경우는 모두 동일한 URL로 인식하여 같은 Shortening Key를 리턴  
ex) http://google.com, google.com, google.com/, http://google.com\  
※http, https 프로토콜의 경우 같은 URL로 인식되지 않음.

2. **Shortening Key -> URL**  
2.1 1.3에서 정상적으로 변경이 되었을 경우 노출되는 하단의 Shortening Key가 포함된 URL로  접근  
ex) *http://localhost:8000/HVFm5uKt*  
2.2 정상적으로 리턴되었던 Shortening Key의 경우는 기존 URL로 자동 리다이렉트, 생성 하지 않은 임의의 Shortening Key로 호출 했을 경우 -> '*URL이 존재하지 않습니다.*' 메세지 노출  
2.3 기존 url이 정상 여부까지 판단하지는 않기 때문에, 잘못된 url로 Shortening Key를 생성 시 잘못된 url로 리턴 됨.
---
### 테스트 환경
http://15.164.63.20:8000/  
상기 서버에 동일하게 테스트 환경이 구축되어 있습니다.
