<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-default">
	<div class="row" data-ng-repeat="x in list">
		<div class="col-sm-12 col-xs-12">
			<h6>{{x.title}}</h6>  
			
			<div style="float: left; padding: 0 10px">
				<div data-ng-repeat="f in attachMapList[x.articleSeq] track by $index">
					<img src="<c:url value="/servlet/Thumbnail"/>?i={{f.url}}&w=290&h=450" alt="{{f.originalName}}" data-ng-if="f.image" data-ng-click="imgPopup(f.url)"/><br/><br/>
				</div>
			</div>
			
			<p data-ng-bind-html="trustAsHtml(x.content)"></p>
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