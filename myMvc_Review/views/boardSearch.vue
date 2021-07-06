<template>
    <div>
        <h1> 게시물 검색 </h1>
    <div class="input-group mb-3" id="form">
         <input type="text" v-model= "bTitle" ref="bTitle" id="bTitle" class="form-control" placeholder="게시물검색" aria-label="Username" aria-describedby="basic-addon1">
         <button type="button" id="searchButton" @click="searchBoard()" class="btn btn-dark">검색</button>
    </div>
   <div class="datalist" v-for="(user, idx) in boardData" :key="idx">
    <div class="boardTitle">
        <h3><a :href="'/Board_view?bId=' + user.bId" >제목: {{user.bTitle}}</a> </h3>

    </div>
    <div class="board_number">
        <small>게시물 번호{{user.bId}}</small>
    </div>
    <div class="board_date">
    <small>글쓴이 : {{user.bName}}</small>
    </div>
    <div class="board_user_name">
    <small>조회수  : {{user.bHit}}</small>
    </div>
    <div class="board_number">
    <small >날짜 : {{user.bDate}}</small>
    </div>
 
 
</div>





    </div>
</template>
<script>

import axios from 'axios'
export default {
    data(){
        return{
            bTitle:''
            ,boardData:''
        }
    },
    setup() {
    
    },
    methods:{
        searchBoard(){
             console.log("get Board List" );
            
             
            //searchBoard 
            console.log("searchBoard =====> "+ this.bTitle);
            if(!this.bTitle){
                alert("검색하실 게시물 제목을 입력해주세요.");
                return false;
                
            }
            axios.get('http://localhost:8082/mymy/BoardSearch',{
                    params: {
                    bTitle:this.bTitle,
            }
        })
        //promise
        .then((res)=>{
                console.log(res)
                this.boardData = res.data;
                if(this.boardData.length == 0){
                    alert("데이터가 존재하지 않습니다.");
                    return false;
                }
                
            })
        
        
        }
    }
}
</script>
<style scoped>
   @import 'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css';
   #form{
       width:700px;
       margin: 0 auto;
       margin-top:40px;


   }
</style>
