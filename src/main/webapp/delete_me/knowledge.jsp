<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<link href="<c:url value="/css/metisMenu.css"/>" rel="stylesheet">
<link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet">

<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>





<script type="text/javascript">
	$(function(){
		var oEditors = [];		

		makeTexteditor("problemText");
		makeTexteditor("solutionText");

		function makeTexteditor(textareaId){
			nhn.husky.EZCreator.createInIFrame({
				oAppRef : oEditors,
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
		
	});
</script>



</head>
<body>
	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">SB Admin v2.0</a>
			</div>
		</nav>
		<div>
			<div class="container-fluid">
				<div class="row">
				
				
					<div class="col-lg-12">
						<div class="panel panel-default">
							<form class="navbar-form navbar-left" role="search">
								<div class="form-group">
									<select class="form-control">
										<option>자바</option>
										<option>OS및 설치</option>
									</select> 

									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">검색</button>
							</form>
				
							<!-- Table -->
							<table class="table">
								<thead>
									<tr>
										<th class="col-md-4">문제</th>
										<th class="col-md-3">해결</th>
										<th class="col-md-1 center">분류</th>
										<th class="col-md-1 center">조회</th>
										<th class="col-md-1 center">날짜</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="col-md-4">몹시 아픈 후 드는 생각</td>
										<td class="col-md-3">몹시 아픈 후 드는 생각</td>
										<td class="col-md-1 center">자바</td>
										<td class="col-md-1 center">11</td>
										<td class="col-md-1 center">2014-11-16</td>
									</tr>
									<tr>
										<td class="col-md-4">몹시 아픈 후 드는 생각</td>
										<td class="col-md-3">몹시 아픈 후 드는 생각</td>
										<td class="col-md-1 center">OS</td>
										<td class="col-md-1 center">11</td>
										<td class="col-md-1 center">2014-11-16</td>
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
					
					
					<div class="col-lg-12">
						<div>
							<a href="#" class="btn btn-primary">수정</a>
							<a href="#" class="btn btn-warning">삭제</a>
							<a href="#" class="btn btn-default">목록</a>					
						</div>
						<br/>
						<div class="panel panel-default">
							<div class="panel-body">
								<h4>문제</h4>
								<div>ㅁㅁㅁㅁㅁㅁㄴㄹㅇㄴㅁㄻㄹ<br/>asdfsadfdsaf</div>
							</div>
							<div class="panel-body">
								<h4>해결</h4>
								<div>ㅁㅁㅁㅁㅁㅁㄴㄹㅇㄴㅁㄻㄹ<br/>asdfsadfdsaf</div>
							</div>
							<div class="panel-body">2016-07-04</div>
						</div> 
						<div>
							<a href="#" class="btn btn-primary">수정</a>
							<a href="#" class="btn btn-warning">삭제</a>
							<a href="#" class="btn btn-default">목록</a>					
						</div>
					</div>
					        
					<br/><br/><br/>

					<div class="col-lg-12" style="padding-top: 30px;">
						<div class="well bs-component">
							<form class="form-horizontal ng-pristine ng-valid ng-valid-required" name="writeForm">
								<fieldset>
									<legend>쓰기</legend>
									<div class="form-group"> 
										<label for="title" class="col-lg-1 control-label">분야</label>
										<div class="col-lg-11">
											<select class="form-control">
												<option>자바</option>
												<option>OS및 설치</option>
											</select> 
										</div>
									</div>									
									<div class="form-group">
										<label for="title" class="col-lg-1 control-label">문제</label>
										<div class="col-lg-11">
											<textarea id="problemText" rows="10" cols="100" style="width: 100%; height: 200px; display: none;"></textarea>
										</div>
									</div>									
									<div class="form-group">
										<label for="title" class="col-lg-1 control-label">해결</label>
										<div class="col-lg-11">
											<textarea id="solutionText" rows="10" cols="100" style="width: 100%; height: 200px; display: none;"></textarea>
										</div>
									</div>										
									<div class="form-group">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
											<button type="submit" class="btn btn-primary" data-ng-click="writeOrUpdateNoteSummit()" data-ng-disabled="writeForm.title.$invalid">쓰기</button>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>
