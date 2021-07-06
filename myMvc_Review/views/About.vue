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
          </div>
              
          </div>

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

    

    }

  }
}
</script>
<style scoped>
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

