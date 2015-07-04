<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	boolean login = user != null;
	String userId = login ? user.getUserId() : "";
	CommentModule module = (CommentModule)request.getAttribute(CommentController.ATTR_MODULE_NAME); 
%>
<script type="text/javascript">
	var app = angular.module('commentApp', []);
	app.controller('commentController', function($scope, $http) {
		var moduleName = "<%=module%>";
		var getUrl = mainCtrl.getUrl("/app/comment/get.json.do");
		var listUrl = mainCtrl.getUrl("/app/comment/list.json.do");
		var deleteUrl = mainCtrl.getUrl("/app/comment/delete.do");
		var addUrl = mainCtrl.getUrl("/app/comment/add.do");
		
	  $scope.loginId = "<%=userId%>";
	  $scope.list = [];
	  $scope.more = function(){
		  var param = {};
		  param["moduleName"] = moduleName;
		  param["startCursor"] = $scope.list.length;
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = $scope.list.concat(response.list);
			  $scope.totalCount = response.totalCount;
			  $scope.currentItem = $scope.list.length;
		  });
	  };

	  $scope.remove = function(index){
		  var deleteItem = $scope.list[index];
		  $http.get(deleteUrl, {params: {commentSeq: deleteItem.commentSeq}}).success(function(response) {
		  	if(response){
		  		$scope.list.splice (index, 1);
		  	}
		  });
	  };
	  
	  $scope.add = function(){
	  	if($scope.content == null || $scope.content.trim() == ""){
	  		alert("내용을 써라");
	  		return; 
	  	}
	  	
	  	var param = {};
	  	param["moduleName"] = moduleName;
	  	param["content"] =  $scope.content.trim();
	  	
		  $http.get(addUrl, {params: param}).success(function(response) {
		  	if(response == -1){
		  		return;
		  	}
		  	
		  	$http.get(getUrl, {params: {commentSeq : response}}).success(function(response) {
		  		$scope.list.unshift(response);
		  	});
		  });	  	
	  };
	  $scope.more();
	});	
	
	var injector = angular.injector(['ng', 'commentApp'])
	injector.invoke(function($rootScope, $compile, $document) {
		var appNode = $(".commentNode")[0];
	  $compile(appNode)($rootScope);
	  $rootScope.$digest();
	});		
</script>

<div class="commentNode" data-ng-app="commentApp"  data-ng-controller="commentController">
	<div class="well bs-component" data-ng-if="loginId != ''">
		<div class="input-group">
			<textarea class="form-control" rows="1" data-ng-model="$parent.content"></textarea>
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" data-ng-click="add()">등록</button>
			</span>
		</div>
	</div>
	<div>
		<ul>
			<li data-ng-repeat="x in list" style="padding: 5px 0;">
				{{x.content}}  
				<span class="label label-default">{{x.regDateDiff}}</span>
				<a href="#" data-ng-click="remove($index)" data-ng-if="x.userId == loginId" class="btn btn-default btn-xs">삭제</a>
			</li>
		</ul>
		<a href="javascript:" data-ng-click="more()"  class="btn btn-default  btn-lg btn-block btn-xs">더 불러오기({{currentItem}}/{{totalCount}})</a>
	</div>
</div>
