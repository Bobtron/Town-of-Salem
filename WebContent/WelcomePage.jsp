<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Town of SC</title>
<style>

	#wrapper {
		margin-top: 28%;
		height: 393px;
		color: white;
	}
	.fields {
		width: 275px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 10px;
	}
	.guest-wrapper {
		width: 275px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 0px;
	}
	#guest {
		margin-bottom: 0px;
	}
	#hidden {
		width: 100%;
		background-color: tan;
		text-align: center;
		display: none;
	}
	#user {
		margin: 10px;
		width: 240px;
		height: 28px;
		font-size: 1.0em;	
	}
	#start {
		width: 200px;
		margin-bottom: 8px;
	}
	input {
		width: 200px;
		height: 20px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 20px;
	}
	.button {
		width: 100%;
		height: 35px;
		background-color: orange;
		font-size: 1.3em;
		color: white;
	}
	.clearfloat {
		clear: both;
	}
	body {
		background-image: url("images/welcomecrop3.png");
		background-repeat: no-repeat;
		background-size: cover;
		overflow: hidden;
	}
</style>
</head>
<body>

<div id="wrapper">
	
	<form action="Login.jsp">
		<div class="fields">
			<input class="button" type="submit" value="LOGIN"/>
		</div>
	</form>
		
	<form action="Register.jsp">
		<div class="fields">
			<input class="button" type="submit" value="REGISTER"/>
		</div>
	</form>
			
	<div class="guest-wrapper">
		<input id="guest" class="button" type="submit" onclick="displayForm()" value="PLAY AS GUEST"/>
		<form name="guestform" id="hidden" action="Lobby.jsp" onsubmit="setUsername()">
			<input id="user" type="text" name="user" placeholder="Enter temporary username" required>
			<input id="start" class="button" type="submit" value="START"/>
		</form>
	</div>
		
</div> <!-- wrapper -->

<script>
	function displayForm() {
		document.getElementById("hidden").style.display = "block";
	}
	
	function setUsername() {
		session.setAttribute("username", "Guest-" + document.guestform.user.value);
		console.log(session.getAttribute("username"));
	}
</script>

</body>
</html>
