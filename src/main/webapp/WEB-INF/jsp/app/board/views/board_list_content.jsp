<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="panel panel-default">
	<div class="row" data-ng-repeat="x in list">
		<div class="col-sm-12 col-xs-12">
			<h6>{{x.title}}</h6>  
			<p>{{x.content}}</p>
			<span>({{x.regDate | date:'yyyy.MM.dd'}})</span>
		</div>
	</div>
</div>
<div class="text-center">
	<ul class="pagination pagination-sm">
		<li data-ng-repeat="n in pageItem track by $index">
			<a href="javascript:" style="background-color: {{pageNumber == n ? 'olive':''}}" data-ng-click="page(n)">{{n}}</a>
		</li>
	</ul>
</div>