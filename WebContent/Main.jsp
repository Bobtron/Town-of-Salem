<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Town of Salem</title>
<script>
	var webSocketUri =  "ws://localhost:8080/Town-of-Salem/ws";
	var socket, gameDoc, chatboxDoc;
	
	
	document.addEventListener('DOMContentLoaded', function (event) {
	    document.getElementById('mainframe').addEventListener('load', function() {
	    	//iframe for main.jsp
			var mainiframe = document.getElementById('mainframe');
			gameDoc = mainiframe.contentDocument || mainiframe.contentWindow.document;
	    })
	})
	
	/* gameDoc.addEventListener('DOMContentLoaded', function (event) {
	    gameDoc.getElementById('chatframe').addEventListener('load', function() { 	
	    	//iframe for game.html
			var chatiframe = document.getElementById('chatframe');
			chatboxDoc = chatiframe.contentDocument || chatiframe.contentWindow.document;
	    })
	}) */
	
	function connectToServer() {
		socket = new WebSocket(webSocketUri);
		socket.onmessage = function(event) {
			console.log(event.data);
			var array = event.data.split("|");
			if ((array[0].localeCompare("CHAT") == 0) && (array[1].localeCompare("ALL") == 0)){
				chatboxDoc.getElementById("mychat").innerHTML += array[2] + ": " + array[3] + "<br />";	
			}
		};
	}
	
	function sendChat(){
		if(state=="NIGHT") sendChatMafia();
		if(state=="DAY") sendChatAll();
	}
	
	function sendChatAll() {
		if(chatboxDoc.chatform.message.value){
			var str = "CHAT|ALL|" + chatboxDoc.chatform.message.value;
			console.log(str);
			sendMessage(str);
			chatboxDoc.chatform.message.value = "";
		}else{
			var str = chatboxDoc.chatform.terminal.value;
			console.log(str);
			sendMessage(str);
		}
		return false;
	}
	function sendChatMafia() {
		if(chatboxDoc.chatform.message.value){
			var str = "CHAT|MAFIA|" + chatboxDoc.chatform.message.value;
			console.log(str);
			sendMessage(str);
			chatboxDoc.chatform.message.value = "";
		}else{
			var str = chatboxDoc.chatform.terminal.value;
			console.log(str);
			sendMessage(str);
		}
		return false;
	}
	
	function create(){
		var name = gameDoc.game_connection.name.value;
		var num = 8;
		var str = "META|CREATE|" + name + "|" + num;
		console.log(str);
		sendMessage(str);
	}
	
	function join(){
		var name = document.game_connection.name.value;
		var str = "META|JOIN|" + name;
		console.log(str);
		sendMessage(str);
	}
	
	function sendMessage(str) {
		socket.send(str);
	}
</script>

<style>
	iframe {
		width: 100vw;
		height: 100vh;
		border: none;
	}
	body {
		margin: 0px;
	}
</style>
</head>
<body onload="connectToServer();">

<iframe src="Lobby.jsp" id="mainframe" name="mainframe"></iframe>

</body>
</html>
