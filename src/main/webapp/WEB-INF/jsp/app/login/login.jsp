<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function(){
		$("._loginBtn").on("click", function(){
			var param = $("._loginForm").serialize();
			$.post(mainCtrl.getUrl("app/login/action.do"), param, function(result){
				console.log(result);
			});
			return false;
		});
	});
</script>
<!-- login -->
<form class="form-signin _loginForm" method="post">
	<h2 class="form-signin-heading">넌 누구냐?</h2>
	<input name="userId" type="text" class="form-control" placeholder="ID" autofocus> 
	<input name="passwd" type="password" class="form-control"	placeholder="Password"> 
	<label class="checkbox"> <input type="checkbox" value="remember-me">
		Remember me
	</label>
	<button class="btn btn-lg btn-primary btn-block _loginBtn" >들어가기</button>
</form>
