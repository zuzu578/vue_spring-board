<template>
<h1>게시판 상세보기</h1>
<div class="board_view_area" v-for="(user, idx) in boardData" :key="idx">
        <div class="board_number">
            <small>게시물 번호: {{user.bId}}</small>
        
          </div>
          <div class="board_hit_count">
         
            <small>조회수 : {{user.bHit}}</small>
          </div>
          <div class="board_title">
              <input type="text" v-model= "bTitle" ref="bTitle" class="form-control" placeholder="제목" aria-label="Username" aria-describedby="basic-addon1">
               
          </div>
          <div class="board_date">
              <small>작성일 : {{user.bDate}} </small>

           
          </div>
          
          <div class="board_user_name">
              <input type="text" v-model= "bName" ref="bName" class="form-control" placeholder="닉네임 " aria-label="Username" aria-describedby="basic-addon1">
           
          </div>
          
          
          <div class="board_content">
              <label for="floatingTextarea">내용을 입력하세요.</label>
            <textarea class="form-control" v-model= "bContent" ref="bContent" placeholder="내용" id="floatingTextarea"></textarea>
            
            </div>

          <div class="btnWrap">
       
        <button type="button" @click="doModify()" class="btn btn-primary">수정</button>
        
        
    </div>	
        
    </div>
    

    


</template>

<script>

import axios from 'axios'
export default {
    
	data() {
		return {
			body:this.$route.query
			,bId:this.$route.query.bId // => 게시물 시퀀스 
            ,boardData : ''
            ,bName:'' // => 사용자 이름
			,bTitle:'' // => 제목 
            ,bContent:'' // => 내용
            ,bDate: '' // => 날짜
            ,bHit:''   // => 조회수 
			
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

    }
   
	,mounted() {
        /*el이 새로 생성된 vm.$el로 
        대체된 인스턴스가 마운트 된 직후 호출됩니다.
         루트 인스턴스가 문서 내의 엘리먼트에 마운트 되어 있으면,
          mounted가 호출 될 때 vm.$el도 문서 안에 있게 됩니다.
        */
       // 게시물 제목을 click => vue rendering => mounted() => axios(ajax) => boardList 
		this.fnGetView();
	}
    
	,methods:{
       //게시물 상세 내용 보기 
		fnGetView() {
            var bId = this.bId;
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
                    
                console.log(res);
                this.boardData =res.data;
                console.log("boardData!!!"+ this.boardData);
                /*
                ,bName:'' // => 사용자 이름
			    ,bTitle:'' // => 제목 
                ,bContent:'' // => 내용
                ,bDate: '' // => 날짜
                ,bHit:'' 
                */
				
                
                
			})
			.catch((err)=>{
				console.log(err);
			})
		}
        //게시물 수정 function
		,doModify(){
            if(!this.bTitle){
                alert("제목을 입력하세요.");
                return false;
            }
            if(!this.bName){
                alert("닉네임을 입력하세요.");
                return false;
            }
            if(!this.bContent){
                alert("내용을 입력하세요.");
                return false;
            }
			var bId = this.bId; //==> 게시물 sequence
            console.log(bId);
            
            var Q1 = confirm("게시물을 수정할까요?");
            if(!Q1){
                alert("게시물 수정을 취소합니다.");
                return false;
            }else{
                alert("게시물을 수정합니다.");
                //! -- post 방식 ==> cross origin 에서 (cors) 문제
                 axios.get('http://localhost:8082/mymy/modify',{
                    params: {
                    bId : this.bId,
                    bName : this.bName,
                    bTitle : this.bTitle,
                    bContent : this.bContent,
            }
        })
        //promise
            .then((res)=>{
                console.log(res)
                if(res.data.success){
                   //
                   // window.location.href = "/";
                }else{
                    alert("수정하였습니다!");
                    window.location.href = "/";
                }
            })

            }

		}
	}
}
</script>

<style scoped>
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
    @import 'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css';
</style>