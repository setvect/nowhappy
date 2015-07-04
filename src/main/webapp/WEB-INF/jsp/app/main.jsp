<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function(){
		var param = {};
		param["moduleName"] = "<%=CommentModule.MAIN%>";
		$("._commentAear").load(mainCtrl.getUrl("/app/comment/page.do"), param);
	});
</script>
<!-- Main... -->
<h4>잠시왔다 가는 세상 즐겁게 살아야죠.</h4>
<div class="_commentAear">
</div>
