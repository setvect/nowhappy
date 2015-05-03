<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loadPage = (String)request.getAttribute(WebAttributeKey.LOAD_PAGE);
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
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<script src= "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>
<script type="text/javascript">	
	function pageActionContrller(contextPath){
		// /nowhappy/   =>   /nowhappy
		this.contextPath = contextPath.substring(0, contextPath.lastIndexOf("/"));
		
		/**
		 * loadPage: 앞에 '/' 문자 있어야 됨
		 */
		this.loadPage = function(loadPage){
			 var page = this.getUrl(loadPage);
			$("._mainAear").load(page);
		};
		
		/**
		 * contextPath와 더해 URL 만듦 
		 * url: 앞에 '/' 문자 있어야 됨.		 
		 */
		this.getUrl = function(url){
			return this.contextPath + url;
		}
	};
	
	// 전역 변수
	var mainCtrl= new pageActionContrller("<c:url value="/"/>");
	
	$(function(){
		mainCtrl.loadPage("<%=loadPage%>");
	});
	
</script>
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="../" class="navbar-brand">Now Happy</a>
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse" id="navbar-main">
				<ul class="nav navbar-nav">
					<li><a href="#">글</a></li>
					<li><a href="#">꿈</a></li>
					<li><a href="#">꿈</a></li>
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">Manager<span	class="caret"></span></a>
						<ul class="dropdown-menu" aria-labelledby="themes">
							<li><a href="#">게시판관리</a></li>
							<li class="divider"></li>
							<li><a href="#">ㅁㅁ</a></li>
							<li><a href="#">ㅍㅍ</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container _mainAear">
		<!-- page load -->
	</div>
	
</body>
</html>
