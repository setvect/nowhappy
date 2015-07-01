<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
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
	ImageUpload.upload = function(){
		if($.FORM.isEmptyRtnMsg(document.uploadAction.imageFile, "이미지를 선택해 줘~")){
			return;
		}	

		var imageName = document.uploadAction.imageFile.value;
		
		if(!$.STR.isImage(imageName)){
			$.FORM.selectMsg(document.uploadAction.imageFile, "이미지 파일만 업로드 하럼.");
			return;			
		}		
		
		document.uploadAction.submit();
	};
</script>
</head>
<body>
<div class="popup">
	<form name="uploadAction" id="uploadAction" method="post" enctype="multipart/form-data" action="<c:url value="/image/upload.do"/>">
		<input type="hidden" name="mode" value="<%=ImageUploadController.Mode.UPLOAD_ACTION%>"/>
		<table>
			<tr>
				<th>이미지</th>
				<td><input type="file" name="imageFile" style="width:100%"/></td>
			</tr>
		</table>
	</form>
	<div>
		<input type="button" value="확인" onclick="ImageUpload.upload()"/>
		<input type="button" value="취소" onclick="window.close();"/>
	</div>
</div>
</body>
</html>