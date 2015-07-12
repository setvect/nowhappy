<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="well bs-component">
	<form class="form-horizontal" name="writeForm">
		<input type="hidden" name="categorySeq" data-ng-model="readItem.categorySeq" required>
		<fieldset>
			<legend>쓰기</legend>
			<div class="form-group">
				<div class="col-lg-12">
					<input type="text" class="form-control" name="title" id="title" data-ng-model="readItem.title" required>
					<span ng-show="writeForm.title.$dirty && writeForm.title.$invalid">제목 써라</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<textarea id="content" rows="10" cols="100" style="width: 100%; height: 350px; display: none;" data-ng-model="readItem.content"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<input type="file" class="form-control" file-model="readItem.attachFile[0]"> 
					<input type="file" class="form-control" file-model="readItem.attachFile[1]">
					<ul>
						<li data-ng-repeat="f in attachMapList[readItem.noteSeq] track by $index">
							{{f.originalName}}  <input type="checkbox" value="{{f.attachFileSeq}}" name="deleteattachFileSeq"/>삭제
						</li>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
					<button type="submit" class="btn btn-primary" data-ng-click="writeOrUpdateNoteSummit()" data-ng-disabled="writeForm.title.$invalid">쓰기</button>
					<span>자동저장...</span>
				</div>
			</div>
		</fieldset>
	</form>
</div>