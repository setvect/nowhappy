<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
	boolean login = user != null;
%>
<script type="text/javascript">
</script>
<!-- Main... -->
<div class="page-header">
	<h1>잠시왔다 가는 세상 즐겁게 살아야죠.</h1>
</div>
<%
	if(login){
%>
<div class="well bs-component">
	<div class="input-group">
		<textarea class="form-control" rows="1"></textarea>
		<span class="input-group-btn">
			<button class="btn btn-default" type="button">등록</button>
		</span>
	</div>
	<!-- /input-group -->
</div>
<%
	}
%>
<div>
	<ul>
		<li>aaaaa <a href="#" class="btn btn-default btn-xs">삭제</a></li>
		<li>aaaaa</li>
		<li>aaaaa</li>
	</ul>
	<a href="#" class="btn btn-default  btn-lg btn-block btn-xs">더 불러오기</a>
</div>