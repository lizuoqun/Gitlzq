<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>main.jsp</title>
</head>
<body>
	当前用户: ${USER.username}
	<a href="${pageContext.request.contextPath}/logout">退出</a>
</body>
</html>