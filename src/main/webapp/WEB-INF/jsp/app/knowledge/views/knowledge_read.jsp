<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="col-lg-12">
	<div class="panel panel-default _knowledge_content">
		<div class="panel-body">
			<h4>문제</h4>
			<div><p data-ng-bind-html="trustAsHtml(readItem.problem)"></p></div>
		</div>
		<div class="panel-body">
			<h4>해결</h4>
			<div><p data-ng-bind-html="trustAsHtml(readItem.solution)"></p></div>
		</div>
		<div class="panel-body">{{readItem.regDate | date:'yyyy.MM.dd HH:mm:ss'}}</div>
	</div> 
	<div>
		<a href="#/update/{{readItem.knowledgeSeq}}" class="btn btn-primary">수정</a>
		<a href="javascript:void(1)" data-ng-click="remove(readItem)" class="btn btn-warning">삭제</a>
		<a href="#/list" class="btn btn-default">목록</a>					
	</div>
</div>