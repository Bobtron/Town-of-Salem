<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Justin Ly CSCI 201 Lab 5</title>
</head>
<body>
	<div id="formerror"></div>
	<form name="myform" method="GET" action="TestMultiThread">
		First Name:
		<input type="text" name="fname" value="${firstValue}"/>
		${firstError}
		<br>
		Last Name:
		<input type="text" name="lname" value="${lastValue}"/>
		${lastError}
		<br>
		College to Apply to:
		<select name="college">
              <option value="UCB" ${college0} >University of California - Berkeley</option>
              <option value="UCLA" ${college1} >University of California - Los Angeles</option>
              <option value="USC" ${college2} >University of Southern California</option>
              <option value="UIUC" ${college3} >University of Illinois at Urbana-Champaign</option>
              <option value="BU" ${college4} >Boston University</option>
        </select>
        ${collegeError}
        <br>
        Please enter the amount you're willing to donate towards the admission office:
       	<input type="number" name="amount" value="${bribeValue}"/>
       	${bribeError}
       	<br>
        Enter your Personal Statement Here: 
        <br>
		<textarea name="essay" rows="5" cols="50" wrap="soft">${essayValue}</textarea>
		${personalError}
		<br>
		<input type="submit" name="submit" value="Submit" />
		<input type="reset" name="reset" value="Reset" />
	</form>
</body>
</html>