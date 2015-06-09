<%@page import="com.setvect.nowhappy.attach.service.AttachFileModule"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	
%>
<script type="text/javascript">
	var appBoardManager = angular.module('boardApp', ['ngSanitize']);
	
	// 첨부파일을 업로드 하기위해... 나도 이해 못함. ㅡㅡ;
	appBoardManager.directive('fileModel', [ '$parse', function($parse) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;

				element.bind('change', function() {
					scope.$apply(function() {
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	} ]);
	
	appBoardManager.controller('boardController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
    $scope.trustAsHtml = $sce.trustAsHtml;
     
		$scope.view = "list";
		$scope.auth = {};
		 
		$scope.list = [];
		$scope.readItem = null;
		$scope.pageNumber = 1;
		$scope.pageCount = 0;
		$scope.pageItem = [];
		$scope.attachList = [];
		
		$scope.boardCode = "<%=request.getParameter("boardCode")%>";
		$scope.boardInfo;
		
		var listUrl = mainCtrl.getUrl("/app/board/list.json");
		var addUrl = mainCtrl.getUrl("/app/board/add.do");
		var updateUrl = mainCtrl.getUrl("/app/board/update.do");
		var deleteUrl = mainCtrl.getUrl("/app/board/delete.do");
		var loadAuthUrl = mainCtrl.getUrl("/app/board/loadAuth.json");
		var readBoardManager = mainCtrl.getUrl("/app/board_manager/read.json");
  	
		var listAttachFileUrl = mainCtrl.getUrl("/app/attachFile/list.json");
		
		var oEditors = [];
  	
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
			$scope.loadAttachFile(article);
	  	$scope.view = "read";
	  };
	  
	  $scope.loadAttachFile = function(article){
	  	var param = {};
  		param["moduleName"] = "<%=AttachFileModule.BOARD%>";
  		param["moduleId"] = article.articleSeq;
	  	
	  	$http.get(listAttachFileUrl, {params: param}).success(function(response) {
	  		$scope.attachList = response;
	  	});	  
	  };
	  
	  $scope.write = function(){
	  	$scope.readItem = {};
	  	$scope.view = "write";
	  	oEditors.getById["content"].setContents("");
	  }
	  
	  $scope.update = function(article){
	  	// deep copy
	  	$scope.readItem = angular.copy(article);
	  	$scope.attachList = [];
	  	$scope.loadAttachFile(article);
	  	$scope.view = "update";
			oEditors.getById["content"].setContents([article.content]);
	  };

	  $scope.writeOrUpdateSummit = function(){
	  	var url = $scope.view == "write" ? addUrl : updateUrl; 
	  	var content = oEditors.getById["content"].getIR();
	  	
	  	if(removeTags(content.trim()) == ""){
	  		alert("내용을 입력해 주세요");
	  		return;
	  	}
	  	// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
	  	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	  	var fd = new FormData();

	  	fd.append("articleSeq", $scope.readItem.articleSeq);
	  	fd.append("boardCode", $scope.boardCode);
	  	fd.append("title", $scope.readItem.title);
	  	fd.append("content", content.trim());
	  	
			if($scope.readItem.attachFile != null){	  	
		  	$.each($scope.readItem.attachFile, function(index, value) {
			  	fd.append("attachFile", value);
		  	}); 	  	
			}
	  	
  		$http.post(url, fd, {transformRequest: angular.identity, headers: {'Content-Type': undefined}}).success(function(response) {
		  	if(response){
		  		$scope.page($scope.pageNumber);
		  	}
		  });
	  };
	  
	  $scope.loadAuth = function(article){
	  	var param = {};
	  	if(article != null){
	  		param["articleSeq"] = article.articleSeq;
	  		param["boardCode"] = $scope.boardCode;
	  	}
	  	
	  	$http.get(loadAuthUrl, {params: param}).success(function(response) {
		  	$scope.auth.write = response.write;
		  	$scope.auth.edit = response.edit;
	  	});
	  };
	  
	  // 게시판 설정정보 load
	  $scope.loadBoard = function(){
	  	var param = {};
	  	param["boardCode"] = $scope.boardCode;
	  	
	  	$http.get(readBoardManager, {params: param}).success(function(response) {
	  		$scope.boardInfo = response;
	  	});
	  };
	  
	  $scope.htmlText = function(){
	  	nhn.husky.EZCreator.createInIFrame({
	  		oAppRef: oEditors,
	  		elPlaceHolder: "content",
				sSkinURI : mainCtrl.getUrl("/editor/SmartEditor2Skin.html"),
	  		fCreator: "createSEditorInIFrame"
	  	});
	  };
	  
	  $scope.loadBoard();
	  $scope.loadAuth();
	  $scope.page(1);
  	$scope.htmlText();
	}]);	
	
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
		<a href="#" class="btn btn-default" data-ng-click="write()" data-ng-show="auth.write">글쓰기</a>
	</div>
	
	<!-- 읽기 폼 -->
	<div data-ng-show="view =='read'">
		<div class="bs-component">
			<div class="jumbotron">
				<h5>{{readItem.title}}</h5>
				<p data-ng-bind-html="trustAsHtml(readItem.content)"></p>
				<span>{{readItem.regDate | date:'yyyy.MM.dd'}}</span>
				<ul>
					<li data-ng-repeat="f in attachList track by $index">
						첨부파일{{$index + 1}}: 
						<a href="<%=request.getContextPath()%>/download.do?s={{f.savePathEncode}}&amp;d={{f.originalNameEncode}}">{{f.originalName}} ({{(f.size / 1024.0) | number:0}}k)</a>
					</li>
				</ul>

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
					<legend>쓰기</legend>
					<div class="form-group">
						<label for="title" class="col-lg-2 control-label">제목</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="title" data-ng-model="readItem.title" required>
						</div>
					</div>
					<div class="form-group">
						<label for="textArea" class="col-lg-2 control-label">내용</label>
						<div class="col-lg-10">
							<textarea id="content" rows="10" cols="100" style="width: 100%; height: 300px; display: none;" data-ng-model="readItem.content"></textarea>
						</div>
					</div>
					<div class="form-group" data-ng-show="boardInfo.encodeF">
						<label for="encrypt" class="col-lg-2 control-label">암호코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" name="encrypt" id="encrypt" data-ng-model="readItem.encrypt">
						</div>
					</div>
					<div class="form-group" data-ng-show="boardInfo.attachF">
						<label for="encrypt" class="col-lg-2 control-label">첨부파일</label>
						<div class="col-lg-10">
							<input type="file" class="form-control" file-model="readItem.attachFile[0]"> 
							<input type="file" class="form-control" file-model="readItem.attachFile[1]">
							<input type="file" class="form-control" file-model="readItem.attachFile[2]">
							<ul>
								<li data-ng-repeat="f in attachList track by $index">
									{{f.originalName}}  <input type="checkbox" value="{{f.attachFileSeq}}"/>삭제
								</li>
							</ul>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
							<button type="submit" class="btn btn-default" data-ng-click="writeOrUpdateSummit()">쓰기</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>	
	
	
	
	
</div>