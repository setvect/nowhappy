<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Now Happy</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="<c:url value="/css/bootstrap1.css"/>" rel="stylesheet">
<link href="<c:url value="/css/bootswatch.min.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>
<script type="text/javascript">
	$(function(){
		var oEditors = [];		
		// 추가 글꼴 목록
		//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "ir1",
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
	});
</script>

</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a href="../" class="navbar-brand">Now Happy</a>
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse" id="navbar-main">
				<ul class="nav navbar-nav">
					<li><a href="#">글</a></li>
					<li><a href="#">꿈</a></li>
					<li><a href="#">꿈</a></li>
					<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" id="themes">Manager <span
							class="caret"></span></a>
						<ul class="dropdown-menu" aria-labelledby="themes">
							<li><a href="#">게시판관리</a></li>
							<li class="divider"></li>
							<li><a href="#">ㅁㅁ</a></li>
							<li><a href="#">ㅍㅍ</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container">

		<!-- Main... -->
		<div class="page-header">
			<h1>잠시왔다 가는 세상 즐겁게 살아야죠.</h1>
		</div>

		<div class="well bs-component">
			<div class="input-group">
				<textarea class="form-control" rows="1"></textarea>
				<span class="input-group-btn">
					<button class="btn btn-default" type="button">Go!</button>
				</span>
			</div>
			<!-- /input-group -->
		</div>
		<!-- /.row -->

		<div>
			<ul>
				<li>aaaaa <a href="#" class="btn btn-default btn-xs">삭제</a></li>
				<li>aaaaa</li>
				<li>aaaaa</li>
			</ul>
			<a href="#" class="btn btn-default  btn-lg btn-block btn-xs">더 불러오기</a>
		</div>




		<!-- login -->
		<form class="form-signin">
			<h2 class="form-signin-heading">Please sign in</h2>
			<input type="text" class="form-control" placeholder="ID" autofocus> <input type="password"
				class="form-control" placeholder="Password"> <label class="checkbox"> <input type="checkbox"
				value="remember-me"> Remember me
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>


		<!-- board -->
		<br />
		<br />
		<br />
		<div class="panel panel-default">
			<!-- Table -->
			<table class="table">
				<tbody>
					<tr>
						<td>몹시 아픈 후 드는 생각</td>
						<td class="date">2014-04-12</td>
					</tr>
					<tr>
						<td>몹시 아픈 후 드는 생각</td>
						<td class="date">2014-04-12</td>
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



		<br />
		<br />
		<br />
		<div class="panel panel-default">
			<div class="row">
				<div class="col-sm-4">
					<img src="http://www.setvect.com/servlet/Thumbnail?i=/upload/board/BDAAAA02/file2219upload.jpg&w=290&h=450" />
				</div>
				<div class="col-sm-4">
					<p>업으로 삼고 있는 분야에 기술이 변하는 모습도 잘 모른다. 쫓아 가기만해도 당행이라 생각한다. 데이터와 뷰의 분리, 양방향 바인딩. 그러고 보니 내 홈피도 오래된다. 올해 안으로 틈틈이 시간을
						내 바꿔야 겠다.</p>
					<span>(2015-03-05)</span>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4">
					<img src="http://www.setvect.com/servlet/Thumbnail?i=/upload/board/BDAAAA02/file2219upload.jpg&w=290&h=450" />
				</div>
				<div class="col-sm-4">
					<p>업으로 삼고 있는 분야에 기술이 변하는 모습도 잘 모른다. 쫓아 가기만해도 당행이라 생각한다. 데이터와 뷰의 분리, 양방향 바인딩. 그러고 보니 내 홈피도 오래된다. 올해 안으로 틈틈이 시간을
						내 바꿔야 겠다.</p>
					<span>(2015-03-05)</span>
				</div>
			</div>
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







		<div class="bs-component">
			<div class="jumbotron">
				<h5>제목....</h5>
				<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured
					content or information.</p>
				<span>2015-02-03</span>
			</div>
		</div>
		<a href="#" class="btn btn-default">목록</a> <br />
		<br />
		<!-- board 2-->


		<div class="panel panel-default">

			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<select class="form-control">
						<option>제목</option>
						<option>내용</option>
					</select> <input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">검색</button>
			</form>

			<!-- Table -->
			<table class="table">
				<thead>
					<tr>
						<th class="col-md-7">제목</th>
						<th class="col-md-1">조회</th>
						<th class="col-md-2 date">날짜</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col-md-7">몹시 아픈 후 드는 생각</td>
						<td class="col-md-1">11</td>
						<td class="col-md-2 date">2014-11-16 10:02</td>
					</tr>
					<tr>
						<td class="col-md-7">몹시 아픈 후 드는 생각</td>
						<td class="col-md-1">11</td>
						<td class="col-md-2 date">2014-11-16 10:02</td>
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


		<br />
		<br />
		<div class="well bs-component">
			<form class="form-horizontal">
				<fieldset>
					<legend>쓰기</legend>
					<div class="form-group">
						<label for="title" class="col-lg-2 control-label">제목</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="title">
						</div>
					</div>
					<div class="form-group">
						<label for="textArea" class="col-lg-2 control-label">내용</label>
						<div class="col-lg-10">
							<textarea class="form-control" rows="3" id="textArea" style="display: none;"></textarea>
							<textarea name="ir1" id="ir1" rows="10" cols="100" style="width: 100%; height: 412px; display: none;"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="encrypt" class="col-lg-2 control-label">암호코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="encrypt">
						</div>
					</div>
					<div class="form-group">
						<label for="encrypt" class="col-lg-2 control-label">첨부파일</label>
						<div class="col-lg-10">
							<input type="file" class="form-control"> <input type="file" class="form-control"> <input
								type="file" class="form-control">
							<ul>
								<li>aaa.jpg <input type="checkbox" />삭제
								</li>
								<li>bbb.jpg <input type="checkbox" />삭제
								</li>
							</ul>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" class="btn btn-default">쓰기</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>


		<!-- board manager-->


		<div class="panel panel-default">

			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<select class="form-control">
						<option>코드</option>
						<option>이름</option>
					</select> <input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">검색</button>
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
					<tr>
						<td>15</td>
						<td>BDAAAA00</td>
						<td>메인화면</td>
						<td><a href="#" class="btn btn-default btn-xs">수정</a></td>
						<td><a href="#" class="btn btn-default btn-xs">삭제</a></td>
					</tr>
					<tr>
						<td>15</td>
						<td>BDAAAA00</td>
						<td>메인화면</td>
						<td><a href="#" class="btn btn-default btn-xs">수정</a></td>
						<td><a href="#" class="btn btn-default btn-xs">삭제</a></td>
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



		<br />
		<br />
		<div class="well bs-component">
			<form class="form-horizontal">
				<fieldset>
					<legend>게시판 등록</legend>
					<div class="form-group">
						<label for="title" class="col-lg-2 control-label">코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="code">
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="col-lg-2 control-label">이름</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="name">
						</div>
					</div>
					<div class="form-group">
						<label for="limit" class="col-lg-2 control-label">업로드 용량 제한</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="limit">
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-2 control-label">답글 사용</label>
						<div class="col-lg-10">
							<label> <input type="radio" name="" value="option2"> 예
							</label> <label> <input type="radio" name="" value="option2"> 아니오
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-2 control-label">짧은 리플 사용</label>
						<div class="col-lg-10">
							<label> <input type="radio" name="" value="option2"> 예
							</label> <label> <input type="radio" name="" value="option2"> 아니오
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-2 control-label">파일 업로드</label>
						<div class="col-lg-10">
							<label> <input type="radio" name="" value="option2"> 예
							</label> <label> <input type="radio" name="" value="option2"> 아니오
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">암호화글 등록</label>
						<div class="col-lg-10">
							<label> <input type="radio" name="" value="option2"> 예
							</label> <label> <input type="radio" name="" value="option2"> 아니오
							</label>
						</div>
					</div>



					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" class="btn btn-default">등록</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>

		<div class="bs-component">
			<div class="jumbotron">
				<h5>코드</h5>
				<p>BDAABB02</p>
				<h5>이름</h5>
				<p>비공개 글</p>
				<h5>업로드 용량 제한</h5>
				<p>0</p>
				<h5>답글 사용</h5>
				<p>false</p>
				<h5>짧은 리플 사용</h5>
				<p>false</p>
				<h5>파일 업로드</h5>
				<p>false</p>
				<h5>암호화글 등록</h5>
				<p>false</p>
			</div>
		</div>
		<a href="#" class="btn btn-default">수정</a> <a href="#" class="btn btn-default">삭제</a> <a href="#"
			class="btn btn-default">목록</a>







	</div>


	<br />
	<br />
	<br />
</body>
</html>
