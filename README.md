<img width="913" alt="스크린샷 2021-07-21 오후 1 53 34" src="https://user-images.githubusercontent.com/69393030/126432957-4f87e6f9-6712-4908-94fc-7be550f90230.png">
# vue_spring-board

# vue - spring legacy 를 이용한 게시판
# vue.js를 이용하여 rest api 통신을해봅니다.
# 카카오 oauth , 카카오 로그인 
https://developers.kakao.com/docs/latest/ko/kakaologin/common

<img width="729" alt="스크린샷 2021-07-21 오후 1 50 40" src="https://user-images.githubusercontent.com/69393030/126432745-6d4e2eb5-fe0f-41ba-84ff-6ab3f4bae17f.png">
카카오 oauth를 이용하기 위해서는 사용하는 redirect url 을 등록해주어야 합니다.
<img width="552" alt="스크린샷 2021-07-21 오후 1 51 19" src="https://user-images.githubusercontent.com/69393030/126432791-496d0947-fbf2-43a6-aa41-c8cdf24dfee6.png">
# 카카오 user Data 정보 info 
<img width="913" alt="스크린샷 2021-07-21 오후 1 54 14" src="https://user-images.githubusercontent.com/69393030/126433002-9c3c503f-b929-43d8-b58d-c67a23d893b1.png">


# 주의사항!
 스프링 legacy(sts 3.0)버전에서는 cors 오류 발생시 @(어노테이션) 으로 허용해주는방법이 없기때문에 filter 에 Cors 오류를 위해 추가 해줍니다. 
 <img width="866" alt="스크린샷 2021-07-21 오후 1 38 46" src="https://user-images.githubusercontent.com/69393030/126431809-47d470d9-4a1d-44ea-868e-ce7647919133.png">
 rest api 통신을 위해 cors 를 허용해줍니다.
 
 # vue 와 spring 을 어떻게 사용하는가?
 vue를 위한 서버 를 띄우고 tomcat 서버를 띄워서 서버단(spring)에서는 데이터를 return 해주는 작업만하고 vue 에서는 그 데이터를 axios / ajax / fetch() 등으로 받아서 데이터를
 렌더링 해줍니다
 이렇게 함으로써 front-end 와 back-end 의 역할이 확실히 분업화 되는 것을 볼수있습니다.
 
 < back - end > 
 <img width="866" alt="스크린샷 2021-07-21 오후 1 39 22" src="https://user-images.githubusercontent.com/69393030/126431858-91626711-6d4d-4fe1-af51-f752cc550dea.png">

<front - end > 
  <img width="1090" alt="스크린샷 2021-07-21 오후 1 41 20" src="https://user-images.githubusercontent.com/69393030/126432016-83a45c93-d245-4000-9714-1360d12027c0.png">

# CORS 오류 
  교차 출처 리소스 공유(Cross-Origin Resource Sharing, CORS)는 추가 HTTP 헤더를 사용하여, 한 출처에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을   부여하도록 브라우저에 알려주는 체제입니다. 웹 애플리케이션은 리소스가 자신의 출처(도메인, 프로토콜, 포트)와 다를 때 교차 출처 HTTP 요청을 실행합니다.
  때문에 서로다른 port 를 가진 서버가 통신하므로 보안때문에 cors를 막아놓아서 cors오류가 발생하게 됩니다. 그러므로 서버단에서 허용을 해주면 됩니다. 

  # 게시판 기능 
  <img width="1090" alt="스크린샷 2021-07-21 오후 1 43 24" src="https://user-images.githubusercontent.com/69393030/126432167-9cd2f3ea-3a06-4bb4-9cf0-34db750a0fa9.png">
  이미지 첨부 기능 을 추가했습니다.
  
 # 댓글 기능!
 <img width="1090" alt="스크린샷 2021-07-21 오후 1 44 39" src="https://user-images.githubusercontent.com/69393030/126432278-727d3bbc-c9f3-4684-b4cc-eaa61302b9bd.png">
  간단한 댓글 기능을 추가했습니다. 
  
 # 게시물 검색기능! 
  <img width="981" alt="스크린샷 2021-07-21 오후 1 45 22" src="https://user-images.githubusercontent.com/69393030/126432342-faad2181-da63-4c83-a5f4-91c892f2e0e9.png">
  
 # kakao oauth 인증 기능!
  <img width="729" alt="스크린샷 2021-07-21 오후 1 45 55" src="https://user-images.githubusercontent.com/69393030/126432388-e116a2fe-dc48-4dca-b627-ec177767ddf8.png">
카카오 oauth 인증을 하여 로그인 하도록 하는 기능을 만들었습니다.!
  
 # kakao Oauth token 
  기존 jsp 로 개발 하였을땐 http session 을 이용하여 해당 세션을 저장하여 로그인을 유지하였는데 , kakao oauth 로그인을 하게되면 카카오 에서 해당 Refresh token , access token 
  을 발급하게 됩니다. 이 토큰을 통해 토큰을 저장하여 세션을 유지하는 방법을 사용합니다.
 #refresh token vs access token 
  리프레시 토큰은 유효기간 2주 정도 , access token은 1시간 정도 수시로 access토큰의 고유값이 바뀝니다.
# axios 에 대한 이야기 , promise / callback 
 Axios는 브라우저, Node.js를 위한 Promise API를 활용하는 HTTP 비동기 통신 라이브러리입니다.
 본인도 ajax만 사용하다가 axios 에 한번 빠진 뒤로 계속 axios 만 쓰게 되었습니다....
 axios 는 vue 에서도 쓸수 있고 , jsp 에서도 쓸수있습니다.
 vue 에서 사용 하는 방법은 npm 을 통해 axios 를 설치할수있고 , cdn 을 통해 스크립트 선언만하여 axios 를 사용할수있습니다. 

 <img width="783" alt="스크린샷 2021-07-21 오후 1 57 21" src="https://user-images.githubusercontent.com/69393030/126433213-f103c78e-99bd-4a87-8eeb-092fe40c3e43.png">

# 후기 ...
최대한 jsp 를 사용안하고 vue 로만 구현하려고 노력했습니다.
1) crud 
2) 게시물 검색 
3) kakao oauth 로그인 
4) 페이징기능
5) oauth 인증 후 마이페이지 view
6) 파일첨부 (cors로 인해 보류) ===> jsp 로 하기로 결정 
7) 파일 첨부 ( 이미지 ) 기능은 추후 vue.js 로 개발하기로, 일단 보류 
8) 간단한 댓글 기능 추가 
