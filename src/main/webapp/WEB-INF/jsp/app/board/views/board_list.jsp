<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="panel panel-default">
	<!-- Table -->
	<table class="table">
		<tbody>
			<tr data-ng-repeat="x in list">
				<td>
					<a href="#/read/{{x.articleSeq}}" data-ng-if="!x.encodeF">{{x.title}}</a>
					<a href="#/encode/{{x.articleSeq}}" data-ng-if="x.encodeF">{{x.title}}(비공개)</a>
				</td>
				<td class="date">{{x.regDate | date:'yyyy.MM.dd'}}</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="text-center">
	<ul class="pagination pagination-sm">
		<li data-ng-if="pagePreviousGroup != -1"><a href="javascript:" data-ng-click="page(pagePreviousGroup)">이전</a></li>
		<li data-ng-repeat="n in pageItem track by $index">
			<a href="javascript:" style="background-color: {{pageNumber == n ? 'olive':''}}" data-ng-click="page(n)">{{n}}</a>
		</li>
		<li data-ng-if="pageNextGroup != -1"><a href="javascript:" data-ng-click="page(pageNextGroup)">다음</a></li>
	</ul>
</div>