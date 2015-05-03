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
	app.controller('commentList', function($scope, $http) {
		var moduleName = "<%=module%>";
		var listUrl = mainCtrl.getUrl("/app/comment/list.json");
		var deleteUrl = mainCtrl.getUrl("/app/comment/delete.do");
		
		var param = {};
		param["moduleName"] = moduleName;
	  $http.get(listUrl, {params: param}).success(function(response) {
	  	$scope.loginId = "<%=userId%>";
		  $scope.item = response.list;
		  $scope.remove = function(index){
			  var deleteItem = $scope.item[index];
			  console.log(deleteItem);
			  $scope.item.splice (index, 1);
		  };
	  });			
	});	
	
	var injector = angular.injector(['ng', 'commentApp'])
	injector.invoke(function($rootScope, $compile, $document) {
	  $compile($document)($rootScope);
	  $rootScope.$digest();
	});		
</script>

<div data-ng-app="commentApp" >
<%
	if(login){
%>
	<div class="well bs-component">
		<div class="input-group">
			<textarea class="form-control" rows="1"></textarea>
			<span class="input-group-btn">
				<button class="btn btn-default" type="button">등록</button>
			</span>
		</div>
		<!-- /input-group -->
	</div>
<%
	}
%>
	<div>
		<ul data-ng-controller="commentList">
			<li data-ng-repeat="x in item">
				{{x.content}} 
				{{x.regDateDiff}}
				{{x.userId}}
				<a href="#" data-ng-click="remove($index)" data-ng-if="x.userId == loginId" class="btn btn-default btn-xs">삭제</a>
			</li>
		</ul>
		<a href="#" class="btn btn-default  btn-lg btn-block btn-xs">더 불러오기</a>
	</div>
</div>
ddddddddd