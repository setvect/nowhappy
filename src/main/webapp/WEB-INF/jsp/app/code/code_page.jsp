<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.setvect.nowhappy.comment.service.CommentModule"%>
<%@page import="com.setvect.nowhappy.comment.web.CommentController"%>
<%@page import="com.setvect.nowhappy.user.vo.UserVo"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	UserVo user = (UserVo)request.getAttribute(WebAttributeKey.USER_SESSION_KEY);
%>
<script type="text/javascript">
	
</script>
<div class="codeManger" data-ng-app="codeMangerApp"  data-ng-controller="codeManagerController">
	<!-- 목록 폼 -->
	<div data-ng-show="view =='list'">
		<div class="panel panel-default">
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<select class="form-control" data-ng-model="searchOption">
						<option value="code">코드</option>
						<option value="name">이름</option>
					</select> <input type="text" data-ng-model="searchWord" class="form-control">
				</div>
				<button type="submit" class="btn btn-default" data-ng-click="search();">검색</button>
				<button type="submit" class="btn btn-default" data-ng-click="searchCancel();" data-ng-show="searchWord != ''">검색 취소</button>
			</form>
	
			<!-- Table -->
			<table class="table">
				<thead>
					<tr>
						<th>No.</th>
						<th>상위분류</th>
						<th>코드</th>
						<th>코드값</th>
						<th>정렬</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="x in list">
						<td>{{$index}}</td>
						<td>{{x.majorCode}}</td>
						<td>{{x.minorCode}}</td>
						<td>{{x.codeValue}}</td>
						<td>{{x.orderNo}}</td>
						<td><a href="#" data-ng-click="update(list[$index])" class="btn btn-default btn-xs">수정</a></td>
						<td><a href="#" data-ng-click="remove(list[$index])" class="btn btn-default btn-xs">삭제</a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="text-center">
			<ul class="pagination pagination-sm">
				<li data-ng-repeat="n in pageItem track by $index">
					<a href="#" style="background-color: {{pageNumber == n ? 'olive':''}}" data-ng-click="page(n)">{{n}}</a>
				</li>
			</ul>
		</div>
		<a href="#" class="btn btn-default" data-ng-click="write()">만들기</a> 
	</div>

	<!-- 등록 폼 -->
	<div data-ng-show="view =='write' || view =='update' ">
		<div class="well bs-component">
			<form class="form-horizontal">
				<fieldset>
					<legend>게시판 등록</legend>
					<div class="form-group">
						<label class="col-lg-2 control-label">상위분류</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.majorCode" maxlength="10">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.minorCode" maxlength="10" required >
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">코드값</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.codeValue">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">순서</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" data-ng-model="readItem.orderNo">
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<button type="button" class="btn btn-default" data-ng-click="listback()">취소</button>
		<button type="button" class="btn btn-default" data-ng-click="writeOrUpdateSummit()">완료</button>
	</div>
</div>
