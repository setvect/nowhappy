<%@page import="com.setvect.nowhappy.code.service.CodeConstant"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
%>
<script type="text/javascript">
	var appcodeManager = angular.module('codeManagerApp', []);
	appcodeManager.controller('codeManagerController', function($scope, $http) {
		var listUrl = $.APP.getContextRoot("/app/code/list.json.do");
		var addUrl = $.APP.getContextRoot("/app/code/add.do");
		var updateUrl = $.APP.getContextRoot("/app/code/update.do");
		var deleteUrl = $.APP.getContextRoot("/app/code/delete.do");
		var changeOrderUrl = $.APP.getContextRoot("/app/code/changeOrder.do");
		var postHeaders = {headers: {'Content-Type': 'application/x-www-form-urlencoded;'}};
		
		$scope.rootCode = "<%=CodeConstant.ROOT%>";
		$scope.majorCode = $scope.rootCode;
		$scope.view = "list";
		$scope.codeList = [];
		$scope.readItem = null;
		
		$scope.listView = function(){
		  var param = {};
		  param["majorCode"] = $scope.majorCode;
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.codeList = response;
			  $scope.view = "list";
		  });
	  };
	  
	  $scope.listback = function(){
	  	$scope.view = "list";	  	
	  };
	  
	  $scope.write = function(){
	  	$scope.readItem = {};
	  	$scope.readItem["majorCode"] = $scope.majorCode;
	  	$scope.readItem["orderNo"] = $scope.codeList.length + 1;
	  	$scope.view = "write";
	  }

	  $scope.update = function(code){
	  	// deep copy
	  	$scope.readItem = angular.copy(code);
	  	$scope.view = "update";
	  };
	  
	  $scope.writeOrUpdateSummit = function(){
	  	var url = $scope.view == "write" ? addUrl : updateUrl; 

  		$http.post(url, $.param($scope.readItem), postHeaders).success(function(response) {
		  	if(response){
		  		$scope.listView();
		  	}
		  });
	  };
	  
	  $scope.remove = function(code){
	  	console.log(code);
	  	if(confirm("삭제할거야?")){
	  		$http.get(deleteUrl, {params: {codeSeq: code.codeSeq}}).success(function(response) {
			  	if(response){
			  		$scope.listView();
			  	}
			  });
	  	}
	  };
	  
	  // 정렬 순서 수정
	  $scope.orderSet = function(){
	  	var dataObject = {codeList : JSON.stringify($scope.codeList)};
  		$http.post(changeOrderUrl, $.param(dataObject), postHeaders).success(function(response) {
		  	if(response){
		  		$scope.listView();
		  	}
		  });
	  };

	  $scope.listView();
	});	
	
	var injector = angular.injector(['ng', 'codeManagerApp'])
	injector.invoke(function($rootScope, $compile, $document) {
		var appNode = $(".codeManager")[0];
	  $compile(appNode)($rootScope);
	  $rootScope.$digest();
	});		
</script>
<div class="codeManager" data-ng-app="codeManagerApp"  data-ng-controller="codeManagerController">
	<!-- 목록 폼 -->
	<div data-ng-show="view =='list'">
		<div class="panel panel-default">
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
					<tr data-ng-repeat="x in codeList">
						<td>{{$index + 1}}</td>
						<td>{{x.majorCode}}</td>
						<td>{{x.minorCode}}</td>
						<td>{{x.codeValue}}</td>
						<td>
							<input type="number" data-ng-model="x.orderNo" style="color: black;">
						</td>
						<td><a href="#" data-ng-click="update(codeList[$index])" class="btn btn-default btn-xs">수정</a></td>
						<td><a href="#" data-ng-click="remove(codeList[$index])" class="btn btn-default btn-xs">삭제</a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<a href="#" class="btn btn-default" data-ng-click="write()">만들기</a>
		<a href="#" class="btn btn-default" data-ng-click="orderSet()">정렬값 수정</a>  
	</div>

	<!-- 등록 폼 -->
	<div data-ng-show="view =='write' || view =='update' ">
		<div class="well bs-component">
			<form class="form-horizontal" name="writeForm">
				<fieldset>
					<legend>게시판 등록</legend>
					<div class="form-group">
						<label class="col-lg-2 control-label">상위분류</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.majorCode" maxlength="10" readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드</label>
						<div class="col-lg-10">
							<input type="text" name="minorCode" class="form-control" data-ng-model="readItem.minorCode" maxlength="10" required="required" >
							<span data-ng-show="writeForm.minorCode.$dirty && writeForm.minorCode.$invalid">코드 넣어라</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드값</label>
						<div class="col-lg-10">
							<input type="text" name="codeValue" class="form-control" data-ng-model="readItem.codeValue" required="required" >
							<span data-ng-show="writeForm.codeValue.$dirty && writeForm.codeValue.$invalid">코드값 넣어라</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">순서</label>
						<div class="col-lg-10">
							<input type="number" name="orderNo" class="form-control" data-ng-model="readItem.orderNo" required="required" >
							<span data-ng-show="writeForm.orderNo.$dirty && writeForm.orderNo.$invalid">순서는 숫자로 넣어라</span>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<button type="button" class="btn btn-default" data-ng-click="listback()">취소</button>
		<button type="button" class="btn btn-default" data-ng-click="writeOrUpdateSummit()">완료</button>
	</div>
</div>
