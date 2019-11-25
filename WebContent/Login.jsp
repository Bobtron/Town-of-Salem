<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Town of SC</title>
</head>
<body>

Town of SC

<form name="loginform" method="POST" action="LoginServlet">
	Username
	<input type="text" name="user" placeholder="Enter your username"><br>
	Password
	<input type="password" name="pass" placeholder="Enter your password"><br>
	<input type="submit" value="Login"/>
</form>

</body>
</html>