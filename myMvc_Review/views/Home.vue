<template>
  <div class="container">
 <div class="list-group" id="boardList">
  <h1> vue 게시판 </h1>
  <div class="image_items">
  <img src="https://upload.wikimedia.org/wikipedia/commons/f/f1/Vue.png">
  </div>
  <div class="datalist" v-for="(user, idx) in boardData" :key="idx">
    <div class="boardTitle">
        <h3><a :href="'/Board_view?bId=' + user.bId"  style="black">제목: {{user.bTitle}}</a> </h3>

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
  <!-- 데이터가 없는경우 -->
 <a v-if="boardData.length == 0" class="list-group-item list-group-item-action">
      게시물이 존재하지않습니다.
  </a>
  
 
<!-- pageNation Area !-->
<div class="paging">
  <nav aria-label="Page navigation example">
  <ul class="pagination">
    
    <li class="page-item"><a class="page-link" :href="'/home?pageNum=' + pageNum" @click="prev()">prev</a></li>
    <li class="page-item"><a class="page-link" :href="'/home?pageNum=' + pageNum" @click="next()">next</a></li>
    
  </ul>
</nav>
</div>
</div>
</div>
</template>

<script>

import axios from 'axios'

export default {
  data(){
    return{
      pageNum:this.$route.query.pageNum, //현재 페이지 number를 return 
      boardData : ''
    }
  },
  methods :{
    //이전페이지 보여주기 
    prev(){
      var pageNum = this.pageNum;
      var currentPageNum = parseInt(pageNum) -1;
      //alert(currentPageNum);
      this.pageNum = currentPageNum;
      
    },
    //다음페이지 보여주기 
    next(){
      var pageNum = this.pageNum;
      var currentPageNum = parseInt(pageNum) +1;
      //alert(currentPageNum);
      this.pageNum = currentPageNum;
       
    }

  },
  created(){
     axios.get('http://localhost:8082/mymy/BoardList',{
        params:{
          pageNum : this.pageNum
        }
      })
      .then(res =>{
        console.log(res);
        this.boardData = res.data;
      })
  },
  mounted(){
    //alert("mounted!");
  },

  computed: {

  },
  name: 'App',
  components: {
  },
  
}
</script>

<style>
a{
  text-decoration: none;
  color:black;
}
.container{
  width:1300px;
}
.image_items img{
  width:8%;
}
.paging{
  width:100px;
  margin:0 auto;
  margin-top:30px;

}
.boardList{
  border-radius: 10px;
}
@import 'https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css'
</style>

