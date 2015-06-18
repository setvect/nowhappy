<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="panel panel-default">
	<!-- Table -->
	<table class="table">
		<tbody>
			<tr data-ng-repeat="x in list">
				<td><a href="#" data-ng-click="read(list[$index])">{{x.title}}</a></td>
				<td class="date">{{x.regDate | date:'yyyy.MM.dd'}}</td>
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
<a href="javascript:" class="btn btn-default" data-ng-click="write()" data-ng-show="auth.write">글쓰기</a>
