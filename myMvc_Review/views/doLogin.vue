<template>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<!------ Include the above in your HEAD tag ---------->
 
<div class="wrapper fadeInDown">
  <div id="formContent">
    <form>
      <div class="login_sns">
        <p> sns 계정으로 손쉽게 로그인 해보세요.</p>
        <img 
        class="kakao_btn" src="../assets/kakao_login_medium_narrow.png"
        @click="loginWithKaKao">
      </div>
    </form>
  
    <!-- Remind Passowrd -->
    

  </div>
</div>
   
</template>
<script>
//
import axios from 'axios'
export default{
	data() {
		return {
      userId : '',
      accessToken:window.Kakao.Auth.getAccessToken(),
      nickname :'',
      userData : '',
			profile_image:''
			,email:'' // => 게시물 시퀀스 
            ,birthday : ''
            ,gender:'' // => 사용자 이름
		,thumbnail_image:'' // => 제목 
            
			
		}
	},
  mounted(){
  window.Kakao.init('78fa920aa5f6d2b28e1e1ad5c6b1594a') // kakao oauth 최초 init function (key)
  },
  name:"LoginKakao",
  //v-for="(user, idx) in userData" :key="idx" 사용 하기 위함  
methods: {
loginWithKaKao() {
var self = this; //다른 method 사용하기 위해 사용
console.log(self);
  window.Kakao.Auth.login({
    success: function (response) {
      console.log(response)
      window.Kakao.API.request({
        url: '/v2/user/me',
        data: {
          //로그인후 가져올 데이터 
          property_keys: ["properties.nickname",
                          "properties.profile_image",
                          "properties.thumbnail_image",
                          "kakao_account.profile",
                          "kakao_account.email",
                          "kakao_account.age_range",
                          "kakao_account.birthday",
                          "kakao_account.gender",
                          "REFRESH_TOKEN"
          ],
        },
        success: async function (response) {
          // 통신 성공 후 , 카카오 계정 정보를 서버에 전달 
          const kakaoemail = response.kakao_account.email;
          console.log(kakaoemail)
          //response data => kakao login user data variables 
          this.userData =  response;
          let nickname = this.userData.properties.nickname;
          let profile_image = this.userData.properties.profile_image;
          let thumbnail_image = this.userData.properties.thumbnail_image;
          let email = this.userData.kakao_account.email;
          //let age_range = this.this.userData.kakao_account.age_range;
          let birthday = this.userData.kakao_account.birthday;
          let gender = this.userData.kakao_account.gender;
          this.userId = this.userData.properties.nickname;
          const tokenNumber = window.Kakao.Auth.getAccessToken();
          console.log("tokenNumber ====================>" + tokenNumber );
          console.log(response);
          console.log("login start! ========> start login process" );

          //kakao user Data List  == > get 
         axios.get('http://localhost:8082/mymy/insertuserData',{
                    params: {
                    nickname: nickname,
                    profile_image : profile_image,
                    thumbnail_image : thumbnail_image,
                    email : email,
                    birthday : birthday,
                    gender : gender,
                    tokenNumber: tokenNumber,
            }
        })
        //promise//
        .then((res)=>{
            console.log(res);
        })   
          },
        fail: function (error) {
          console.log(error)
        },
      })
      alert("로그인되었습니다!");
      //! 로그인 후 , 메인으로 redirect시 세션이 끊어짐 //
      //window.location.href = "/"

    },
    fail: function (error) {
      console.log(error)
    },
  })

},
dologOut(){
  window.Kakao.Auth.logout(res =>{
    if(!res)return ;
  
  });
}
}
}
</script>
<style>

/* BASIC */

html {
  background-color: #56baed;
}

body {
  font-family: "Poppins", sans-serif;
  height: 100vh;
}

a {
  color: #92badd;
  display:inline-block;
  text-decoration: none;
  font-weight: 400;
}

h2 {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  text-transform: uppercase;
  display:inline-block;
  margin: 40px 8px 10px 8px; 
  color: #cccccc;
}



/* STRUCTURE */

.wrapper {
  display: flex;
  align-items: center;
  flex-direction: column; 
  justify-content: center;
  width: 100%;
  min-height: 100%;
  padding: 20px;
}

#formContent {
  -webkit-border-radius: 10px 10px 10px 10px;
  border-radius: 10px 10px 10px 10px;
  background: #fff;
  padding: 30px;
  width: 90%;
  max-width: 450px;
  position: relative;
  padding: 0px;
  -webkit-box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
  box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
  text-align: center;
}

#formFooter {
  background-color: #f6f6f6;
  border-top: 1px solid #dce8f1;
  padding: 25px;
  text-align: center;
  -webkit-border-radius: 0 0 10px 10px;
  border-radius: 0 0 10px 10px;
}



/* TABS */

h2.inactive {
  color: #cccccc;
}

h2.active {
  color: #0d0d0d;
  border-bottom: 2px solid #5fbae9;
}



/* FORM TYPOGRAPHY*/

input[type=button], input[type=submit], input[type=reset]  {
  background-color: #56baed;
  border: none;
  color: white;
  padding: 15px 80px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  text-transform: uppercase;
  font-size: 13px;
  -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
  box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  margin: 5px 20px 40px 20px;
  -webkit-transition: all 0.3s ease-in-out;
  -moz-transition: all 0.3s ease-in-out;
  -ms-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

input[type=button]:hover, input[type=submit]:hover, input[type=reset]:hover  {
  background-color: #39ace7;
}

input[type=button]:active, input[type=submit]:active, input[type=reset]:active  {
  -moz-transform: scale(0.95);
  -webkit-transform: scale(0.95);
  -o-transform: scale(0.95);
  -ms-transform: scale(0.95);
  transform: scale(0.95);
}

input[type=text] {
  background-color: #f6f6f6;
  border: none;
  color: #0d0d0d;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 5px;
  width: 85%;
  border: 2px solid #f6f6f6;
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type=text]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
  color: #cccccc;
}



/* ANIMATIONS */

/* Simple CSS3 Fade-in-down Animation */
.fadeInDown {
  -webkit-animation-name: fadeInDown;
  animation-name: fadeInDown;
  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
}

@-webkit-keyframes fadeInDown {
  0% {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }
  100% {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }
  100% {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

/* Simple CSS3 Fade-in Animation */
@-webkit-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@-moz-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@keyframes fadeIn { from { opacity:0; } to { opacity:1; } }

.fadeIn {
  opacity:0;
  -webkit-animation:fadeIn ease-in 1;
  -moz-animation:fadeIn ease-in 1;
  animation:fadeIn ease-in 1;

  -webkit-animation-fill-mode:forwards;
  -moz-animation-fill-mode:forwards;
  animation-fill-mode:forwards;

  -webkit-animation-duration:1s;
  -moz-animation-duration:1s;
  animation-duration:1s;
}

.fadeIn.first {
  -webkit-animation-delay: 0.4s;
  -moz-animation-delay: 0.4s;
  animation-delay: 0.4s;
}

.fadeIn.second {
  -webkit-animation-delay: 0.6s;
  -moz-animation-delay: 0.6s;
  animation-delay: 0.6s;
}

.fadeIn.third {
  -webkit-animation-delay: 0.8s;
  -moz-animation-delay: 0.8s;
  animation-delay: 0.8s;
}

.fadeIn.fourth {
  -webkit-animation-delay: 1s;
  -moz-animation-delay: 1s;
  animation-delay: 1s;
}

/* Simple CSS3 Fade-in Animation */
.underlineHover:after {
  display: block;
  left: 0;
  bottom: -10px;
  width: 0;
  height: 2px;
  background-color: #56baed;
  content: "";
  transition: width 0.2s;
}

.underlineHover:hover {
  color: #0d0d0d;
}

.underlineHover:hover:after{
  width: 100%;
}



/* OTHERS */

*:focus {
    outline: none;
} 

#icon {
  width:60%;
}


</style>
