<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="bs-component">
	<div class="col-lg-12">
		<h1 class="page-header">{{readItem.title}}</h1>
		<div class="panel panel-default">
			<div class="panel-body">
				<p data-ng-bind-html="trustAsHtml(readItem.content)"></p>
				
				<div style="padding: 0 10px">
					<div data-ng-repeat="f in attachMapList[readItem.noteSeq] track by $index">
						<img src="<c:url value="/servlet/Thumbnail"/>?i={{f.url}}&w=290&h=450" alt="{{f.originalName}}" data-ng-if="f.image" data-ng-click="imgPopup(f.url)"/><br/><br/>
					</div>
				</div>		
				
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
		<br/><br/>
	</div>
</div>
