<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy - 복슬지식</title>
<meta name="contentRoot" content="<c:url value="/"/>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<link href="<c:url value="/css/metisMenu.css"/>" rel="stylesheet">
<link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet">
<link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet">

<style>
	.drop-active{color:red;}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-sanitize.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js"></script>

<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/sb-admin-2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/metisMenu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/util.js"/>"></script>

<script type="text/javascript">

	angular.module('core', []);

	angular.module('core').filter('clearHtml', function() {
		return function(html) {
			var tmp = document.createElement("DIV");
			tmp.innerHTML = html;
			return tmp.textContent || tmp.innerText || "";
		};
	});
	var appKnowledge = angular.module('knowledgeApp', ['core', 'ngSanitize', 'ngRoute']);
	
// 	appKnowledge.directive('classifyDirective', function() {
// 		return function(scope, element, attrs) {
// 			// 마지막 바인드가 되면 이벤드 발생 
// 			if (scope.$last) {
// 				scope.searchParam.classifyC = "JAVASCRIPT";
// 			}
// 		};
// 	});
	
	
	
	var HTML_EDITOR;
	
	appKnowledge.config(function($routeProvider) {
		$routeProvider.when('/list', {
			templateUrl : $.APP.getContextRoot("app/knowledge/list.do"),
			controller : 'knowledgeListController' 
		}).when('/write', {
			templateUrl : $.APP.getContextRoot("app/knowledge/write.do"),
			controller : 'knowledgeWriteController'
		}).when('/update/:knowledgeSeq', {
			templateUrl : $.APP.getContextRoot("app/knowledge/write.do"),
			controller : 'knowledgeWriteController' 
		}).when('/read/:knowledgeSeq', {
			templateUrl : $.APP.getContextRoot("app/knowledge/read.do"),
			controller : 'knowledgeReadController'
		}).otherwise({
			redirectTo : '/list'
		});
	});
	
	appKnowledge.controller('knowledgeController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
		// 한 페이지 이동 네비게이션 네비게이선 상에 묶음 
		var BULDEL_OF_PAGE = 10;
		
		$scope.trustAsHtml = $sce.trustAsHtml;
		$scope.list = [];
		$scope.category = [];
		$scope.readItem = null;
		$scope.pageNumber = 1;
		$scope.pageCount = 0;
		// 묶음단위 이전 이후
		$scope.pagePreviousGroup = -1;
		$scope.pageNextGroup = -1;
		
		$scope.pageItem = [];
		$scope.readItem = {};
		$scope.oEditors = [];

		$scope.searchParam = {};
		$scope.searchParam.classifyC = "";
		$scope.searchParam.word = "";

		$scope.listback = function(){
			location.href="#/list";
		};

		$scope.writeOrUpdateKnowledgeSummit = function(){
			var problem = $scope.oEditors.getById["problemText"].getIR();
			var solution = $scope.oEditors.getById["solutionText"].getIR();
			if($scope.readItem.classifyC == null){
				alert("분야를 선택하세요.");
				$("select[name='classifyC']").focus();
				return;
			}
			
			if(removeTags(problem.trim()) == ""){
				alert("문제 내용을 입력해 주세요");
				return;
			}
			
			// 에디터의 내용을 에디터 생성시에 사용했던 textarea에 넣어 줍니다.
			$scope.oEditors.getById["problemText"].exec("UPDATE_CONTENTS_FIELD", []);
			$scope.oEditors.getById["solutionText"].exec("UPDATE_CONTENTS_FIELD", []);

			//TODO multipart 사용 안함.
			var fd = new FormData();
			fd.append("knowledgeSeq", $scope.readItem.knowledgeSeq);
			fd.append("problem", problem.trim());
			fd.append("solution", solution.trim());
			fd.append("classifyC", $scope.readItem.classifyC);

			var headers = {headers: {'Content-Type': undefined}};

			var addUrl = $.APP.getContextRoot("app/knowledge/addKnowledge.do");
			var updateUrl = $.APP.getContextRoot("app/knowledge/updateKnowledge.do");
			var url = $scope.readItem.knowledgeSeq == 0 ? addUrl : updateUrl;
			
			$http.post(url, fd, headers).success(function(response) {
				if(response){
					location.href="#/list";
				}
			});
		};

		// 페이지. 1부터 시작
		$scope.page = function(pageNumber){
			var param = {};
			$scope.pageNumber = pageNumber;
			
			param["pageNumber"] = $scope.pageNumber;
			param["searchClassifyC"] = $scope.searchParam.classifyC;
			param["searchWord"] = $scope.searchParam.word;
			
			var listUrl = $.APP.getContextRoot("app/knowledge/listKnowledge.json.do");
			$http.get(listUrl, {params: param}).success(function(response) {
				$scope.list = response.list;
				$scope.pageCount = response.pageCount;
				$scope.pageItem = [];

				var pageStart = (Math.ceil($scope.pageNumber / BULDEL_OF_PAGE) -1) * BULDEL_OF_PAGE ;
				$scope.pagePreviousGroup = pageStart - 1 > 0 ? pageStart : -1; 
				$scope.pageNextGroup = pageStart + BULDEL_OF_PAGE <	$scope.pageCount ? pageStart + BULDEL_OF_PAGE + 1 : -1;

				for(var i= pageStart; i < $scope.pageCount && i < pageStart + BULDEL_OF_PAGE; i++){
					$scope.pageItem.push(i + 1);
				}
			});
		};

		$scope.loadCategoryCode = function(){
			var categoryUrl = $.APP.getContextRoot("app/knowledge/listCategory.json.do");
			$http.get(categoryUrl).success(function(response) {
				$scope.category = response;
			});
		};

		$scope.remove = function(knowledge){
			var deleteUrl = $.APP.getContextRoot("app/knowledge/deleteKnowledge.do");
			if(!confirm("삭제할거야?")){
				return;
			}

			var param = {};
			param["knowledgeSeq"] = knowledge.knowledgeSeq;

			$http.get(deleteUrl, {params: param}).success(function(response) {
				location.href="#/list";
			});
		};

		$scope.loadKnowledge = function(knowledgeSeq, callBack){
			var readKnowledge = $.APP.getContextRoot("app/knowledge/readKnowledge.json.do");
			var param = {};
			param["knowledgeSeq"] = knowledgeSeq;
			$http.get(readKnowledge, {params: param}).success(function(response) {
				$scope.readItem = response;
				if(callBack != null){
					callBack();
				}
			});
		};

		// 본문에 큰 이미지가 있으면 줄임.
		$scope.resizeImg = function(){
			setTimeout(function(){
				$("._knowledge_content img").each(function() {
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

		$scope.initReadItem = function(){
			$scope.readItem = {};
			// Controller에서 VO Bind를 하기 위해.
			$scope.readItem.knowledgeSeq = 0;
		};
		
		$scope.loadCategoryCode();
	}]);
	
	// 목록
	appKnowledge.controller('knowledgeListController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.search = function(){
			$scope.page(1);
		};
	
		$scope.searchCancel = function(){
			$scope.searchParam.classifyC = "";
			$scope.searchParam.word = "";
			$scope.page(1);
		};
		$scope.page($scope.pageNumber);
	}]);

	appKnowledge.controller('knowledgeWriteController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		if($routeParams.knowledgeSeq != null){
			$scope.loadKnowledge($routeParams.knowledgeSeq, function(){
				// 이전 값으로 selected 선택. jquery 안 쓰고 angularjs로만 사용하는 방법이 있을 것 같은데...
				$("form[name='writeForm'] select[name='classifyC']").val($scope.readItem.classifyC)
			});
		}
		else{
			$scope.initReadItem();
		}

		$scope.htmlText = function(textareaId){
			nhn.husky.EZCreator.createInIFrame({
				oAppRef : $scope.oEditors,
				elPlaceHolder : textareaId,
				sSkinURI : "<c:url value='/editor/SmartEditor2Skin.html'/>",
				htParams : {
					bUseToolbar : true, // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true, // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
					fOnBeforeUnload : function() {
						//alert("완료!");
					}
				}, //boolean
				fOnAppLoad : function() {
					//예제 코드
					//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
				},
				fCreator : "createSEditor2"
			});
		}
		
		$scope.htmlText("problemText");
		$scope.htmlText("solutionText");
	}]);	

	appKnowledge.controller('knowledgeReadController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
		$scope.loadKnowledge($routeParams.knowledgeSeq, function(){
			$scope.resizeImg();
		});
	}]);
</script>

</head>
<body data-ng-app="knowledgeApp" data-ng-controller="knowledgeController">
	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value="/knowledge/page.do#/list"/>">복슬지식</a>
			</div>
		</nav>
		<!-- Page Content -->
		<div>
			<div class="container-fluid">
				<div class="row">
					
					<div class="col-lg-12">	
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
