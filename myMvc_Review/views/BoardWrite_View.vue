<template>

<h1> 게시글 작성 </h1>
<div class="AddWrap">
    <!-- v-model 양뱡향데이터전송으로 상세 데이터 넣어준다 -->
    <div class="input-group mb-3">

<input type="text" v-model= "bTitle" ref="bTitle" class="form-control" placeholder="닉네임" aria-label="Username" aria-describedby="basic-addon1">
</div>
    <!-- v-model 양뱡향데이터전송으로 상세 데이터 넣어준다 -->
    <div class="input-group mb-3">

<input type="text" v-model= "bName" ref="bName" class="form-control" placeholder="제목" aria-label="Username" aria-describedby="basic-addon1">
</div>

    <!-- v-model 양뱡향데이터전송으로 상세 데이터 넣어준다 -->
<div class="form-floating">
<textarea class="form-control" v-model= "bContent" ref="bContent" placeholder="내용을 입력하세요." id="floatingTextarea2" style="height: 100px"></textarea>
<label for="floatingTextarea2">내용</label>
</div> 
<div class="btnWrap">
<a href="javascript:;" @click="fnList" class="btn">목록</a>
<a v-if="!num" href="javascript:;" @click="fnAddProc" class="btnAdd btn">등록</a>
<a v-else href="javascript:;" @click="fnModProc" class="btnAdd btn">수정</a>
</div>	
</div>




</template>

<script>
import axios from 'axios'
export default {
data() {
    return{
        bTitle:''
        ,bName:''
        ,bContent:''
        ,body:this.$route.query 
        ,form:'' //form 전송 데이터
        
    }
}
,mounted() { //최초 로딩 시 실행
    if(this.num) { // num 값이 있으면 상세 데이터 호출한다.
        this.fnGetView();
    }
}
,methods:{
    fnList(){
        delete this.body.num;
        this.$router.push({path:'./',query:this.body});
        
    }
    //게시물 작성하기
    ,fnGetView() {
        axios.get('http://localhost:8082/mymy/Dowrite'+this.body.num,{params:this.body})
        .then((res)=>{
            this.bTitle = res.data.bTitle[0];
            this.bName = this.view.bName;
            this.bContent = this.view.bContent;
        })
        .catch((err)=>{
            console.log(err);
        })
    }
    ,fnView() {
        this.$router.push({path:'./view',"query":this.body});
    }	
    ,fnAddProc() {
        if(!this.bTitle) {
            alert("제목을 입력해 주세요");
            this.$refs.bTitle.focus(); //방식으로 선택자를 찾는다.
            return false;
        }
        if(!this.bName){
            alert("닉네임을 입력해주세요");
            this.$refs.bName.focus();
            return false;
        }
        if(!this.bContent){
            alert("내용을 입력해주세요");
            this.$$refs.bContent.focus();
            return false;
            
        }

        this.form = {
            bTitle:this.bTitle
            ,bName:this.bName
            ,bContent:this.bContent
    
        } 
        let bTitle = this.form.bTitle;
        let bName = this.form.bName;
        let bContent = this.form.bContent;
        var params = new URLSearchParams();
        params.append('bTitle',bTitle);
        params.append('bName',bName);
        params.append('bContent',bContent);
        
        console.log("bTitle=====>" + bTitle)
        console.log("bName=====>" + bName)
        console.log("bContent=====>" + bContent)
        console.log('http://192.168.0.52:8082/mymy/Dowrite',params);
        axios.post('http://localhost:8082/mymy/Dowrite',params)
        /*
        크롬 종료
        open "/Applications/Google Chrome.app" --disable-web-security
        터미널 접속
        보안 해제 flag 설정 및 사용 디렉토리 지정하여 크롬 실행 --args disable-web-security 로 보안을 해제해줘야 한다.
        */
        // 'http://localhost:8082/mymy/Dowrite';
        .then((res)=>{
            if(res.data.success) {
                //alert('등록되었습니다.');
                this.fnList();
            } else {
                alert("등록되었습니다.");
                window.location.href = '/';
            }
        })
        .catch((err)=>{
            console.log(err);
        })
        
    }
    ,fnModProc() {
        if(!this.bTitle) {
            alert("제목을 입력해 주세요");
            this.$refs.bTitle.focus(); //방식으로 선택자를 찾는다.
            return false;
        }
        if(!this.bName){
            alert("닉네임을 입력해주세요");
            this.$refs.bName.focus();
            return false;
        }
        if(!this.bContent){
            alert("내용을 입력해주세요");
            this.$$refs.bContent.focus();
            return false;
            
        }

        this.form = {
            bTitle:this.bTitle
            ,bName:this.bName
            ,bContent:this.bContent
        },
        
axios.put('http://localhost:8082/mymy/Dowrite',this.form)
        .then((res)=>{
            if(res.data.success) {
                alert('수정되었습니다.');
                this.fnView();
            } else {
                alert("실행중 실패했습니다.\n다시 이용해 주세요");
            }
        })
        .catch((err)=>{
            console.log(err);
        })
    }
}	
}	




</script>
<style>
.btnWrap{
    margin-top:100px;
    ;
    text-align:right;
}
.AddWrap{
    width:700px;
    margin:0 auto;
}
@import 'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css'
</style>
