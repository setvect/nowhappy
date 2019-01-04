<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String imageUrl = (String)request.getAttribute(WebAttributeKey.IMAGE_URL);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap1.css"/>" rel="stylesheet">
<link href="<c:url value="/css/bootswatch.min.css"/>" rel="stylesheet">
<link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>

<script type="text/javascript">
	var ImageUpload = new Object();
	ImageUpload.apply = function(){
		var sHTML = "<img src='<%=imageUrl%>'/>";
		
		// 하드 코딩..원래는 이미지 팝업창 오픈 할때 이미지를 받을 객체의 파라미터를 넣어야 됨.
		opener.nhn.husky.PopUpManager.setCallback(window, 'PASTE_HTML', [sHTML]);
		window.close();
	};
</script>
</head>
<body>
<div class="popup">
	<input type="button" value="확인" onclick="ImageUpload.apply()"/><br/>
	<img src='<%=imageUrl%>' onclick="ImageUpload.apply()"/>
</div>
</body>
</html>