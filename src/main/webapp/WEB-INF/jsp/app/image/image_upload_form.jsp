<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page import="com.setvect.nowhappy.image.web.ImageUploadController"%>
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
<link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet">
<link href="<c:url value="/css/custom.css"/>" rel="stylesheet">

<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/bootswatch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/util.js"/>"></script>
<script type="text/javascript" src="<c:url value="/editor/js/HuskyEZCreator.js"/>"></script>

<script type="text/javascript">
	var ImageUpload = new Object();
	ImageUpload.upload = function() {
		if ($.FORM.isEmptyRtnMsg(document.uploadAction.imageFile, "이미지를 선택해 줘~")) {
			return;
		}

		var imageName = document.uploadAction.imageFile.value;

		if (!$.STR.isImage(imageName)) {
			$.FORM.selectMsg(document.uploadAction.imageFile, "이미지 파일만 업로드 하럼.");
			return;
		}

		document.uploadAction.submit();
	};

	$(function() {
		var clipboard = new imageClipboard("image_canvas", true);
		/**
		 * image pasting into canvas
		 * 
		 * @param {string} canvas_id - canvas id
		 * @param {boolean} autoresize - if canvas will be resized
		 */
		function imageClipboard(canvas_id, autoresize) {
			var _self = this;
			var canvas = document.getElementById(canvas_id);
			var ctx = document.getElementById(canvas_id).getContext("2d");

			//handlers
			document.addEventListener('paste', function(e) {
				_self.pasteAuto(e);
			}, false);

			//on paste
			this.pasteAuto = function(e) {
				if (e.clipboardData) {
					var items = e.clipboardData.items;
					if (!items)
						return;

					//access data directly
					for (var i = 0; i < items.length; i++) {
						if (items[i].type.indexOf("image") !== -1) {
							//image
							var blob = items[i].getAsFile();
							var URLObj = window.URL || window.webkitURL;
							var source = URLObj.createObjectURL(blob);
							this.pasteCreateImage(source);
						}
					}
					e.preventDefault();
				}
			};
			//draw pasted image to canvas
			this.pasteCreateImage = function(source) {
				var pastedImage = new Image();
				pastedImage.onload = function() {
					if (autoresize == true) {
						//resize
						canvas.width = pastedImage.width;
						canvas.height = pastedImage.height;
					} else {
						//clear canvas
						ctx.clearRect(0, 0, canvas.width, canvas.height);
					}
					ctx.drawImage(pastedImage, 0, 0);
				};
				pastedImage.src = source;

				$("#image_canvas").click(function() {
					clipboard.uploadServerAndApply();
				});
			};
			
			// 이미지를 서버에 업로드하고 편집창에 적용 
			this.uploadServerAndApply = function(){
				var data = $("#image_canvas")[0].toDataURL('image/jpeg');
				console.log(data);
				
				var sHTML = "<img src='" + data + "'/>";
				
				// 하드 코딩..원래는 이미지 팝업창 오픈 할때 이미지를 받을 객체의 파라미터를 넣어야 됨.
				opener.nhn.husky.PopUpManager.setCallback(window, 'PASTE_HTML', [sHTML]);
				window.close();
			}
		};
		 
	});
</script>
</head>
<body style="padding:10px;">
	<div class="popup">
		1. 첨부파일로 업로드
		<form name="uploadAction" id="uploadAction" method="post" enctype="multipart/form-data"
			action="<c:url value="/image/upload.do"/>">
			<input type="hidden" name="mode" value="<%=ImageUploadController.Mode.UPLOAD_ACTION%>" />
			<table>
				<tr>
					<th>이미지</th>
					<td><input type="file" name="imageFile" style="width: 100%" /></td>
				</tr>
			</table>
		</form>
		<div>
			<input type="button" value="확인" onclick="ImageUpload.upload()" /> <input type="button" value="취소"
				onclick="window.close();" />
		</div>
		<br/><br/>
		2. 클립보드에서 붙여 넣기<br/>
		Ctrl+V 후 이미지를 클릭
		<br /> <br />
		<canvas style="border: 1px solid grey;" id="image_canvas" width="300" height="300"></canvas>
	</div>
</body>
</html>