<template>
  <div class="about">
    <h1>나의 정보 </h1>

    <div class="board_view_area" v-for="(user, idx) in userData" :key="idx">
        <div class="user_image">
          <img :src="user.thumbnail_image"> 
          </div>
          <div class="user_info">
          <div class="user_name">
           유저 이름: {{user.nickname}}
          
          </div>
          <div class="user_gender">
             유저 성별: {{user.gender}}
          </div>
          <div class="user_birth">
            유저 생년월일: {{user.birthday}}
          </div>
          <div class="user_email">
            유저 이메일 : {{user.email}}
             <input type="hidden" v-model= user.email ref="email_hidden"  name="email_hidden" id="email_hidden">
          </div>
              
          </div>

    </div>
    <div class="sign_up">
    <button type="button" @click="doSignUp" class="btn btn-outline-dark">회원가입하기</button>
    </div>
  </div>
</template>
<script>

import axios from 'axios'
export default {   
 data() {
		return {
			accessToken:window.Kakao.Auth.getAccessToken(),
      userData : '',
		}
	},
   created(){
       
       axios.get('http://localhost:8082/mymy/selectUserData',{
                    params: {
                    tokens : this.accessToken,   
            }
             })
             .then((res)=>{ 
              console.log(res);
              this.userData =res.data;
              console.log("userData!!!"+ this.userData);      
         })
   
  },
  methods:{
    kakoLoginBtn() {

    

    },
    doSignUp(){
      var user_email = document.getElementById("email_hidden").value;
      //alert(checkSignUp)
      // user email select 후 , 중복되는 이메일 있는경우 중복 회원가입 막기
       axios.get('http://localhost:8082/mymy/CheckCountUser',{
                    params: {
                    user_email,   
            }
             })
             .then((res)=>{ 
              console.log(res);
              console.log("data select count");   
              if(res.data == '1'){
                alert("이미 회원가입한 유저입니다.");
                return false;
              }else{
                window.location.href ='/doSignUp';
              }
         })
        
      
    }

  }
}
</script>
<style scoped>
.sign_up{
  margin-top:100px;

}
.board_view_area{
  align-items: center;
  justify-content: center;
}
.user_info{
  margin-left:100px;
}
.board_view_area{
  display:flex;
  margin-top:40px;

}
</style>

