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
		var listUrl = mainCtrl.getUrl("/app/board_manager/list.json");
		var addUrl = mainCtrl.getUrl("/app/board_manager/add.do");
		var updateUrl = mainCtrl.getUrl("/app/board_manager/update.do");
		var deleteUrl = mainCtrl.getUrl("/app/board_manager/delete.do");
		
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
<div class="boardManger" data-ng-app="boardMangerApp"  data-ng-controller="boardManagerController">
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
						<th>코드</th>
						<th>이름</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="x in list">
						<td>{{$index}}</td>
						<td><a href="#" data-ng-click="read(list[$index])">{{x.boardCode}}</a></td>
						<td>{{x.name}}</td>
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
	
	<!-- 읽기 폼 -->
	<div data-ng-show="view =='read'">
		<div class="bs-component">
			<div class="jumbotron">
				<h5>코드</h5>
				<p>{{readItem.boardCode}}</p>
				<h5>이름</h5>
				<p>{{readItem.name}}</p>
				<h5>업로드 용량 제한</h5>
				<p>{{readItem.uploadLimit}}</p> 
				<h5>답글 사용</h5>
				<p>{{readItem.replyF}}</p>
				<h5>짧은 리플 사용</h5>
				<p>{{readItem.commentF}}</p>
				<h5>파일 업로드</h5>
				<p>{{readItem.attachF}}</p>
				<h5>암호화글 등록</h5>
				<p>{{readItem.encodeF}}</p>
			</div>
		</div>
		<a href="#" data-ng-click="update(readItem)" class="btn btn-default">수정</a> 
		<a href="#" data-ng-click="remove(readItem)" class="btn btn-default">삭제</a> 
		<a href="#" data-ng-click="listback()"class="btn btn-default">목록</a>
	</div>

	<!-- 등록 폼 -->
	<div data-ng-show="view =='write' || view =='update' ">
		<div class="well bs-component">
			<form class="form-horizontal">
				<fieldset>
					<legend>게시판 등록</legend>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.boardCode" data-ng-show="view =='write'" maxlength="10">
							<span data-ng-show="view =='update'">{{readItem.boardCode}}</span>
						</div>
					</div>
	
					<div class="form-group">
						<label class="col-lg-2 control-label">이름</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.name" maxlength="20" required >
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">업로드 용량 제한</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.uploadLimit">Kbyte
						</div>
					</div>
	
					<div class="form-group">
						<label class="col-lg-2 control-label">답글 사용</label>
						<div class="col-lg-10">
							<label> <input type="radio" value="true" data-ng-model="readItem.replyF" data-ng-value="true"> 예 </label> 
							<label> <input type="radio" value="false" data-ng-model="readItem.replyF" data-ng-value="false"> 아니오</label>
						</div>
					</div>
	
					<div class="form-group">
						<label class="col-lg-2 control-label">짧은 리플 사용</label>
						<div class="col-lg-10">
							<label> <input type="radio" value="true" data-ng-model="readItem.commentF" data-ng-value="true"> 예 </label> 
							<label> <input type="radio" value="false" data-ng-model="readItem.commentF" data-ng-value="false"> 아니오</label>
						</div>
					</div>
	
					<div class="form-group">
						<label class="col-lg-2 control-label">파일 업로드</label>
						<div class="col-lg-10">
							<label> <input type="radio" value="true" data-ng-model="readItem.attachF" data-ng-value="true"> 예	</label> 
							<label> <input type="radio" value="false" data-ng-model="readItem.attachF" data-ng-value="false"> 아니오	</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">암호화글 등록</label>
						<div class="col-lg-10">
							<label> <input type="radio" value="true" data-ng-model="readItem.encodeF" data-ng-value="true"> 예</label> 
							<label> <input type="radio" value="false" data-ng-model="readItem.encodeF" data-ng-value="false"> 아니오</label>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<button type="button" class="btn btn-default" data-ng-click="listback()">취소</button>
		<button type="button" class="btn btn-default" data-ng-click="writeOrUpdateSummit()">완료</button>
	</div>


</div>
