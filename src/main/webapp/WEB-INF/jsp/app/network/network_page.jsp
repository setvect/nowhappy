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
					<li><a href="javascript:void(0)" class="_add">노드 추가</a></li>
					<li><a href="javascript:void(0)" class="_link">연결선 추가</a></li>
					<li><a href="javascript:void(0)" class="_edit">수정</a></li>
					<li><a href="javascript:void(0)" class="_remove">삭제</a></li>
					<li>
						<div style="width: 40px;">&nbsp;</div>
					</li>
					<li><a href="javascript:void(0)" class="_remove">앞으로가기</a></li>
					<li><a href="javascript:void(0)" class="_remove">되돌리기</a></li>
					<li><a href="javascript:void(0)" class="_json">JSON</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div id="mynetwork"></div>

	<!-- 노드 Modal -->
	<div class="modal fade" id="addNodeModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">노드 추가</h4>
				</div>
				<div class="modal-body">
					<form name="addNodeForm">
						<input type="hidden" name="id" value="" />
						<div class="form-group">
							<label for="text_01">레이블</label>
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
						<div class="form-group">
							<label for="text_02">색:</label>
							<div class="input-group colorpicker-component _colorpicker">
								<input type="text" value="#00AABB" class="form-control" id="text_02" name="color" />
								<span class="input-group-addon"><i></i></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info _addNodeBtn">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 연결선 Modal -->
	<div class="modal fade" id="addEdgeModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">연결선 추가</h4>
				</div>
				<div class="modal-body">
					<form name="addEdgeForm">
						<input type="hidden" name="id" value="" />
						<div class="form-group">
							<label for="text_01">시작</label>
							<select class="form-control" name="from" id="select_01">
								<!--동적으로 등록-->
							</select>
						</div>
						<div class="form-group">
							<label for="text_01">끝</label>
							<select class="form-control" name="to" id="select_01">
								<!--동적으로 등록-->
							</select>
						</div>
						<div class="form-group">
							<label for="text_01">레이블</label>
							<input type="input" class="form-control" id="text_01" maxlength="20" name="label">
						</div>
						<div class="form-group">
							<label>선형태:</label>
							<label><input type="radio" value="false" name="dashes" checked="checked">실선</label>
							<label><input type="radio" value="true" name="dashes">점선</label>
						</div>
						<div class="form-group">
							<label for="text_02">색:</label>
							<div class="input-group colorpicker-component _colorpicker">
								<input type="text" value="#00AABB" class="form-control" id="text_02" name="color" />
								<span class="input-group-addon"><i></i></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info _addEdgeBtn">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		const DEFAULT_NODE_COLOR = "#ccffcc";
		const DEFAULT_EDGE_COLOR = "#66ff66";



		function RelationNetwork(id, data) {
			this.id = id;
			this.network = null;
			this.nodes = null;
			this.edges = null;
			this.displayNetwork(id, data);
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

		// 선택한 node 반환.
		// 없으면 null 반환
		RelationNetwork.prototype.getSelectNodeId = function (obj) {
			let selectionList = this.network.getSelection();
			if (selectionList.nodes.length > 0) {
				return selectionList.nodes[0];
			}
			return null;
		}

		// 선택한 edge 반환.
		// 없으면 null 반환
		RelationNetwork.prototype.getSelectEdgeId = function (obj) {
			let selectionList = this.network.getSelection();
			// edge만 선택 시 반환. 즉 노드 선택을 하여 edge가 선택된 경우는 null 반환
			if (selectionList.nodes.length > 0) {
				return null;
			}
			if (selectionList.edges.length > 0) {
				return selectionList.edges[0];
			}
			return null;
		}

		RelationNetwork.prototype.getJson = function () {
			// TODO Position...
			// this.network.getPositions()
			var data = { nodes: this.nodes.get(), edges: this.edges.get() };

			var exportValue = JSON.stringify(data, undefined, 2);
			return exportValue;
		}

		$(() => {
			let relation;
			$.ajax({
				url: "/delete_me/temp/test.json",
				dataType: 'json',
				async: false,
				success: function (data) {
					relation = new RelationNetwork('mynetwork', data);
				}
			});

			relation.network.on("select", function (params) {
				menuDisplay();
			});

			// 수정, 삭제 메뉴 표시 여부
			function menuDisplay() {
				let isSelectObject = relation.getSelectNodeId() != null || relation.getSelectEdgeId() != null;
				$("._edit, ._remove").hide();
				if (isSelectObject) {
					$("._edit, ._remove").show();
				}
			}

			function addNodeForm(node) {
				let form = $("form[name='addNodeForm']");
				form.find("input[name='id']").val(node.id);
				form.find("input[name='label']").val(node.label);
				form.find("select[name='shape']").val(node.shape);
				form.find("input[name='color']").val(node.color || DEFAULT_NODE_COLOR);
				$('._colorpicker').colorpicker('destroy');
				$('._colorpicker').colorpicker({ color: node.color || DEFAULT_NODE_COLOR });
				$("#addNodeModal").modal();
			}

			function addEdgeForm(edge) {
				let form = $("form[name='addEdgeForm']");
				let fSelect = form.find("select[name='from']");
				let tSelect = form.find("select[name='to']");
				fSelect.html("");
				tSelect.html("");
				relation.nodes.forEach((v) => {
					fSelect.append(new Option(v.label, v.id));
					tSelect.append(new Option(v.label, v.id));
				});

				form.find("input[name='id']").val(edge.id);
				if (edge.from) {
					form.find("select[name='from']").val(edge.from);
				}
				if (edge.to) {
					form.find("select[name='to']").val(edge.to);
				}

				form.find("input[name='label']").val(edge.label);
				let dashes = edge.dashes || 'false';
				form.find("input[name='dashes'][value=" + edge.dashes + "]").prop("checked", true);
				let color = edge.color ? edge.color.color : DEFAULT_EDGE_COLOR;
				form.find("input[name='color']").val();
				$('._colorpicker').colorpicker('destroy');
				$('._colorpicker').colorpicker({ color: color });
				$("#addEdgeModal").modal();
			}

			function addNodeProc() {
				let form = $("form[name='addNodeForm']");
				if ($.FORM.isEmptyRtnMsg(form.find("input[name='label']").get(0), "레이블을 입력하세요.")) {
					return;
				}
				let newNode = {
					id: form.find("input[name='id']").val() || (Math.random() * 1e7).toString(32),
					label: form.find("input[name='label']").val(),
					shape: form.find("select[name='shape']").val(),
					color: form.find("input[name='color']").val(),
				}
				// 수정
				if (form.find("input[name='id']").val()) {
					relation.nodes.update(newNode);
				}
				// 추가
				else {
					relation.nodes.add(newNode);
					let selectNodeId = relation.getSelectNodeId();
					if (selectNodeId) {
						relation.edges.add({
							from: selectNodeId,
							to: newNode.id
						})
					}
				}
				$("#addNodeModal").modal("hide");
				menuDisplay();
			}

			function addEdgeProc() {
				let form = $("form[name='addEdgeForm']");
				let newEdge = {
					id: form.find("input[name='id']").val() || (Math.random() * 1e7).toString(32),
					from: form.find("select[name='from']").val(),
					to: form.find("select[name='to']").val(),
					label: form.find("input[name='label']").val(),
					dashes: form.find("input[name='dashes']:checked").val() == "true",
					color: { color: form.find("input[name='color']").val(), highlight: form.find("input[name='color']").val() },
				}
				// 수정
				if (form.find("input[name='id']").val()) {
					relation.edges.update(newEdge);
				}
				// 추가
				else {
					relation.edges.add(newEdge);
				}

				$("#addEdgeModal").modal("hide");
				menuDisplay();
			}


			$("._list").click(() => {
				console.log("list");
			});

			// 노드 등록
			$("._add").click(() => addNodeForm({ label: '', shape: 'ellipse', color: DEFAULT_NODE_COLOR }));
			// 연결선 등록
			$("._link").click(() => addEdgeForm({ from: relation.getSelectNodeId(), to: null, label: '', color: { "color": DEFAULT_EDGE_COLOR, "highlight": DEFAULT_EDGE_COLOR }, dashes: false }));

			// 노드 등록 확인
			$("._addNodeBtn").click(() => addNodeProc());

			// 연결선 등록 확인
			$("._addEdgeBtn").click(() => addEdgeProc());

			// 수정
			$("._edit").click(() => {
				let nodeId = relation.getSelectNodeId();
				if (nodeId) {
					addNodeForm(relation.nodes.get(nodeId));
					return;
				}
				let edgeId = relation.getSelectEdgeId();
				if (edgeId) {
					addEdgeForm(relation.edges.get(edgeId));
					return;
				}
				alert("뭐라도 선택해라.")
			});

			$("._remove").click(() => {
				let selectionList = relation.network.getSelection();
				selectionList.nodes.forEach((id) => {
					relation.nodes.remove({ id: id });
				});
				selectionList.edges.forEach((id) => {
					relation.edges.remove({ id: id });
				});
				menuDisplay();
			});

			$("._json").click(() => {
				var a = relation.getJson();
				console.log("%%%", a);
			});

			$("._edit, ._remove").hide();
		});
	</script>
</body>

</html>