<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Town of SC: Login</title>
<style>

	#loginform {
		margin-top: 26%;
		color: white;
	}
	.fields {
		width: 300px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 10px;
	}
	#thumbnail-wrapper {
		width: 739px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 20px;
	}
	input {
		width: 250px;
		height: 20px;
		margin-bottom: 20px;
	}
	.button {
		width: 257px;
		height: 30px;
		margin-top: 10px;
		background-color: orange;
		font-size: 1.2em;
		color: white;
	}
	.thumbnail {
		border: 1px solid #000;
		width: 80px;
		height: 80px;
		margin: 5px;
		float: left;
		overflow: hidden;
		opacity: 0.6;
		cursor: pointer;
	}
	.thumbnail:hover {
		opacity: 1.0
	}
	.thumbnail > img {
		height: 100%;
	}
	.credit {
		font-size: 0.5em;
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

<form id="loginform" name="loginform" method="POST" action="LoginServlet">
	<div class="fields">
		Username <br>
		<input type="text" name="user" required><br>
		Password <br>
		<input type="password" name="pass" required><br>
	</div>
	
	<div id="thumbnail-wrapper">
		<div class="thumbnail">
			<img src="images/thumb_1.png" alt="Boy 1">
		</div>

		<div class="thumbnail">
			<img src="images/thumb_2.png" alt="Girl 1">
		</div>

		<div class="thumbnail">
			<img src="images/thumb_3.png" alt="Girl 2">
		</div>

		<div class="thumbnail">
			<img src="images/thumb_4.png" alt="Man">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_5.png" alt="Girl 3">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_6.png" alt="Girl 4">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_7.png" alt="Girl 5">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_8.png" alt="Boy 2">
		</div>
		
		<div class='clearfloat'></div>
		
		<div class="thumbnail">
			<img src="images/thumb_9.png" alt="Boy 3">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_10.png" alt="Boy 4">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_11.png" alt="Boy 5">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_12.png" alt="Girl 6">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_13.png" alt="Girl 7">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_14.png" alt="Girl 8">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_15.png" alt="Girl 9">
		</div>
		
		<div class="thumbnail">
			<img src="images/thumb_16.png" alt="Girl 10">
		</div>

		<div class='clearfloat'></div>

	</div> <!-- #thumbnail-wrapper -->
		
	<div class="fields">
		<input class="button" type="submit" value="LOGIN"/>
	</div>

</form>

<div class="credit">Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>


<script type="text/javascript">
	// document.getElementById("thumbnail").onclick = function() {
	// 	this.style.opacity = 1.0;
	// }

	let thumbnails = document.querySelectorAll(".thumbnail");
	for( let i = 0; i < thumbnails.length; i++){
		thumbnails[i].onclick = function() {
			for(let j = 0; j<thumbnails.length; j++){
				if(j != i){
					thumbnails[j].style.opacity = 0.6;
					thumbnails[j].style.borderColor = "#000000";
				}
			}
			thumbnails[i].style.opacity = 1.0;
			//change to red border
			thumbnails[i].style.borderColor = "#F00000";
		}

	}
	
</script>

</body>
</html>