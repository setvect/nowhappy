<%@page import="com.setvect.nowhappy.board.web.BoardListPage"%>
<%@page import="com.setvect.nowhappy.attach.service.AttachFileModule"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	BoardListPage listPgae = (BoardListPage)request.getAttribute(WebAttributeKey.BOARD_LIST_TYPE);
%>
<script type="text/javascript">
	var appBoard = angular.module('boardApp', ['ngSanitize', 'ngRoute']);
	
	var HTML_EDITOR;
	
	// 첨부파일을 업로드 하기위해... 나도 이해 못함. ㅡㅡ;
	appBoard.directive('fileModel', [ '$parse', function($parse) {
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
	
	appBoard.config(function($routeProvider) {
		$routeProvider.when('/list', {
			templateUrl : mainCtrl.getUrl("/app/board/list.do?type=<%=listPgae%>"),
			controller : 'boardListController' 
		}).when('/write', {
			templateUrl : mainCtrl.getUrl("/app/board/write.do"),
			controller : 'boardWriteController'
		}).when('/update/:articleSeq', {
			templateUrl : mainCtrl.getUrl("/app/board/write.do"),
			controller : 'boardWriteController' 
		}).when('/read/:articleSeq', {
			templateUrl : mainCtrl.getUrl("/app/board/read.do"),
			controller : 'boardReadController' 
		}).when('/encode/:articleSeq', {
			templateUrl : mainCtrl.getUrl("/app/board/encode.do"),
			controller : 'boardEncodeController' 
		}).otherwise({
			redirectTo : '/list'
		});
	});
	
	appBoard.controller('boardController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
	
		// 한 페이지 이동 네비게이션 네비게이선 상에 묶음 
		var BULDEL_OF_PAGE = 10;
		
		$scope.trustAsHtml = $sce.trustAsHtml;
		$scope.auth = {};
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
		
		$scope.boardCode = "<%=request.getParameter("boardCode")%>";
		$scope.boardInfo;
		
		$scope.encode = {};
		$scope.encode.encodeString= "";
		
		$scope.user = <%=user != null ? user.isAdminF() : false%>;
		
		$scope.searchParam = {};
		$scope.searchParam.option = "title";
		$scope.searchParam.word = "";
		
	  $scope.listback = function(){
	  	location.href="#/list";  	
	  };

	  $scope.loadAttachFile = function(article){
			var listAttachFileUrl = mainCtrl.getUrl("/app/attachFile/list.json.do");
	  	var param = {};
  		param["moduleName"] = "<%=AttachFileModule.BOARD%>";
  		param["moduleId"] = article.articleSeq;
	  	$http.get(listAttachFileUrl, {params: param}).success(function(response) {
	  		$scope.attachMapList[article.articleSeq] = response;
	  	});	  
	  };

	  $scope.writeOrUpdateSummit = function(){
			var addUrl = mainCtrl.getUrl("/app/board/add.do");
			var updateUrl = mainCtrl.getUrl("/app/board/update.do");
			
	  	var url = $scope.readItem.articleSeq == 0 ? addUrl : updateUrl; 
	  	var content = $scope.oEditors.getById["content"].getIR();
	  	
	  	if(removeTags(content.trim()) == ""){
	  		alert("내용을 입력해 주세요");
	  		return;
	  	}
	  	// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
	  	$scope.oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	  	var fd = new FormData();
	  	
	  	fd.append("articleSeq", $scope.readItem.articleSeq);
	  	fd.append("boardCode", $scope.boardCode);
	  	fd.append("title", $scope.readItem.title);
	  	fd.append("content", content.trim());
	  	fd.append("encrypt", $("#encrypt").val());
	  	
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
  		$http.post(url, fd, headers).success(function(response) {
		  	if(response){
		  		location.href="#/list";
		  	}
		  });			
	  };
	  
		$scope.page = function(pageNumber){
		  var param = {};
		  $scope.pageNumber = pageNumber;
		  param["pageNumber"] = $scope.pageNumber;
		  param["boardCode"] = $scope.boardCode;
		  param["searchOption"] = $scope.searchParam.option;
		  param["searchWord"] = $scope.searchParam.word;
		  
			var listUrl = mainCtrl.getUrl("/app/board/list.json.do");
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
			 	location.href="#";
		  });
	  };
	  
	  $scope.remove = function(article){
			var deleteUrl = mainCtrl.getUrl("/app/board/delete.do");
	  	if(!confirm("삭제할거야?")){
	  		return;
	  	}
	  	
	  	var param = {};
  		param["articleSeq"] = article.articleSeq;
	  	
	  	$http.get(deleteUrl, {params: param}).success(function(response) {
	  		location.href="#/list";  	
	  	});	  	
	  };
	  
	  $scope.loadAuth = function(article){
	  	var param = {};
	  	if(article != null){
	  		param["articleSeq"] = article.articleSeq;
	  		param["boardCode"] = $scope.boardCode;
	  	}
	  	
			var loadAuthUrl = mainCtrl.getUrl("/app/board/loadAuth.json.do");
	  	$http.get(loadAuthUrl, {params: param}).success(function(response) {
		  	$scope.auth.write = response.write;
		  	$scope.auth.edit = response.edit;
	  	});
	  };
	  
	  // 게시판 설정정보 load
	  $scope.loadBoard = function(){
			var readBoardManager = mainCtrl.getUrl("/app/board_manager/read.json.do");
	  	
	  	var param = {};
	  	param["boardCode"] = $scope.boardCode;
	  	
	  	$http.get(readBoardManager, {params: param}).success(function(response) {
	  		$scope.boardInfo = response;
	  	});
	  };
	  
	  $scope.loadArticle = function(articleSeq){
			var readArticle = mainCtrl.getUrl("/app/board/read.json.do");
	  	var param = {};
	  	param["articleSeq"] = articleSeq;
	  	param["encodeString"] = $scope.encode.encodeString;
		  $http.get(readArticle, {params: param}).success(function(response) {
		  	$scope.readItem = response;
				$scope.loadAttachFile($scope.readItem);
				$scope.resizeImg();
		  });
	  };
	  
	  $scope.initReadItem = function(){
	  	$scope.readItem = {};
			// Controller에서 VO Bind를 하기 위해.
			$scope.readItem.articleSeq = 0;
			$scope.attachMapList = {};
	  };
	  
	  $scope.imgPopup = function(imgPath){
	  	var url = mainCtrl.getUrl(imgPath);
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
	      $("._board_content img").each(function() {
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
	  
	  $scope.loadBoard();
	  $scope.loadAuth();
	}]);
	
	appBoard.controller('boardListController', ['$scope', '$http', function($scope, $http) {
	  $scope.search = function(){
	  	$scope.page(1);
	  };
	  
	  $scope.searchCancel = function(){
	  	$scope.searchParam.option = "title";
			$scope.searchParam.word = "";
			$scope.page(1);
	  };

		$scope.page($scope.pageNumber);
	}]);	

	appBoard.controller('boardWriteController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	  if($routeParams.articleSeq != null){
	  	$scope.loadArticle($routeParams.articleSeq);
	  }
	  else{
			$scope.initReadItem();
	  }
	  
	  $scope.htmlText = function(){
	  	nhn.husky.EZCreator.createInIFrame({
	  		oAppRef: $scope.oEditors,
	  		elPlaceHolder: "content",
				sSkinURI : mainCtrl.getUrl("/editor/SmartEditor2Skin.html"),
	  		fCreator: "createSEditorInIFrame"
	  	});
	  	
	  	HTML_EDITOR = $scope.oEditors;
	  	
	  };
	  $scope.htmlText();
	}]);	

	appBoard.controller('boardReadController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.loadArticle($routeParams.articleSeq);
	}]);	
	
	appBoard.controller('boardEncodeController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.encodeRead = function(){
			$scope.encode.encodeString = $("#encode").val();
			location.href="#/read/" + $routeParams.articleSeq;
		};
	}]);	
	angular.element(document).ready(function(){
		angular.bootstrap($(".boardNode")[0], ['boardApp'])
	});
</script>
<div class="boardNode _board_content" data-ng-app="boardApp" data-ng-controller="boardController">
	<ng-view></ng-view>
</div>
<div class="_img_popup" title="이미지 보기">
  <a href="javascript:"><img src=""/></a>
</div>
