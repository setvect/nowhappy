<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="panel panel-default">
	<form class="navbar-form navbar-left" role="search">
		<div class="form-group">
			<select class="form-control" data-ng-model="searchParam.option">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select> 
			<input type="text" class="form-control" data-ng-model="searchParam.word">
		</div>
		{{search}}
		<button type="submit" class="btn btn-default" data-ng-click="search();">검색</button>
		<button type="submit" class="btn btn-default" data-ng-click="searchCancel();" data-ng-show="searchWord != ''">검색 취소</button>
	</form>

	<!-- Table -->
	<table class="table">
		<thead>
			<tr>
				<th class="col-md-7">제목</th>
				<th class="col-md-1">조회</th>
				<th class="col-md-2 date">날짜</th>
			</tr>
		</thead>
		<tbody>
			<tr data-ng-repeat="x in list">
				<td class="col-md-7">
					<a href="#/read/{{x.articleSeq}}" data-ng-if="!x.encodeF">{{x.title}}</a>
					<a href="#/encode/{{x.articleSeq}}" data-ng-if="x.encodeF">{{x.title}}(비공개)</a>
				</td>
				<td class="col-md-1">{{x.hitCount}}</td>
				<td class="col-md-2 date">{{x.regDate | date:'yyyy.MM.dd HH:mm'}}</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="text-center">
	<ul class="pagination pagination-sm">
		<li data-ng-repeat="n in pageItem track by $index">
			<a href="javascript:" style="background-color: {{pageNumber == n ? 'olive':''}}" data-ng-click="page(n)">{{n}}</a>
		</li>
	</ul>
</div>
<a href="#/write" class="btn btn-default" data-ng-show="auth.write">글쓰기</a>
