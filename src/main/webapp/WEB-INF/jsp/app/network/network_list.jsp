<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8">
	<title>Now Happy - 복슬관계</title>
	<meta name="contentRoot" content="<c:url value="/"/>"> <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet">
	<link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet">
	<link href="<c:url value="/lib/vis/vis.css"/>" rel="stylesheet">
	<link href="<c:url value="/lib/bootstrap-colorpicker-2.5.2/css/bootstrap-colorpicker.css"/>" rel="stylesheet">
	<style type="text/css">
		body,
		html {
			height: 100%;
		}

		#mynetwork {
			width: 100%;
			height: 100%;
		}
	</style>
	<script type="text/javascript" src="<c:url value='/js/jquery-1.11.2.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery-ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootswatch.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/util.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/vis/vis.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/bootstrap-colorpicker-2.5.2/js/bootstrap-colorpicker.js'/>"></script>
</head>

<body>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				 aria-controls="navbar">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">복슬관계</a>
			</div>

		</div>
	</nav>
	<div class="container" style="margin-top:70px">
		<div class="row">
			<div class="col-md-3">
				<div class="thumbnail">
					<a target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>zzzz</p>
						</div>
					</a>
				</div>
			</div>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="/w3images/nature.jpg" target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>ㅎㅎㅎㅎ</p>
						</div>
					</a>
				</div>
			</div>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="/w3images/fjords.jpg" target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>ㅎㅎㅎㅎ</p>
						</div>
					</a>
				</div>
			</div>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="/w3images/fjords.jpg" target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>ㅎㅎㅎㅎ</p>
						</div>
					</a>
				</div>
			</div>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="/w3images/fjords.jpg" target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>ㅎㅎㅎㅎ</p>
						</div>
					</a>
				</div>
			</div>
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="/w3images/fjords.jpg" target="_blank">
						<div style="width:100%;height: 100px;">&nbsp;</div>
						<div class="caption">
							<p>ㅎㅎㅎㅎ</p>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
</body>

</html>