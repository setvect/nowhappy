<%@page import="com.setvect.nowhappy.ApplicationConstant.WebCommon"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<script type="text/javascript">
	$(function(){
		$("._loginBtn").on("click", function(){
			var param = $("._loginForm").serialize();
			$.post($.APP.getContextRoot("/app/login/action.do"), param, function(result){
				var login = result["<%=WebAttributeKey.PROCESS_RESULT%>"];
				if(login){
					var loadPage = result["<%=WebAttributeKey.LOAD_PAGE%>"]
					var page = loadPage == "" ? "/home.do" : loadPage;
					location.href = page;
				}
				else{
					alert("틀렸다. 넌 누구냐?")
				}
			});
			return false;
		});
	});
</script>
<!-- login -->
<form class="form-signin _loginForm" method="post">
	<input type="hidden" name="<%=WebCommon.RETURN_URL%>" value="<%=request.getAttribute(WebAttributeKey.RETURN_URL)%>"/>
	
	<h2 class="form-signin-heading">넌 누구냐?</h2>
	<input name="userId" type="text" class="form-control" placeholder="ID" autofocus> 
	<input name="passwd" type="password" class="form-control"	placeholder="Password"> 
	<label class="checkbox"> <input type="checkbox" value="remember-me" name="statusSave">
		Remember me
	</label>
	<button class="btn btn-lg btn-primary btn-block _loginBtn" >들어가기</button>
</form>
