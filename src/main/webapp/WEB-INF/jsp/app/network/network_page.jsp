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
	<style type="text/css">
		body,
		html {
			height: 100%;
		}

		#mynetwork {
			width: 100%;
			height: 100%;
			border: 1px solid lightgray;
		}
	</style>
	<script type="text/javascript" src="<c:url value='/js/jquery-1.11.2.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery-ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootswatch.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/util.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/vis/vis.js'/>"></script>
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
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">저장</a></li>
					<li><a href="javascript:void(0)" class="_">About</a></li>
					<li><a href="javascript:void(0)" class="_json">JSON</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div id="mynetwork"></div>

	<script type="text/javascript">
		function RelationNetwork(id) {
			this.id = id;
			this.network = null;
			this.displayNetwork(id);
		}

		RelationNetwork.prototype.displayNetwork = function(id) {
			// create an array with nodes
			let nodes = new vis.DataSet([
				{ id: 1, label: 'Node 1' },
				{ id: 2, label: 'Node 2' },
				{ id: 3, label: 'Node 3' },
				{ id: 4, label: 'Node 4' },
				{ id: 5, label: 'Node 5' }
			]);

			// create an array with edges
			let edges = new vis.DataSet([
				{ from: 1, to: 3 },
				{ from: 1, to: 2 },
				{ from: 2, to: 4 },
				{ from: 2, to: 5 },
				{ from: 3, to: 3 }
			]);

			// create a network
			let container = document.getElementById(this.id);
			let data = {
				nodes: nodes,
				edges: edges
			};
			let options = { physics: false };
			network = new vis.Network(container, data, options);
		}

		RelationNetwork.prototype.toJson = function(){
			console.log("$$$$$$$$$$$$$$$$");
		}

		$(() => {
			let network = new RelationNetwork('mynetwork');
			network.toJson();
		});
	</script>
</body>

</html>