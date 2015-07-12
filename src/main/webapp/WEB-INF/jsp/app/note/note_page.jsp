<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.attach.service.AttachFileModule"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy - 복슬노트</title>
<meta name="contentRoot" content="<c:url value="/"/>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<link href="<c:url value="/css/metisMenu.css"/>" rel="stylesheet">
<link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet">

<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-sanitize.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js"></script>

<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/sb-admin-2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/metisMenu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/util.js"/>"></script>

<script type="text/javascript">
	var appNote = angular.module('noteApp', ['ngSanitize', 'ngRoute']);
	
	var HTML_EDITOR;
	
	// 첨부파일을 업로드 하기위해... 나도 이해 못함. ㅡㅡ;
	appNote.directive('fileModel', [ '$parse', function($parse) {
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
	
	appNote.config(function($routeProvider) {
		$routeProvider.when('/list', {
			templateUrl : $.APP.getContextRoot("app/note/list.do"),
			controller : 'noteListController' 
		}).when('/list/:categorySeq', {
			templateUrl : $.APP.getContextRoot("app/note/list.do"),
			controller : 'noteListController' 
		}).when('/write/:categorySeq', {
			templateUrl : $.APP.getContextRoot("app/note/write.do"),
			controller : 'noteWriteController'
		}).when('/update/:noteSeq', {
			templateUrl : $.APP.getContextRoot("app/note/write.do"),
			controller : 'noteWriteController' 
		}).when('/read/:noteSeq', {
			templateUrl : $.APP.getContextRoot("app/note/read.do"),
			controller : 'noteReadController'
		}).otherwise({
			redirectTo : '/list'
		});
	});
	
	appNote.controller('noteController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
	
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
		$scope.attachMapList = {};
		$scope.readItem = {};
		$scope.oEditors = [];
		
		$scope.categoryName = "";
		$scope.categoryList=[];
		
		$scope.searchParam = {};
		$scope.searchParam.option = "title";
		$scope.searchParam.word = "";
		$scope.searchParam.currentCategory = {};
		
		$scope.addCategory = function(){
			if($.STR.isEmpty($scope.categoryName)){
				alert("입력해.");
				return;
			}
			
			var url = $.APP.getContextRoot("app/note/addCategory.do");
			var data = {"name": $scope.categoryName};
  		$http.get(url, {params:data}).success(function(response) {
		  	if(response){
		  		$scope.categoryName = "";
		  		$scope.loadCategory();
		  	}
		  });
		};
		
		$scope.updateCategory = function(){
			if($.STR.isEmpty($scope.searchParam.currentCategory.name)){
				alert("입력해.");
				return;
			}

			var url = $.APP.getContextRoot("app/note/updateCategory.do");
			$http.get(url, {params:$scope.searchParam.currentCategory}).success(function(response) {
		  	if(response){
		  		$scope.loadCategory();
		  	}
		  });
		};
		
		$scope.deleteCategory = function(){
			if(confirm("지울거야?")){
				var url = $.APP.getContextRoot("app/note/deleteCategory.do");
				$http.get(url, {params:$scope.searchParam.currentCategory}).success(function(response) {
			  	if(response){
			  		$scope.loadCategory();
			  		$scope.searchParam.currentCategory = null;
			  		$scope.page(1);
			  	}
			  });
			}
		};
		
		// 카테고리 
		$scope.loadCategory = function(){
			var listCategoryUrl = $.APP.getContextRoot("app/note/listCategory.json.do");
	  	$http.get(listCategoryUrl).success(function(response) {
	  		$scope.categoryList = response.list;
	  	});	  
		};
		
		// 카테고리 정보
		$scope.getCategory = function(categorySeq){
			for(var i=0 ;i < $scope.categoryList.length; i++){
				var value = $scope.categoryList[i];
	  		if(value.categorySeq == categorySeq){
	  			return value;
	  		}
			}
  		return null;
		};
		
	  $scope.listback = function(){
	  	location.href="#/list";  	
	  };

	  $scope.loadAttachFile = function(note){
			var listAttachFileUrl = $.APP.getContextRoot("app/attachFile/list.json.do");
	  	var param = {};
  		param["moduleName"] = "<%=AttachFileModule.NOTE%>";
  		param["moduleId"] = note.noteSeq;
	  	$http.get(listAttachFileUrl, {params: param}).success(function(response) {
	  		$scope.attachMapList[note.noteSeq] = response;
	  	});	  
	  };

	  $scope.writeOrUpdateNoteSummit = function(){
	  	var content = $scope.oEditors.getById["content"].getIR();
	  	
	  	if(removeTags(content.trim()) == ""){
	  		alert("내용을 입력해 주세요");
	  		return;
	  	}
	  	// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
	  	$scope.oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	  	var fd = new FormData();
	  	
	  	fd.append("noteSeq", $scope.readItem.noteSeq);
	  	
	  	var categorySeq;
	  	if($scope.readItem.noteSeq == 0){
	  		//신규 등록
	  		categorySeq = $scope.searchParam.currentCategory.categorySeq;
	  	}
	  	else{
	  		categorySeq = $scope.readItem.categorySeq;
	  	}
	  	
	  	fd.append("categorySeq", categorySeq);
	  	fd.append("title", $scope.readItem.title);
	  	
	  	fd.append("content", content.trim());
	  	
			if($scope.readItem.attachFile != null){
		  	$.each($scope.readItem.attachFile, function(index, value) {
			  	fd.append("attachFile", value);
		  	}); 	  	
			}

			$("input[name='deleteattachFileSeq']").each(function(idx, node){
				if($(node).is(":checked")){
					fd.append("deleteattachFileSeq", $(node).val());
				}
			});
			var headers = {headers: {'Content-Type': undefined}};
	  	
			var addUrl = $.APP.getContextRoot("app/note/addNote.do");
			var updateUrl = $.APP.getContextRoot("app/note/updateNote.do");
			var url = $scope.readItem.noteSeq == 0 ? addUrl : updateUrl; 
  		$http.post(url, fd, headers).success(function(response) {
		  	if(response){
		  		location.href="#/list/" + categorySeq;
		  	}
		  });			
	  };
	  
		$scope.page = function(pageNumber){
		  var param = {};
		  $scope.pageNumber = pageNumber;
		  param["pageNumber"] = $scope.pageNumber;
		  if($scope.searchParam.currentCategory != null){
		  	param["categorySeq"] = $scope.searchParam.currentCategory.categorySeq;
		  }
		  param["searchOption"] = $scope.searchParam.option;
		  param["searchWord"] = $scope.searchParam.word;
		  
			var listUrl = $.APP.getContextRoot("app/note/listNote.json.do");
		  $http.get(listUrl, {params: param}).success(function(response) {
			  $scope.list = response.list;
			  $scope.pageCount = response.pageCount;
			  $scope.pageItem = [];

			  for(var i=0; i < $scope.list.length; i++){
			  	$scope.loadAttachFile($scope.list[i]);
			  }
			  
			 	var pageStart = (Math.ceil($scope.pageNumber / BULDEL_OF_PAGE) -1) * BULDEL_OF_PAGE ;
			 	$scope.pagePreviousGroup = pageStart - 1 > 0 ? pageStart : -1; 
			 	$scope.pageNextGroup = pageStart + BULDEL_OF_PAGE <  $scope.pageCount ? pageStart + BULDEL_OF_PAGE + 1 : -1;
			 	
			  for(var i= pageStart; i < $scope.pageCount && i < pageStart + BULDEL_OF_PAGE; i++){
				  $scope.pageItem.push(i + 1);
			  }
			  $scope.resizeImg();
		  });
	  };
	  
	  $scope.remove = function(note){
			var deleteUrl = $.APP.getContextRoot("app/note/deleteNote.do");
	  	if(!confirm("삭제할거야?")){
	  		return;
	  	}
	  	
	  	var param = {};
  		param["noteSeq"] = note.noteSeq;
	  	
	  	$http.get(deleteUrl, {params: param}).success(function(response) {
	  		location.href="#/list";  	
	  	});	  	
	  };
	  
	  $scope.loadNote = function(noteSeq){
			var readNote = $.APP.getContextRoot("app/note/readNote.json.do");
	  	var param = {};
	  	param["noteSeq"] = noteSeq;
		  $http.get(readNote, {params: param}).success(function(response) {
		  	$scope.readItem = response;
				$scope.loadAttachFile($scope.readItem);
				$scope.resizeImg();
		  });
	  };
	  
	  $scope.initReadItem = function(){
	  	$scope.readItem = {};
			// Controller에서 VO Bind를 하기 위해.
			$scope.readItem.noteSeq = 0;
			$scope.attachMapList = {};
	  };
	  
	  $scope.imgPopup = function(imgPath){
	  	var url = $.APP.getContextRoot(imgPath);
	  	$("._img_popup a").on("click", function(){
	  		$("._img_popup").dialog("close");
	  	});
	  	
	  	$("._img_popup img").attr("src", url);
  	 	$("._img_popup").dialog({
        resizable: false,
        modal: true,
        width:'auto'
			});
	  };
	  
	  // 본문에 큰 이미지가 있으면 줄임.
	  $scope.resizeImg = function(){
	  	setTimeout(function(){
	      $("._note_content img").each(function() {
	        var oImgWidth = $(this).width();
	        var oImgHeight = $(this).height();
	        
	        $(this).css({
	            'max-width':oImgWidth+'px',
	            'max-height':oImgHeight+'px',
	            'width':'100%',
	            'height':'100%'
	        });
	    	});
	  	}, 500);
	  };
	  $scope.loadCategory();
	}]);
	
	appNote.controller('noteListController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	  $scope.search = function(){
	  	$scope.page(1);
	  };
	  
	  $scope.searchCancel = function(){
	  	$scope.searchParam.option = "title";
			$scope.searchParam.word = "";
			$scope.page(1);
	  };
	  
	  $scope.searchParam.currentCategory = $scope.getCategory($routeParams.categorySeq);
	  $scope.page(1);
	}]);	

	appNote.controller('noteWriteController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	  if($routeParams.noteSeq != null){
	  	$scope.loadNote($routeParams.noteSeq);
	  }
	  else{
			$scope.initReadItem();
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
	  $scope.searchParam.currentCategory = $scope.getCategory($routeParams.categorySeq);
	}]);	

	appNote.controller('noteReadController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.loadNote($routeParams.noteSeq);
	}]);	
	
</script>

</head>
<body data-ng-app="noteApp" data-ng-controller="noteController">
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value="/note/page.do#/list"/>">복슬노트</a>
			</div>
			<!-- /.navbar-top-links -->
			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						
						<li class="sidebar-search">
							<div class="input-group custom-search-form">
								<input type="text" class="form-control" data-ng-model="categoryName">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" data-ng-click="addCategory()" >
										등록
									</button>
								</span>
							</div> 
						</li>
						
						<li class="sidebar-search" data-ng-show="searchParam.currentCategory">
							<div class="input-group custom-search-form">
								<input type="text" class="form-control" name="" data-ng-model="searchParam.currentCategory.name">
								<span class="input-group-btn">
									<input type="button" class="btn btn-default" value="수정" data-ng-click="updateCategory()" >
									<input type="button" class="btn btn-default" value="삭제" data-ng-click="deleteCategory()" >
								</span>
							</div>
						</li>

            <li data-ng-repeat="item in categoryList">
							<a href="#/list/{{item.categorySeq}}"><i class="fa fa-edit fa-fw"></i> {{item.name}}</a>
            </li>

					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" style="padding:20px 0">
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
