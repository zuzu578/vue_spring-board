<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> Login Page </h1>
	
	<form action="doCheckUser" method="POST">
		
		<input type="text" id="user_id" name="user_id">
		<input type="password" id="user_password" name="user_password">
	
	
		<input type="submit" id="submit" name="submit">
	</form>

	
</body>
</html>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script language="javascript">
$(document)
.on("click","#submit",function(){
	if($("#user_id").val()==""){
		alert("아이디를 입력하세요");
		return false;
	}
	if($("#user_password").val()==""){
		alert("비밀번호를 입력하세요 ");
		return false;
	}
})
</script>