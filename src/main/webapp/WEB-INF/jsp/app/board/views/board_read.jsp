<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="bs-component">
	<div class="jumbotron">
		<h5>{{readItem.title}}</h5>
		<p data-ng-bind-html="trustAsHtml(readItem.content)"></p>
		<span>{{readItem.regDate | date:'yyyy.MM.dd'}}</span>
		<ul>
			<li data-ng-repeat="f in attachList track by $index">
				첨부파일{{$index + 1}}: 
				<a href="<%=request.getContextPath()%>/download.do?s={{f.savePathEncode}}&amp;d={{f.originalNameEncode}}">{{f.originalName}} ({{(f.size / 1024.0) | number:0}}k)</a>
			</li>
		</ul>

	</div>
</div>
<a href="javascript:" data-ng-click="update(readItem)" class="btn btn-default">수정</a> 
<a href="javascript:" data-ng-click="remove(readItem)" class="btn btn-default">삭제</a> 
<a href="javascript:" data-ng-click="listback()"class="btn btn-default">목록</a>
