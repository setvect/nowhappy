<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div>
	<div class="panel panel-default">
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<select class="form-control"  data-ng-model="searchParam.classifyC" data-ng-change="search();">
				<option value="">--전체--</option>
					<option data-ng-repeat="cate in category" value="{{cate.minorCode}}">
						{{cate.codeValue}}
					</option>
				</select> 
				<input type="text" class="form-control" placeholder="Search" data-ng-model="searchParam.word">
			</div>
			<button type="submit" class="btn btn-default" data-ng-click="search();">검색</button>
			<button type="submit" class="btn btn-default" data-ng-click="searchCancel();" data-ng-show="searchParam.word != ''">검색 취소</button>
		</form>
	
		<!-- Table -->
		<table class="table">
			<thead>
				<tr>
					<th class="col-md-1 center">분류</th>
					<th class="col-md-4">문제</th>
					<th class="col-md-4">해결</th>
					<th class="col-md-1 center">날짜</th>
				</tr>
			</thead>
			<tbody>
				<tr data-ng-repeat="x in list">	
					<td class="col-md-1 center">{{x.classifyCode.codeValue}}</td>
					<td class="col-md-4 td_ellipsis">
						<a href="#/read/{{x.knowledgeSeq}}" data-note-seq="{{x.knowledgeSeq}}">{{x.problem | clearHtml}}</a>
					</td>
					<td class="col-md-4 td_ellipsis">
						<a href="#/read/{{x.knowledgeSeq}}" data-note-seq="{{x.knowledgeSeq}}">{{x.solution | clearHtml}}</a>
					</td>
					<td class="col-md-1 center">{{x.regDate | date:'yyyy.MM.dd'}}</td>
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
		<a href="#/write/{{searchParam.currentCategory.categorySeq}}" class="btn btn-primary" style="float: left;" >글쓰기</a>
	</div>
</div>