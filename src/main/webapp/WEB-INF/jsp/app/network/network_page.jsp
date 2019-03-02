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

		._content {
			padding-top: 90px;
			margin-top: -35px;
			height: 100%;
		}

		#mynetwork {
			width: 100%;
			height: 100%;
			background-color: #f0f0f5;
		}

		.color_label {
			border: 1px solid #CCC;
			padding-left: 15px;
		}
	</style>
	<script type="text/javascript" src="<c:url value='/js/jquery-1.11.2.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery-ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootswatch.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/util.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/vis/vis.js'/>"></script>

	<!-- 오른쪽 마우스 클릭, 메뉴 -->
	<script src="${pageContext.request.contextPath}/lib/jQuery-contextMenu-master/jquery.contextMenu.js"></script>
	<script src="${pageContext.request.contextPath}/lib/jQuery-contextMenu-master/jquery.ui.position.js"></script>
	<link href="${pageContext.request.contextPath}/lib/jQuery-contextMenu-master/jquery.contextMenu.css" rel="stylesheet">
</head>

<body>
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
					<li><a href="/network/list.do" class="_list">목록</a></li>
					<li><a href="javascript:void(0)" class="_add">노드 추가</a></li>
					<li><a href="javascript:void(0)" class="_link">연결선 추가</a></li>
					<li><a href="javascript:void(0)" class="_edit">수정</a></li>
					<li><a href="javascript:void(0)" class="_remove">제거</a></li>
					<li>
						<div style="width: 40px;">&nbsp;</div>
					</li>
					<li><a href="javascript:void(0)" class="_remove">앞으로가기</a></li>
					<li><a href="javascript:void(0)" class="_remove">되돌리기</a></li>
					<li><a href="javascript:void(0)" class="_delete">삭제</a></li>
					<li><a href="javascript:void(0)" class="_fullscreen">전체화면</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="_content">
		<div style="background-color: blue">
			<input type="text" name="title" class="form-control" placeholder="제목 넣어라." />
		</div>
		<div id="mynetwork"></div>
	</div>

	<!-- 노드 Modal -->
	<div class="modal fade" id="addNodeModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">노드 추가</h4>
				</div>
				<div class="modal-body">
					<form name="addNodeForm" onsubmit="return false;">
						<input type="hidden" name="id" value="" />
						<div class="form-group">
							<label for="text_01">레이블</label>
							<input type="input" class="form-control" id="text_01" maxlength="20" name="label">
						</div>
						<div class="form-group">
							<label for="select_01">모양:</label>
							<label><input type="radio" name="shape" value="ellipse">타원</label>
							<label><input type="radio" name="shape" value="box">사각형</label>
							<label><input type="radio" name="shape" value="circle">원</label>
						</div>
						<div class="form-group">
							<label for="text_02">색:</label>
							<label><input type="radio" name="color" value="#ffffcc"><span class="color_label" style="background: #ffffcc;"></span></label>
							<label><input type="radio" name="color" value="#ffffff"><span class="color_label" style="background: #ffffff;"></span></label>
							<label><input type="radio" name="color" value="#ffff66"><span class="color_label" style="background: #ffff66;"></span></label>
							<label><input type="radio" name="color" value="#ccff66"><span class="color_label" style="background: #ccff66;"></span></label>
							<label><input type="radio" name="color" value="#99ccff"><span class="color_label" style="background: #99ccff;"></span></label>
							<label><input type="radio" name="color" value="#ff99ff"><span class="color_label" style="background: #ff99ff;"></span></label>
							<label><input type="radio" name="color" value="#eeeeee"><span class="color_label" style="background: #eeeeee;"></span></label>
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
					<form name="addEdgeForm" onsubmit="return false;">
						<input type="hidden" name="id" value="" />
						<div class="form-group">
							<label for="select_02">시작</label>
							<select class="form-control" name="from" id="select_02">
								<!--동적으로 등록-->
							</select>
						</div>
						<div class="form-group">
							<label for="select_03">끝</label>
							<select class="form-control" name="to" id="select_03">
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
							<label><input type="radio" name="color" value="#777777"><span class="color_label" style="background: #777777;"></span></label>
							<label><input type="radio" name="color" value="#006699"><span class="color_label" style="background: #006699;"></span></label>
							<label><input type="radio" name="color" value="#00ff00"><span class="color_label" style="background: #00ff00;"></span></label>
							<label><input type="radio" name="color" value="#aa44aa"><span class="color_label" style="background: #aa44aa;"></span></label>
							<label><input type="radio" name="color" value="#cc9900"><span class="color_label" style="background: #cc9900;"></span></label>
							<label><input type="radio" name="color" value="#cc0066"><span class="color_label" style="background: #cc0066;"></span></label>
							<label><input type="radio" name="color" value="#3333ff"><span class="color_label" style="background: #3333ff;"></span></label>
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
		const DEFAULT_NODE_COLOR = "#ffffcc";
		const DEFAULT_EDGE_COLOR = "#777777";
		let networkSeq = 0;

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
			let position = this.network.getPositions()
			let ndoes = this.nodes.get();
			ndoes.forEach((v) => {
				let p = position[v.id];
				v["x"] = p.x;
				v["y"] = p.y;
			});

			var data = { nodes: ndoes, edges: this.edges.get() };

			var exportValue = JSON.stringify(data, undefined, 2);
			return exportValue;
		}

		$(() => {
			let relation;
			let url = new URL(location.href);
			networkSeq = url.searchParams.get("networkSeq");
			networkSeq = networkSeq || 0;

			if (networkSeq != 0) {
				$.ajax({
					url: $.APP.getContextRoot("/network/get.json"),
					data: { networkSeq: networkSeq },
					dataType: 'json',
					async: false,
					success: function (data) {
						relation = new RelationNetwork('mynetwork', JSON.parse(data.jsonData));
						$("input[name='title']").val(data.title);
					}
				});
			} else {
				let data = "{\"nodes\": [{\"id\": \"1\", \"label\": \"복슬\", \"shape\": \"ellipse\", \"color\": \"#22ee55\" }],\"edges\": [ ]}";
				relation = new RelationNetwork('mynetwork', JSON.parse(data));
			}

			// 오른쪽 마우스 클릭
			$.contextMenu({
				selector: '#mynetwork',
				callback: function (type, options) {
					$("." + type).trigger("click");
				},
				items: {
					"_add": { name: "노드추가" },
					"_link": { name: "연결선 추가" },
					"_edit": { name: "수정" },
					"_remove": { name: "제거" },
				}
			});

			relation.network.on("select", function (params) {
				menuDisplay();
			});

			// 더블 클릭 시 수정 폼
			relation.network.on("doubleClick", function (params) {
				editForm();
			});

			// 드래그 후 저장
			relation.network.on("dragEnd", function (params) {
				save();
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

				let shape = node.shape || 'ellipse';
				form.find("input[name='shape'][value='" + shape + "']").prop('checked', true);

				let color = node.color || DEFAULT_NODE_COLOR;
				form.find("input[name='color'][value='" + color + "']").prop('checked', true);
				$('#addNodeModal').on('shown.bs.modal', function () {
					form.find("input[name='label']").focus();
				})
				$("#addNodeModal").modal();
			}

			function addEdgeForm(edge) {
				let form = $("form[name='addEdgeForm']");
				let fSelect = form.find("select[name='from']");
				let tSelect = form.find("select[name='to']");
				fSelect.html("");
				tSelect.html("");
				let nodes = relation.nodes.get()

				nodes.sort(function (a, b) {
					return ('' + a.label).localeCompare(b.label);
				});

				nodes.forEach((v) => {
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
				form.find("input[name='color'][value='" + color + "']").prop('checked', true);
				form.find("input[name='label']").focus();
				$('#addEdgeModal').on('shown.bs.modal', function () {
					form.find("input[name='label']").focus();
				})
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
					shape: form.find("input[name='shape']:checked").val(),
					color: form.find("input[name='color']:checked").val(),
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
				save();
			}

			function addEdgeProc() {
				let form = $("form[name='addEdgeForm']");
				let color = form.find("input[name='color']:checked").val()

				let newEdge = {
					id: form.find("input[name='id']").val() || (Math.random() * 1e7).toString(32),
					from: form.find("select[name='from']").val(),
					to: form.find("select[name='to']").val(),
					label: form.find("input[name='label']").val(),
					dashes: form.find("input[name='dashes']:checked").val() == "true",
					color: { color: color, highlight: color },
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
				save();
			}

			// 저장.
			function save() {
				let title = $("input[name='title']").val();
				let json = relation.getJson();

				let param = { networkSeq: networkSeq, title: title, jsonData: json };
				console.log("Saving.", networkSeq)
				$.post("/network/save.do", param, function (data) {
					networkSeq = data;
					console.log("Saved.", networkSeq)
				});
			}

			// 수정 화면
			function editForm() {
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
			}

			$("input[name='title']").on("blur", () => {
				save();
			});

			// 노드 등록
			$("._add").click(() => {
				addNodeForm({ label: '', shape: 'ellipse', color: DEFAULT_NODE_COLOR });
			});
			// 연결선 등록
			$("._link").click(() => addEdgeForm({ from: relation.getSelectNodeId(), to: null, label: '', color: { "color": DEFAULT_EDGE_COLOR, "highlight": DEFAULT_EDGE_COLOR }, dashes: false }));

			// 노드 등록 확인
			$("._addNodeBtn").click(() => addNodeProc());

			// 연결선 등록 확인
			$("._addEdgeBtn").click(() => addEdgeProc());

			// 수정
			$("._edit").click(() => {
				editForm();
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
				save();
			});

			$("._delete").click(() => {
				if (confirm("연결관계 삭제할 거야")) {
					$.post("/network/delete.do", { networkSeq: networkSeq }, function (data) {
						location.href = "/network/list.do";
					});
				}
			});

			$("#addNodeModal").keypress(function (e) {
				if (e.keyCode == 13) {
					$("._addNodeBtn").trigger("click")
				}
			});

			$("#addEdgeModal").keypress(function (e) {
				if (e.keyCode == 13) {
					$("._addEdgeBtn").trigger("click")
				}
			});

			$("._fullscreen").click(()=>{
				let element  = $("#mynetwork")[0];
				
				if (element.requestFullScreen) {
					element.requestFullScreen();
				} else if (element.webkitRequestFullScreen) {
					element.webkitRequestFullScreen();
				} else if (element.mozRequestFullScreen) {
					element.mozRequestFullScreen();
				} else if (element.msRequestFullscreen) {
					element.msRequestFullscreen(); // IE
				}
			});

			$("._edit, ._remove").hide();
		});
	</script>
</body>

</html>