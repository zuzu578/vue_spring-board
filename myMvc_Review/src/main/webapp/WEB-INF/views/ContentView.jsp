<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<title>Insert title here</title>
<style>
.Main_area{
	width:600px;
	margin:0 auto;
	margin-top:100px;
	
}
a{
color:white;
text-decoration:none;


}

</style>
</head>

<body>
	<div class="Main_area">
    <table class="table table-hover">
    	<tr><td>조회수</td> <td>${ContentView.bHit}</td></tr>
   		<tr><td>게시물번호</td> <td>${ContentView.bId }</td></tr>
    	<tr><td>제목</td> <td>${ContentView.bTitle }</td></tr>
    	<tr><td>작성자</td> <td>${ContentView.bName }</td></tr>
    	<tr><td>작성일</td> <td>${ContentView.bDate }</td></tr>
    	<tr><td>내용</td> <td><textarea readonly>${ContentView.bContent }</textarea></td></tr>
    	
    	
    </table>
    
    <div class="bottom_buttons">
    <button type="button"class="btn btn-primary"><a href="delete?bId=${ContentView.bId }">삭제</a></button>
    <button type="button" class="btn btn-primary"><a href="modify_view?bId=${ContentView.bId }">수정</a></button>
    <button type="button" class="btn btn-primary"><a href="BoardList">글 목록</a></button>
    </div>
   </div>
</body>
<script>


</script>

</html>
