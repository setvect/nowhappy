<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
%>
<script type="text/javascript">
	var app = angular.module('boardMangerApp', []);
	app.controller('boardManagerController', function($scope, $http) {
		var listUrl = mainCtrl.getUrl("/app/board_manager/list.json");
	  $scope.list = [];
	  $scope.page = function(pageNumber){
		  var param = {};
		  param["pageNumber"] = pageNumber;
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = $scope.list.concat(response.list);
		  });
	  };
	  $scope.page(1);
	});	
	
	var injector = angular.injector(['ng', 'boardMangerApp'])
	injector.invoke(function($rootScope, $compile, $document) {
	  $compile($document)($rootScope);
	  $rootScope.$digest();
	});		
</script>
<div data-ng-app="boardMangerApp"  data-ng-controller="boardManagerController">
	<div class="panel panel-default">
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<select class="form-control">
					<option>코드</option>
					<option>이름</option>
				</select> <input type="text" class="form-control" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">검색</button>
		</form>

		<!-- Table -->
		<table class="table">
			<thead>
				<tr>
					<th>No.</th>
					<th>코드</th>
					<th>이름</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<tr data-ng-repeat="x in list">
					<td>{{$index}}</td>
					<td>{{x.boardCode}}</td>
					<td>{{x.name}}</td>
					<td><a href="#" class="btn btn-default btn-xs">수정</a></td>
					<td><a href="#" class="btn btn-default btn-xs">삭제</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="text-center">
		<ul class="pagination pagination-sm">
			<li><a href="#">«</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">»</a></li>
		</ul>
	</div>
</div>
