<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Town of SC: Login</title>
<style>
	.loginform {
		color: white;
		width: 300px;
		margin-left: auto;
		margin-right: auto;
		margin-top: 34%;
	}
	input {
		width: 300px;
		height: 20px;
		margin-bottom: 20px;
	}
	.button {
		width: 306px;
		height: 30px;
		margin-top: 13px;
		background-color: orange;
		font-size: 1.2em;
		color: white;
	}
	body {
		background-image: url("images/welcometitlecropped.png");
		background-repeat: no-repeat;
		background-size: cover;
	}
</style>
</head>
<body>

<form class="loginform" name="loginform" method="POST" action="LoginServlet">
	Username <br>
	<input type="text" name="user" required><br>
	Password <br>
	<input type="password" name="pass" required><br>
	<input class="button" type="submit" value="LOGIN"/>
</form>

</body>
</html>