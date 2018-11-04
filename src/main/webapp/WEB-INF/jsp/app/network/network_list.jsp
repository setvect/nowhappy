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
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="/network/page.do" class="_add">등록</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container" style="padding-top:70px">
		<div class="row _list">
			<!-- list -->
		</div>
	</div>

	<script>
		$(function () {
			$.get($.APP.getContextRoot("/network/list.json"), function (data) {
				$.map(data.list, function (v, idx) {
					$("._list").html("<div class='col-md-3'><div class='thumbnail'><a href='javascript:void(0)' target='_blank' class='_network_item' data-seq='" + v.networkSeq + "'>" +
						"<div class='caption'><p>" + v.title + "</p></div></a></div>")
				});
			});

			$("._list").on("click", "._network_item", function (t) {
				let networkSeq = $(t.target).parents("._network_item").attr("data-seq");
				location.href = "/network/page.do?networkSeq=" + networkSeq;
			});
		});
	</script>
</body>

</html>