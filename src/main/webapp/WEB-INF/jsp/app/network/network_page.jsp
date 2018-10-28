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
					<li><a href="javascript:void(0)" class="_list" href="#">목록</a></li>
					<li><a href="javascript:void(0)" class="_add">추가</a></li>
					<li><a href="javascript:void(0)" class="_edit">수정</a></li>
					<li><a href="javascript:void(0)" class="_link">연결선추가</a></li>
					<li><a href="javascript:void(0)" class="_remove">삭제</a></li>
					<li><a href="javascript:void(0)" class="_json">JSON</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div id="mynetwork"></div>


	<!-- Modal -->
	<div class="modal fade" id="addModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">노드 추가</h4>
				</div>
				<div class="modal-body">
					<form name="addForm">
						<div class="form-group">
							<label for="text_01">레이블을</label>
							<input type="input" class="form-control" id="text_01" maxlength="20" name="label">
						</div>
						<div class="form-group">
							<label for="select_01">모양:</label>
							<select class="form-control" name="shape" id="select_01">
								<option value="ellipse">타원</option>
								<option value="box">사각형</option>
								<option value="circle">원</option>
							</select>
						</div>
						<div class="checkbox">
							<label for="text_02">색:</label>
							<div class="input-group colorpicker-component _colorpicker">
								<input type="text" value="#00AABB" class="form-control" id="text_02" name="color" />
								<span class="input-group-addon"><i></i></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info _addBtn">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function RelationNetwork(id) {
			this.id = id;
			this.network = null;
			this.nodes = null;
			this.edges = null;
			var json = $.getJSON("/delete_me/temp/test.json")
				.done((data) => {
					this.displayNetwork(id, data);
				});
		}
		RelationNetwork.prototype.displayNetwork = function (id, graphData) {
			let container = document.getElementById(this.id);
			this.nodes = new vis.DataSet(graphData.nodes);
			this.edges = new vis.DataSet(graphData.edges);

			let data = {
				nodes: this.nodes,
				edges: this.edges
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
			console.log("####", this.network);
			console.log("####", this.nodes);
			console.log("####", this.edges);

			return Object.keys(obj).map(function (key) {
				obj[key].id = key;
				return obj[key];
			});
		}

		// 선택한 node 반환.
		// 없으면 null 반환
		RelationNetwork.prototype.getSelectNodeId = function (obj) {
			let selectionList = this.network.getSelection();
			if(selectionList.nodes.length > 0){
				return selectionList.nodes[0];
			}
			return null;
		}

		// 선택한 edge 반환.
		// 없으면 null 반환
		RelationNetwork.prototype.getSelectEdgeId = function (obj) {
			let selectionList = this.network.getSelection();
			// edge만 선택 시 반환. 즉 노드 선택을 하여 edge가 선택된 경우는 null 반환
			if(selectionList.nodes.length > 0){
				return null;
			}
			if(selectionList.edges.length > 0){
				return selectionList.edges[0];
			}
			return null;
		}

		RelationNetwork.prototype.getJson = function () {
			// TODO Position...
			this.objectToArray(this.network.getPositions());
			// console.log("%$%%%%%%%%%", this.nodes.get());
			var data = { nodes: this.nodes.get(), edges: this.edges.get() };

			var exportValue = JSON.stringify(data, undefined, 2);
			return exportValue;
		}


		$(() => {
			let relation = new RelationNetwork('mynetwork');
			$("._list").click(() => {
				console.log("list");
			});
			$('._colorpicker').colorpicker();
			// 등록
			$("._add").click(() => {
				console.log("add");
				$("#addModal").modal();
			});
			// 수정
			$("._edit").click(() => {
				console.log("edit");
			});
			// 연결선 추가
			$("._link").click(() => {
				console.log("link");
			});

			// 등록 확인
			$("._addBtn").click(() => {
				let form = $("form[name='addForm']");
				if ($.FORM.isEmptyRtnMsg(form.find("input[name='label']").get(0), "레이블을 입력하세요.")) {
					return;
				}
				let newNode = {
					label: form.find("input[name='label']").val(),
					shape: form.find("select[name='shape']").val(),
					color: form.find("input[name='color']").val(),
					id: (Math.random() * 1e7).toString(32)
				}
				console.log("####", newNode);
				relation.nodes.add(newNode);

				let selectNodeId = relation.getSelectNodeId();
				if(selectNodeId){
					relation.edges.add({
						from: selectNodeId,
						to: newNode.id
					})
				}
				$("#addModal").modal("hide");
			});

			$("._remove").click(() => {
				let nodeId = relation.getSelectNodeId();
				if(nodeId){
					relation.nodes.remove({ id: nodeId});
					return;
				}

				let edgeId = relation.getSelectEdgeId();
				if(edgeId){
					relation.edges.remove({ id: edgeId });
					return;
				}
			});

			$("._json").click(() => {
				var a = relation.getJson();
				console.log("%%%", a);
			});
		});
	</script>
</body>

</html>