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
		<button type="submit" class="btn btn-default" data-ng-click="search();">검색</button>
		<button type="submit" class="btn btn-default" data-ng-click="searchCancel();" data-ng-show="searchWord != ''">검색 취소</button>
	</form>

	<!-- Table -->
	<table class="table">
		<thead>
			<tr>
				<th class="col-md-1">카테고리</th>
				<th class="col-md-9">제목</th>
				<th class="col-md-1 date">마지막 변경</th>
				<th class="col-md-1 date">처음 등록</th>
			</tr>
		</thead>
		<tbody>
			<tr data-ng-repeat="x in list">
				<td class="col-md-2">
					{{getCategory(x.categorySeq).name}}
				</td>
				<td class="col-md-6">
					<a href="#/read/{{x.noteSeq}}">{{x.title}}</a>
				</td>
				<td class="col-md-1 date">{{x.uptDateDiff}}</td>
				<td class="col-md-1 date">{{x.regDate | date:'yyyy.MM.dd'}}</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="text-center">
	<ul class="pagination pagination-sm">
		<li data-ng-if="pagePreviousGroup != -1"><a href="javascript:" data-ng-click="page(pagePreviousGroup)">이전</a></li>
		<li data-ng-repeat="n in pageItem track by $index">
			<a href="javascript:" style="background-color: {{pageNumber == n ? '#FFDEAD':''}}" data-ng-click="page(n)">{{n}}</a>
		</li>
		<li data-ng-if="pageNextGroup != -1"><a href="javascript:" data-ng-click="page(pageNextGroup)">다음</a></li>
	</ul>
</div>
<a href="#/write/{{searchParam.currentCategory.categorySeq}}" class="btn btn-primary" data-ng-show="searchParam.currentCategory">글쓰기</a>
