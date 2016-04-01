<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function(){
		var param = {};
		param["moduleName"] = "<%=CommentModule.MAIN%>";
		$("._commentAear").load($.APP.getContextRoot("/app/comment/page.do"), param);
	});
</script>
<!-- Main... -->
<h4>꼰대는 되지 말자.</h4>
<div class="_commentAear">
</div>
