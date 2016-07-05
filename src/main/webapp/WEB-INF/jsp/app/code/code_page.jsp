<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
%>
<script type="text/javascript">
	var appBoardManager = angular.module('boardMangerApp', []);
	appBoardManager.controller('boardManagerController', function($scope, $http) {
		var listUrl = $.APP.getContextRoot("/app/board_manager/list.json.do");
		var addUrl = $.APP.getContextRoot("/app/code/add.do");
		var updateUrl = $.APP.getContextRoot("/app/code/update.do");
		var deleteUrl = $.APP.getContextRoot("/app/code/delete.do");
		
		$scope.view = "list";
		$scope.list = [];
		$scope.readItem = null;
		$scope.pageNumber = 1;
		$scope.pageCount = 0;
		$scope.pageItem = [];
		
		$scope.searchOption = "code";
		$scope.searchWord = "";

		$scope.page = function(pageNumber){
		  var param = {};
		  $scope.pageNumber = pageNumber;
		  param["pageNumber"] = $scope.pageNumber;
		  param["searchOption"] = $scope.searchOption;
		  param["searchWord"] = $scope.searchWord;
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = response.list;
			  $scope.view = "list";
			  $scope.pageCount = response.pageCount;
			  $scope.pageItem = [];
			  for(var i= 0; i< $scope.pageCount; i++){
				  $scope.pageItem.push(i + 1);
			  }
		  });
	  };
	  
	  $scope.search = function(){
	  	$scope.page(1);
	  };
	  
	  $scope.searchCancel = function(){
	  	$scope.searchOption = "code";
			$scope.searchWord = "";
			$scope.page(1);
	  };
	  
	  $scope.listback = function(){
	  	$scope.view = "list";	  	
	  };
	  
	  $scope.read = function(board){
	  	$scope.readItem = angular.copy(board);
	  	$scope.view = "read";
	  };
	  
	  $scope.write = function(){
	  	$scope.readItem = {};
	  	$scope.readItem["replyF"] = false;
	  	$scope.readItem["commentF"] = false;
	  	$scope.readItem["attachF"] = true;
	  	$scope.readItem["encodeF"] = false;
	  	$scope.view = "write";
	  }

	  $scope.update = function(board){
	  	// deep copy
	  	$scope.readItem = angular.copy(board);
	  	$scope.view = "update";
	  };
	  
	  $scope.writeOrUpdateSummit = function(){
	  	var url = $scope.view == "write" ? addUrl : updateUrl; 

  		$http.get(url, {params: $scope.readItem}).success(function(response) {
		  	if(response){
		  		$scope.page($scope.pageNumber);
		  	}
		  });
	  };
	  
	  $scope.remove = function(board){
	  	if(confirm("삭제할거야?")){
	  		$http.get(deleteUrl, {params: {boardCode: board.boardCode}}).success(function(response) {
			  	if(response){
			  		$scope.page($scope.pageNumber);
			  	}
			  });
	  	}
	  };

	  $scope.page(1);
	});	
	
	var injector = angular.injector(['ng', 'boardMangerApp'])
	injector.invoke(function($rootScope, $compile, $document) {
		var appNode = $(".boardManger")[0];
	  $compile(appNode)($rootScope);
	  $rootScope.$digest();
	});		
</script>
<div class="codeManger" data-ng-app="codeMangerApp"  data-ng-controller="codeManagerController">
	<!-- 목록 폼 -->
	<div data-ng-show="view =='list'">
		<div class="panel panel-default">
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<select class="form-control" data-ng-model="searchOption">
						<option value="code">코드</option>
						<option value="name">이름</option>
					</select> <input type="text" data-ng-model="searchWord" class="form-control">
				</div>
				<button type="submit" class="btn btn-default" data-ng-click="search();">검색</button>
				<button type="submit" class="btn btn-default" data-ng-click="searchCancel();" data-ng-show="searchWord != ''">검색 취소</button>
			</form>
	
			<!-- Table -->
			<table class="table">
				<thead>
					<tr>
						<th>No.</th>
						<th>상위분류</th>
						<th>코드</th>
						<th>코드값</th>
						<th>정렬</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="x in list">
						<td>{{$index}}</td>
						<td>{{x.majorCode}}</td>
						<td>{{x.minorCode}}</td>
						<td>{{x.codeValue}}</td>
						<td>{{x.orderNo}}</td>
						<td><a href="#" data-ng-click="update(list[$index])" class="btn btn-default btn-xs">수정</a></td>
						<td><a href="#" data-ng-click="remove(list[$index])" class="btn btn-default btn-xs">삭제</a></td>
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
		<a href="#" class="btn btn-default" data-ng-click="write()">만들기</a> 
	</div>

	<!-- 등록 폼 -->
	<div data-ng-show="view =='write' || view =='update' ">
		<div class="well bs-component">
			<form class="form-horizontal">
				<fieldset>
					<legend>게시판 등록</legend>
					<div class="form-group">
						<label class="col-lg-2 control-label">상위분류</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.majorCode" maxlength="10">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.minorCode" maxlength="10" required >
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드값</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.codeValue">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">순서</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.orderNo">
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<button type="button" class="btn btn-default" data-ng-click="listback()">취소</button>
		<button type="button" class="btn btn-default" data-ng-click="writeOrUpdateSummit()">완료</button>
	</div>
</div>
