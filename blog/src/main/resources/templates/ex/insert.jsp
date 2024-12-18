<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name = "my" method = "post" action = "memberWrite.do">
		<input type="text" name="id" placeholder="id">
		<input type="password" name="pw" placeholder="password"><br>
		<input type="checkbox" name="language" value="java">자바
		<input type="checkbox" name="language" value="jsp">웹
		<input type="checkbox" name="language" value="html">화면구현
		<input type="checkbox" name="language" value="oracle">DB<br>
		<input type="radio" name="answer" value="yes">예
		<input type="radio" name="answer" value="no">아니오<br>
		<input type="submit" value="전송">
	</form>
</body>
</html>