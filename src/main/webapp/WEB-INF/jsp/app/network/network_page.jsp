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
				<a class="navbar-brand" href="#">복슬관계</a>
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
			var json = $.getJSON("/delete_me/temp/test.json")
				.done((data) => {
					this.displayNetwork(id, data);
				});
		}

		RelationNetwork.prototype.displayNetwork = function (id, graphData) {
			let container = document.getElementById(this.id);
			let data = {
				nodes: graphData.nodes,
				edges: graphData.edges
			};
			let options = {
				physics: false,
				edges: {
					smooth: {
						type: 'continuous'
					}
				}
			};
			this.network = new vis.Network(container, data, options);
		}

		RelationNetwork.prototype.objectToArray = function (obj) {
			console.log("####", obj);
			console.log("####", this.network);
			return Object.keys(obj).map(function (key) {
				obj[key].id = key;
				return obj[key];
			});
		}

		RelationNetwork.prototype.getJson = function () {
			var nodes = this.objectToArray(this.network.getPositions());

			nodes.forEach((elem, index) => {
				// need to replace this with a tree of the network, then get child direct children of the element
				elem.connections = this.network.getConnectedNodes(index);
			});

			var exportValue = JSON.stringify(nodes, undefined, 2);

			return exportValue;
		}

		$(() => {
			let relation = new RelationNetwork('mynetwork');
			$("._json").click(() => {
				var a = relation.getJson();
				// console.log("$$$$$$$$$", a);
			});
		});
	</script>
</body>

</html>