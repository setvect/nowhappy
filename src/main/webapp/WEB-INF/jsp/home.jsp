<%@page import="com.setvect.nowhappy.board.web.BoardListPage"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loadPage = (String)request.getAttribute(WebAttributeKey.LOAD_PAGE);
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	boolean login = user != null;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="contentRoot" content="${pageContext.request.contextPath}/">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="${pageContext.request.contextPath}/css/bootstrap1.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootswatch.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-sanitize.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootswatch.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/editor/js/HuskyEZCreator.js"></script>
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
		};
	};
	
	// 전역 변수
	var mainCtrl= new pageActionContrller("${pageContext.request.contextPath}/");
	
	$(function(){
		mainCtrl.loadPage("<%=loadPage%>");
// 	mainCtrl.loadPage("/app/board_manager/page.do");
<%--mainCtrl.loadPage("/app/board/page.do?type=<%=BoardListPage.CONTENT%>&boardCode=BDAAAA01"); --%>
<%-- 		mainCtrl.loadPage("/app/board/page.do?type=<%=BoardListPage.MANAGE%>&boardCode=BDAAAA01"); --%>
		
		$("._boardManager").on("click", function(){
			mainCtrl.loadPage("/app/board_manager/page.do");
		}); 
	});
	
	var menuApp = angular.module('menuApp', []);
	menuApp.controller('menuController', function($scope, $http) {
		var listUrl = mainCtrl.getUrl("/app/board_manager/list.json.do");

		$scope.list = [];
		
		$scope.loadBoadMenu = function(){
		  var param = {};
		  param["pageNumber"] = 1;
		  param["pagePerItem"] = 100;
		  $http.get(listUrl, {params: param}).success(function(response) {
		  	$scope.list = response.list;
		  });
	  };
		
		/**
		 * 게시판 불러옴 
		 */
	  $scope.loadBoard = function(boardCode){
	  	mainCtrl.loadPage("/app/board/page.do?boardCode=" + boardCode);
	  };

	  $scope.loadBoardMg = function(boardCode){
	  	mainCtrl.loadPage("/app/board/page.do?type=<%=BoardListPage.MANAGE%>&boardCode=" + boardCode);
	  };
	  	  
	  $scope.loadHome = function(){
	  	mainCtrl.loadPage("/app/main.do");
	  };
	  
	  $scope.loadBoadMenu(1);
	});
</script>
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top" data-ng-app="menuApp"  data-ng-controller="menuController">
		<div class="container">
			<div class="navbar-header">
				<a href="#" class="navbar-brand" data-ng-click="loadHome()">Now Happy</a>
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse" id="navbar-main">
				<ul class="nav navbar-nav">
					<li><a href="#" data-ng-click="loadBoard('BDAAAA01')">글</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA09')">꿈</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA02')">책</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA03')">음악</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA04')">영화</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA05')">사진</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA08')">잡담</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA06')">기억</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA07')">인연</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA11')">소설</a></li>
					<li><a href="#" data-ng-click="loadBoard('BDAAAA10')">기술사</a></li>
<%
	if(login && user.isAdminF()){
%>					
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">Manager<span	class="caret"></span></a>
						<ul class="dropdown-menu" aria-labelledby="themes">
							<li><a href="#" class="_boardManager">게시판관리</a></li>
							<li class="divider"></li>
							<li data-ng-repeat="x in list">
								<a href="#" data-ng-click="loadBoardMg(x.boardCode)">{{x.name}}</a>
							</li>
						</ul>
					</li>
					<li><a href="<c:url value='/app/login/logout.do'/>">로그아웃</a></li>
<%
	}
%>
				</ul>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="page-header _mainAear">
		</div>
	</div>
	
</body>
</html>
