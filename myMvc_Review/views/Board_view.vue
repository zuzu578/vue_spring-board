<template>
<h1>게시판 상세보기</h1>
<div class="board_view_area" v-for="(user, idx) in boardData" :key="idx">
    <div class="board_title">
        <h1><small>  {{user.bTitle}}</small></h1>
    </div>
    <div class="board_date">
    <small>날짜 : {{user.bDate}}</small>
    </div>
    <div class="board_user_name">
    <small>글쓴이 : {{user.bName}}</small>
    </div>
    <div class="board_number">
    <small >게시물 번호 : {{user.bId}}</small>
    </div>
    <div class="board_hit_count">
    <small>조회수 : {{user.bHit}}</small>
    </div>
    <div class="board_content">
        <!-- <img src="./resources/assets/img/${imgUrl}"> -->
    {{user.bContent}}
    </div>
    <div class="contents_image_area" v-if="user.file_image==null">
      <small> <!-- 이미지가 없는 게시물일 경우 아무런 이미지를 표시하지 않습니다.--> </small>
        
     </div>
     <div class="render" v-else>
    <img v-bind:src="'http://localhost:8082/mymy/resources/assets/img/'+user.file_image">
    </div>
    
    <div class="btnWrap">
<button type="button" @click="doModify()" id="modify" class="btn btn-primary"><a :href="'/modify_view?bId=' + user.bId" >수정</a></button>
<button type="button" @click="doDelete()" id ="delete" class="btn btn-dark">삭제</button>
</div>	
</div>
 
<div class="board_reply_view_area">
    <h4> 댓글 목록 </h4>
    <div class="reply_view" v-for="(reply, idx) in replyData" :key="idx">
        <div class="user_name">
        {{reply.reply_user}}
        </div>
        <div class="content">
        {{reply.reply_content}}
        </div>
        <div class="content_date">
        {{reply.reply_date}}
        </div>
    </div>
</div>
<div class="board_reply_area">
    <h3> 댓글달기 </h3>
<div class="input-group mb-3">
  <input type="text" v-model= "Reply_user" ref="Reply_user" class="form-control" placeholder="닉네임" aria-label="Username" aria-describedby="basic-addon1">
</div>
<div class="form-floating">
  <textarea class="form-control"  v-model= "Reply_content" ref="Reply_content" placeholder="Leave a comment here" id="floatingTextarea"></textarea>
  <label for="floatingTextarea">Comments</label>
</div>
<div class="button_area">
<button type="button" @click="doReply()" class="btn btn-info">작성</button>
</div>
</div>
</template>
<script>

import axios from 'axios'
export default {

data() {
return {
    //kakao oauth 인중 후 , 발급받는 token 을 가져옴 
     accessToken:window.Kakao.Auth.getAccessToken(),
     body:this.$route.query
    ,bId:this.$route.query.bId // => 게시물 시퀀스 
    ,boardData : ''
    ,bName :''
    ,Reply_content:''
    ,Reply_user:''
    ,getReply_content:''
    ,getReply_user:''
    ,getReply_dateTime:''
    ,replyData:''
    ,getImageFile:''
    ,

    }
 }
,created() {
/*
인스턴스가 작성된 후 동기적으로 호출됩니다.
    이 단계에서 인스턴스는 데이터 처리, 
    계산된 속성, 메서드, 감시/이벤트 콜백 등과
    같은 옵션 처리를 완료합니다. 그
    러나 마운트가 시작되지 않았으므로 $el 속성을 아직 사용할 수 없습니다.
*/
// 인스턴스 작성된 후 , 댓글 list 불러오기 
//getReply
let bId = this.bId;

axios.get('http://localhost:8082/mymy/getReply',{
        params:{
            bId:bId,
        }
    })
    .then((res)=>{
        //if success! //
        console.log(res);
        this.replyData = res.data;
        console.log(this.replyData);
    })


}

,mounted() {
    // mounted 
    console.log("get mounted! ");
    //get mounted() ;
    // mounted 되었을때 게시물 상세보기 작동.
    this.fnGetView();
}
,methods:{
//댓글작성
doReply(){
    var check = this.accessToken;
   
    //해당 게시글 번호 에 대한 댓글 작성 
    var bId = this.bId;
    var Reply_content = this.Reply_content
    console.log("Reply_content =============>" +Reply_content);
    var Reply_user = this.Reply_user;
    console.log("Reply_user==================>"+Reply_user);
    if(!Reply_user){
        alert("작성자를 입력해주세요.");
        return false;
    }
    if(!Reply_content){
        alert("내용을 입력해주세요.");
        return false;
    }
     if(!check){
        alert("로그인 후 이용해주세요.");
        return false;
    }
    //writeReply
    axios.get('http://localhost:8082/mymy/writeReply',{
        params:{
            bId:bId,
            Reply_content:this.Reply_content,
            Reply_user:this.Reply_user,

        }
    })
    .then((res)=>{
        //if success! //
        console.log(res);
    })
    alert("댓글작성 완료!");
    window.location.reload();
    

 }, 
//게시물 상세 내용 보기 
//get board List 
fnGetView() {
    var bId = this.bId;
    //board sequence ==> axios  ==> back-end 
    axios.get('http://localhost:8082/mymy/Board_view',{
    params: {
        bId : bId
    }
})
    //promise ==> 비동기 처리에 사용되는 객체 
    // 비동기 ex) 1) ajax , 2) callback , 3) promise 
    //promise 는 어떻게 보면 예외처리 를 해줄수있는 과정 이라고 할수도 있다 
    //promise 
    //ex) backend  ==> data ==> front ==> 
    //data failed === > promise X ===> 아무런 효과가 없음 (에러도 안뜸)
    // promise 
    .then((res)=>{  
        //if transaction successed !  
        console.log(res);
        this.boardData =res.data;
        console.log("boardData!!!"+ this.boardData);
        console.log("bName ===>" + res.data.bName);
        this.getImageFile = res.data;
        console.log("file_image======>" + this.getImageFile);
        /*
        ,bName:'' // => 사용자 이름
        ,bTitle:'' // => 제목 
        ,bContent:'' // => 내용
        ,bDate: '' // => 날짜
        ,bHit:'' 
        */
    })
    // if transcation failed. 
    .catch((err)=>{
        console.log(err);
    })
}
//data delete () 
,doDelete(){
    //token ==> user check 
    const tokens = this.accessToken;
//alert(tokens);
if(!tokens){
// if kakao OAUTH is null => login X 
console.log("로그인안됨.");
alert("권한이 없습니다.");
return false;
}else{
    var bId = this.bId; //==> 게시물 sequence
    console.log(bId);
    console.log(this.boardData.bName);
    var Q1 = confirm("게시물을 삭제할까요?");
    if(!Q1){
        alert("게시물 삭제를 취소합니다.");
        return false;
    }else{
        alert("게시물을 삭제합니다.");
            axios.get('http://localhost:8082/mymy/delete',{
            params: {
                // ==> axios transaction 할때 parameter 를 boardsequence 로 전달 합니다. 
            bId : bId
    }
})
//promise
    .then((res)=>{
        console.log(res)
        if(res.data.success){
            //
            // window.location.href = "/";
            console.log("promise ====> success DataList")
        }else{
            alert("삭제되었습니다.");
            window.location.href = "/home?pageNum=1";
        }
    })
  }
        }
}
,
doModify(){
    //modify 
    //<a :href="'/modify_view?bId=' + user.bId" >
       const tokens = this.accessToken;
//alert(tokens);
// 로그인이 되어있지 않은 경우 ( 토큰이 없는경우 )
    if(!tokens){
        alert("권한이 없습니다.");
        return false;
    }
}

  }
 }
</script>

<style scoped>

.reply_view{
    border:1px solid black;
    text-align: left;
    display:flex;
    justify-content: space-between;
}
.board_reply_view_area{
    margin-bottom:1000px;
    margin-top:20px;
    width:1000px;
    margin:0 auto;
    


}
.button_area{
    float:left;
}
.board_reply_area{
    width: 1000px;
    margin: 0 auto;
}
.form-floating{
    /*
    width:1000px;
    margin-top:20px;
    margin:0 auto;
    */
}
a{
text-decoration: none;
color:white;
}
.board_view_area{
width:1000px;
margin:0 auto;

}
.board_title{
text-align: left;
}
.board_date{
text-align: right;
font-size:20px;
font-weight: bold;
}
.board_user_name{
text-align: left;
font-size:20px;
font-weight:bold;

}
.board_number{
text-align: left;
font-size: 20px;
font-weight: bold;
}
.board_hit_count{
text-align: left;
font-size: 20px;
font-weight: bold;
}
.board_content{
text-align: left;
font-size:20px;
height:auto;
}
.button_area{
text-align: right;
margin-top:100px;

}
.btnWrap{
text-align: right;
margin-top:150px;
}
.board_reply_area{
    margin-top:50px;
    margin-bottom:200px;


}
@import 'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css';
</style>
