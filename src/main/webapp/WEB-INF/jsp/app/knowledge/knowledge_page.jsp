<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy - 복슬지식</title>
<meta name="contentRoot" content="<c:url value="/"/>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<link href="<c:url value="/css/metisMenu.css"/>" rel="stylesheet">
<link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet">
<link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet">

<style>
	.drop-active{color:red;}
</style>

<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-sanitize.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js"></script>

<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/sb-admin-2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/metisMenu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/util.js"/>"></script>

<script type="text/javascript">
	var appknowledge = angular.module('knowledgeApp', ['ngSanitize', 'ngRoute']);
	
	var HTML_EDITOR;
	
	appknowledge.config(function($routeProvider) {
		$routeProvider.when('/list', {
			templateUrl : $.APP.getContextRoot("app/knowledge/list.do"),
			controller : 'knowledgeListController' 
		}).when('/write', {
			templateUrl : $.APP.getContextRoot("app/knowledge/write.do"),
			controller : 'knowledgeWriteController'
		}).when('/update/:knowledgeSeq', {
			templateUrl : $.APP.getContextRoot("app/knowledge/write.do"),
			controller : 'knowledgeWriteController' 
		}).when('/read/:knowledgeSeq', {
			templateUrl : $.APP.getContextRoot("app/knowledge/read.do"),
			controller : 'knowledgeReadController'
		}).otherwise({
			redirectTo : '/list'
		});
	});
	
	appknowledge.controller('knowledgeController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
		// 한 페이지 이동 네비게이션 네비게이선 상에 묶음 
		var BULDEL_OF_PAGE = 10;
		
		$scope.trustAsHtml = $sce.trustAsHtml;
		$scope.list = [];
		$scope.readItem = null;
		$scope.pageNumber = 1;
		$scope.pageCount = 0;
		// 묶음단위 이전 이후
		$scope.pagePreviousGroup = -1;
		$scope.pageNextGroup = -1;
		
		$scope.pageItem = [];
		$scope.readItem = {};
		$scope.oEditors = [];
		
		$scope.searchParam = {};
		$scope.searchParam.classifyC = "";
		$scope.searchParam.word = "";

		$scope.listback = function(){
	  	location.href="#/list";   	
	  };

	  $scope.writeOrUpdateKnowledgeSummit = function(){
	  	var problem = $scope.oEditors.getById["problem"].getIR();
	  	var solution = $scope.oEditors.getById["solution"].getIR();
	  	
	  	if(removeTags(problem.trim()) == ""){
	  		alert("문제 내용을 입력해 주세요");
	  		return;
	  	}
	  	
	  	// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
	  	$scope.oEditors.getById["problem"].exec("UPDATE_CONTENTS_FIELD", []);
	  	$scope.oEditors.getById["solution"].exec("UPDATE_CONTENTS_FIELD", []);

		  //TODO multipart 사용 안함.
	  	var fd = new FormData();
	  	fd.append("knowledgeSeq", $scope.readItem.knowledgeSeq);
	  	fd.append("problem", content.trim());
			var headers = {headers: {'Content-Type': undefined}};
	  	
			var addUrl = $.APP.getContextRoot("app/knowledge/addKnowledge.do");
			var updateUrl = $.APP.getContextRoot("app/knowledge/updateKnowledge.do");
			var url = $scope.readItem.knowledgeSeq == 0 ? addUrl : updateUrl;
			
  		$http.post(url, fd, headers).success(function(response) {
		  	if(response){
		  		location.href="#/list";
		  	}
		  });			
	  };
	  
	  // 페이지. 1부터 시작
		$scope.page = function(pageNumber){
		  var param = {};
		  $scope.pageNumber = pageNumber;
		  param["pageNumber"] = $scope.pageNumber;
		  param["searchClassifyC"] = $scope.searchParam.classifyC;
		  param["searchWord"] = $scope.searchParam.word;
		  
			var listUrl = $.APP.getContextRoot("app/knowledge/listKnowledge.json.do");
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = response.list;
			  $scope.pageCount = response.pageCount;
			  $scope.pageItem = [];

			 	var pageStart = (Math.ceil($scope.pageNumber / BULDEL_OF_PAGE) -1) * BULDEL_OF_PAGE ;
			 	$scope.pagePreviousGroup = pageStart - 1 > 0 ? pageStart : -1; 
			 	$scope.pageNextGroup = pageStart + BULDEL_OF_PAGE <  $scope.pageCount ? pageStart + BULDEL_OF_PAGE + 1 : -1;
			 	
			  for(var i= pageStart; i < $scope.pageCount && i < pageStart + BULDEL_OF_PAGE; i++){
				  $scope.pageItem.push(i + 1);
			  }
			  $scope.resizeImg();
		  });
	  };
	  
	  $scope.remove = function(knowledge){
			var deleteUrl = $.APP.getContextRoot("app/knowledge/deleteKnowledge.do");
	  	if(!confirm("삭제할거야?")){
	  		return;
	  	}
	  	
	  	var param = {};
  		param["knowledgeSeq"] = knowledge.knowledgeSeq;
	  	
	  	$http.get(deleteUrl, {params: param}).success(function(response) {
	  		location.href="#/list";  	
	  	});	  	
	  };
	  
	  $scope.loadKnowledge = function(knowledgeSeq){
			var readKnowledge = $.APP.getContextRoot("app/knowledge/readKnowledge.json.do");
	  	var param = {};
	  	param["knowledgeSeq"] = knowledgeSeq;
		  $http.get(readKnowledge, {params: param}).success(function(response) {
		  	$scope.readItem = response;
		  });
	  };
	}]);
	
	appKnowledge.controller('knowledgeListController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	  $scope.search = function(){
	  	$scope.page(1);
	  };
	  
	  $scope.searchCancel = function(){
	  	$scope.searchParam.classifyC = "";
			$scope.searchParam.word = "";
			$scope.page(1);
	  };
	  
	  $scope.page(1);
	}]);	

	appKnowledge.controller('knowledgeWriteController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		var INTERVAL_TIME = 3;
	  if($routeParams.knowledgeSeq != null){
	  	$scope.loadKnowledge($routeParams.knowledgeSeq);
	  }
	  
	  $scope.htmlText = function(){
	  	nhn.husky.EZCreator.createInIFrame({
	  		oAppRef: $scope.oEditors,
	  		elPlaceHolder: "content",
				sSkinURI : $.APP.getContextRoot("editor/SmartEditor2Skin.html"),
	  		fCreator: "createSEditorInIFrame"
	  	});

	  	HTML_EDITOR = $scope.oEditors;
	  };

	  $scope.htmlText();
	}]);	

	appKnowledge.controller('knowledgeReadController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.loadKnowledge($routeParams.knowledgeSeq);
	}]);	
</script>

</head>
<body data-ng-app="knowledgeApp" data-ng-controller="knowledgeController">
	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value="/knowledge/page.do#/list"/>">복슬지식</a>
			</div>
		</nav>
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					
					<div class="col-lg-12">	
						<ng-view></ng-view>
					</div>
					<!-- /.col-lg-12 -->
					
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->
	</div>
</body>
</html>
