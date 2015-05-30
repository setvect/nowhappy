<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	
%>
<script type="text/javascript">
	var appBoardManager = angular.module('boardApp', []);
	appBoardManager.controller('boardController', function($scope, $http) {
		$scope.view = "list";
		$scope.list = [];
		$scope.readItem = null;
		$scope.pageNumber = 1;
		$scope.pageCount = 0;
		$scope.pageItem = [];
		
		$scope.boardCode = "<%=request.getParameter("boardCode")%>"; 
		
		var listUrl = mainCtrl.getUrl("/app/board/list.json");
		var addUrl = mainCtrl.getUrl("/app/board/add.do");
		var updateUrl = mainCtrl.getUrl("/app/board/update.do");
		var deleteUrl = mainCtrl.getUrl("/app/board/delete.do");
		
		$scope.page = function(pageNumber){
		  var param = {};
		  $scope.pageNumber = pageNumber;
		  param["pageNumber"] = $scope.pageNumber;
		  param["boardCode"] = $scope.boardCode; 
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = response.list;
			  $scope.view = "list";
			  $scope.pageCount = response.pageCount;
			  $scope.pageItem = [];
			  console.log(response);
			  
			  for(var i= 0; i< $scope.pageCount; i++){
				  $scope.pageItem.push(i + 1);
			  }
		  });
	  };
	  
	  $scope.listback = function(){
	  	$scope.view = "list";	  	
	  };

	  $scope.read = function(article){
	  	$scope.readItem = angular.copy(article);
	  	$scope.view = "read";
	  };	  
		
	  $scope.page(1);
	});	
	
	var injector = angular.injector(['ng', 'boardApp'])
	injector.invoke(function($rootScope, $compile, $document) {
		var appNode = $(".boardNode")[0];
	  $compile(appNode)($rootScope);
	  $rootScope.$digest();
	});		
</script>
<div class="boardNode" data-ng-app="boardApp"  data-ng-controller="boardController">
	<!-- 목록 폼 -->
	<div data-ng-show="view =='list'">
		<div class="panel panel-default">
			<!-- Table -->
			<table class="table">
				<tbody>
					<tr data-ng-repeat="x in list">
						<td><a href="#" data-ng-click="read(list[$index])">{{x.title}}</a></td>
						<td class="date">{{x.regDate | date:'yyyy.MM.dd'}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="text-center">
			<ul class="pagination pagination-sm">
				<li data-ng-repeat="n in pageItem track by $index">
					<a href="#" style="background-color: {{pageNumber == n ? 'olive':''}}" data-ng-click="page(n)">{{n}}</a>
				</li>
			</ul>
		</div>
	</div>
	
	<!-- 읽기 폼 -->
	<div data-ng-show="view =='read'">
		<div class="bs-component">
			<div class="jumbotron">
				<h5>{{readItem.title}}</h5>
				<p>{{readItem.content}}</p>
				<span>{{readItem.regDate | date:'yyyy.MM.dd'}}</span>
			</div>
		</div>
		<a href="#" data-ng-click="update(readItem)" class="btn btn-default">수정</a> 
		<a href="#" data-ng-click="remove(readItem)" class="btn btn-default">삭제</a> 
		<a href="#" data-ng-click="listback()"class="btn btn-default">목록</a>
	</div>
</div>