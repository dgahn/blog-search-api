# 블로그 검색 API

## 기능 요구 사항
### 블로그 검색 기능
- [X] 키워드를 통해 블로그를 검색할 수 있다.
- [X] 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 한다.
  - Query Param으로 ACCURACY와 RECENCY을 사용
- [X] 검색 결과는 Pagination 형태로 제공한다.
- [X] 검색 소스는 카카오 API를 활용한다.
- [X] 카카오 API 이외의 검색 소스가 추가될 수 있음을 고려해야한다.
  - Blog 검색 관련 클래스를 인터페이스로 추상화함.

### 인기 검색어 목록
- [X] 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공한다.
- [X] 검색어 별로 검색 횟수도 함께 제공한다.

### 프로젝트 구성 추가 요건
- [X] 멀티 모듈 구성 및 모듈간 의존성 제약
  - `backend-for-frontend`: 클라이언트에게 제공할 API가 명세되는 모듈, inbound-adapter에 해당됨
  - `application`: 유스케이스에 대한 로직을 구현된 모듈
  - `doamin`: 도메인 계층, 가장 내부의 로직으로 포트를 통해서 외부 모듈에 접근할 수 있음 
  - `adapter`:
    - `outbound`: 외부 애플리케이션에 접근하는 역할을 하는 모듈, KaKaoClient, Database등을 접근함

### 추가 요건
- [X] 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현
  - 트래픽이 많을 경우를 대비하여 블로그 검색시 인기 검색어 기록은 `@Async` 수행되도록 작성함
  - 데이터가 많을 경우를 대비하여 `SearchBlogHistoryEntity`에 검색과 관련된 컬럼에 인덱스 추가함
  - 인기 검색어 API에 대해서 `CaffeineCache`를 적용함.
  - 인기 검색어 API는 5분에 한번씩 갱신할 수 있도록 설정함.
  - 블로그 검색 API는 keyword가 워낙 다양하고 조회하는 size도 다양하기 때문에 캐시를 설정하지 않음.
- [X] 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현(예시. 키워드 별로 검색된 횟수의 정확도)
  - `@Async`를 사용한만큼 강하게 Lock을 사용하기 위해 비관적 잠금을 사용함.
- [X] 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공
  - `fallback`으로 네이버 블로그로 검색하도록 구성함.

### 제약사항
- JAVA 11 이상 또는 Kotlin 사용
  - `Kotlin` 사용
- Spring Boot 사용
  - `Spring Boot Web`, `Spring Boot Aop`, `Spring Boot Data JPA` 사용
- Gradle 기반의 프로젝트
  - Gradle(Kotlin DSL) 기반으로 프로젝트 구성
- DB는 인메모리 DB(예: h2)를 사용하며 DB 컨트롤은 JPA로 구현
  - H2, `Spring Boot Data JPA` 사용
- 외부 라이브러리 및 오픈소스 사용 가능
  - [detekt](https://github.com/detekt/detekt): 코틀린 정적 코드 분석
  - [kotlinter](https://github.com/jeremymailen/kotlinter-gradle): 코틀린 Linter
  - [Junit5](https://github.com/junit-team/junit5): 테스트 프레임워크
  - [kotest](https://kotest.io/): matcher용 서드파티로 사용

### API 명세

#### 블로그 검색 API
```
GET /v1/search/blog
```

|Name|Type| Description                                                       |Required|
|---|---|-------------------------------------------------------------------|---|
|query|String| 검색을 원하는 질의어 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음 |O|
|sort|String| 결과 문서 정렬 방식, ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 accuracy        |X|
|page|Integer| 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                                     |X|
|size|Integer| 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10                              |X|

```
예시)
curl -X GET "http://localhost:8080/v1/search/blog?query=aabbcc&sort=RECENCY&size=10&page=1"
[{"blogName":"","contents":"이용 <b>AABBCC</b> 이질배수체 = 복2배체 같은게놈을 복수로 가지고 있어서 복2배체라 함 이질배수체는 두 종의 중간형질을 나타내고, 적응력이 크며, 감수분열시 대합이 이루어져 임성이 높다. 1 게놈이 다른 양친을... ","dateTime":"20220919","thumbnail":"","title":"재배학","url":"https://blog.naver.com/zominz?Redirect=Log&logNo=222878869233"},{"blogName":"","contents":"가령 <b>AABbCc</b>의 유전자를 가진 생물에 있어서 A·B·C를 우성유전자, bc를 열성유전자라고 하면 이 생물은 A에 대하여는 동형접합체이며, B·b와 C·c에 대하여는 이형접합체라고 함. =동질접합체·호모접합체.... ","dateTime":"20220909","thumbnail":"","title":"낱말사전 (동)ㅎ","url":"https://blog.naver.com/thddudgh77?Redirect=Log&logNo=222870594523"},{"blogName":"","contents":"2 abc를 넣어서 <b>aabbcc</b> 출력하고 줄이 바뀌지 않은 채로 3 abcd가 들어가기 때문에 정상적으로 출력되지 않는다. 그래서 2 abc를 넣어서 <b>aabbcc</b>를 출력했으면 print()로 줄을 바꿔주어야 이렇게... ","dateTime":"20220908","thumbnail":"","title":"[백준 알고리즘 Baekjoon] 2675번 python (문자열 반복)","url":"https://blog.naver.com/wjdrjsdn3938?Redirect=Log&logNo=222870163410"},{"blogName":"","contents":"하루에 알파벳 세개씩공부하는데 오늘 <b>AaBbCc</b> 공부하고 내일도 <b>AaBbCc</b> 복습합니다 그리고 따로 CD 틀필요없이 QR코드 인식하면 발음이랑 노래가 나와서 너무너무 편리해요....씨디귀찮아요... 따로 CD는 있어요!... ","dateTime":"20220907","thumbnail":"","title":"5세 아이 알파벳 쉽게 공부하기 노래 아이템 책 추천... ","url":"https://blog.naver.com/usus11116?Redirect=Log&logNo=222869062461"},{"blogName":"","contents":"산엔청 쇼핑몰 추천인 <b>aabbcc</b> (복붙 가능) https://www.sanencheong.com/member/agreement 휴대폰 본인인증 있어요 일반 가입 하세요 추천인 id <b>aabbcc</b> 추천인 입력하고 가입하면 5000 포인트 들어와요 피추천인... ","dateTime":"20220830","thumbnail":"","title":"산엔청 쇼핑몰 추석 이벤트","url":"https://blog.naver.com/sh_auddk?Redirect=Log&logNo=222861721161"},{"blogName":"","contents":"Text=&quot;<b>AaBbCc</b>&quot; VerticalAlignment=&quot;Top&quot; HorizontalAlignment=&quot;Center&quot; Width=&quot;120&quot; Height=&quot;50&quot; HorizontalContentAlignment=&quot;Center&quot; FontSize=&quot;{Binding ElementName='sbTest', Path='Value'}&quot;/&gt; 위 코드를 보면... ","dateTime":"20220827","thumbnail":"","title":"WPF의 DataBining","url":"https://blog.naver.com/anakt?Redirect=Log&logNo=222859826561"},{"blogName":"","contents":"0은 ff 로 표현한다.) +)HEX의 단축표현 : #abc #<b>aabbcc</b>의 단축 표현으로, 두자리씩 구분했을 때 반복되는 두 수치가 있을 떄만 사용 가능하다. #aab3cc -&gt; #ab3c (x) | #<b>aabbcc</b>-&gt;$abc(o) background... ","dateTime":"20220814","thumbnail":"","title":"CSS 2일차","url":"https://blog.naver.com/heavenly627?Redirect=Log&logNo=222848066421"},{"blogName":"","contents":"<b>AaBbCc</b>방식으로 숫자가 매겨지고 있지 않다!! 한국어는 낱자와 음절의... /* 숫자, ㄱㄴㄷ, <b>AaBbCc</b>, 특수문자 1. 둘다 같은 타입인지 체크! 2. 둘이 다른 타입이면... ","dateTime":"20220813","thumbnail":"","title":"Swift 한글/영어/특수문자 정렬하기","url":"https://blog.naver.com/rlawnguq12?Redirect=Log&logNo=222846704792"},{"blogName":"","contents":"ex) #FF00FF : 보라색 #abc : #<b>aabbcc</b>의 단축표현으로 aa, bb, cc처럼 ,red,green,blue 값이 같을때만 사용할 수 있다. . #2aabbc같은경우는 사용하지 못한다 RGB와 마찬가지로 투명도를... ","dateTime":"20220809","thumbnail":"","title":"개발 첫걸음_7일차(22.08.08)","url":"https://blog.naver.com/rhdwjdqo0618?Redirect=Log&logNo=222843407022"},{"blogName":"","contents":"(2) 이질배수체 : 게놈이 AABB 또는 <b>AABBCC</b> 처럼 서로 다른 종류의 게놈이 배가 된것 - 4배체 : TTSS (담배) - 6배체 : AABBDD (호밀) - 같은 게놈을 복수로 가지고 있어 복 2배체라고도 부름 ㆍ복2배체의 특징은 ① 두... ","dateTime":"20220808","thumbnail":"","title":"5강. 염색체 변화와 유전","url":"https://blog.naver.com/yskiiiv?Redirect=Log&logNo=222843370713"}]
```

#### 인기 검색어 목록 API
```
GET /v1/top-searched/blog HTTP/1.1
```

```
예시)
curl -X GET "http://localhost:8080/v1/top-searched/blog"
[{"id":1,"keyword":"aabbcc","createdAt":"2022-09-21T07:13:12.746481Z","updatedAt":"2022-09-21T07:13:54.324151Z","searchCount":4},{"id":2,"keyword":"aa","createdAt":"2022-09-21T07:14:01.246195Z","updatedAt":"2022-09-21T07:14:04.283770Z","searchCount":4}]%
```

### 실행파일 다운로드 링크

https://github.com/dgahn/blog-search-api/raw/main/kakaobank.jar