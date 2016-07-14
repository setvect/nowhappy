<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div>
	<div class="panel panel-default">
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<select class="form-control">
					<option data-ng-repeat="cate in category" value="{{cate.minorCode}}">
						{{cate.codeValue}}
					</option>
				</select> 
				<input type="text" class="form-control" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">검색</button>
		</form>
	
		<!-- Table -->
		<table class="table">
			<thead>
				<tr>
					<th class="col-md-4">문제</th>
					<th class="col-md-4">해결</th>
					<th class="col-md-1 center">분류</th>
					<th class="col-md-1 center">날짜</th>
				</tr>
			</thead>
			<tbody>
				<tr data-ng-repeat="x in list">
					<td class="col-md-4">{{x.problem}}</td>
					<td class="col-md-4">{{x.solution}}</td>
					<td class="col-md-1 center">{{x.classifyC}}</td>
					<td class="col-md-1 center">{{x.regDate | date:'yyyy.MM.dd'}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="text-center">
		<div class="text-center">
			<ul class="pagination pagination-sm">
				<li data-ng-if="pagePreviousGroup != -1"><a href="javascript:" data-ng-click="page(pagePreviousGroup)">이전</a></li>
				<li data-ng-repeat="n in pageItem track by $index">
					<a href="javascript:" style="background-color: {{pageNumber == n ? '#FFDEAD':''}}" data-ng-click="page(n)">{{n}}</a>
				</li>
				<li data-ng-if="pageNextGroup != -1"><a href="javascript:" data-ng-click="page(pageNextGroup)">다음</a></li>
			</ul>
		</div>
		<a href="#/write/{{searchParam.currentCategory.categorySeq}}" class="btn btn-primary" >글쓰기</a>
	</div>
</div>