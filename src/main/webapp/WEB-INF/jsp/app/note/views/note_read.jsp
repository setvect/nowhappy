<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="bs-component">
	<div class="jumbotron">
		<h5>{{readItem.title}}</h5>
		<p data-ng-bind-html="trustAsHtml(readItem.content)"></p>
		<span>최종 수정일: {{readItem.uptDate | date:'yyyy.MM.dd HH:mm:ss'}}</span><br/>
		<span>최초 등록일: {{readItem.regDate | date:'yyyy.MM.dd HH:mm:ss'}}</span>
		<ul>
			<li data-ng-repeat="f in attachMapList[readItem.noteSeq] track by $index">
				첨부파일{{$index + 1}}: 
				<a href="<%=request.getContextPath()%>/download.do?s={{f.savePathEncode}}&amp;d={{f.originalNameEncode}}">{{f.originalName}} ({{(f.size / 1024.0) | number:0}}k)</a>
			</li>
		</ul>

	</div>
</div>
<a href="#/update/{{readItem.noteSeq}}" class="btn btn-primary">수정</a> 
<a href="javascript:" data-ng-click="remove(readItem)" class="btn btn-warning">삭제</a> 
<a href="#list/{{searchParam.currentCategory.categorySeq}}" class="btn btn-default">목록</a>
