<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="well bs-component">
	<form class="form-horizontal" name="writeForm">
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
					<textarea id="content" rows="10" cols="100" style="width: 100%; height: 300px; display: none;" data-ng-model="readItem.content"></textarea>
					<input onclick="$.APP.openImageUpload();" type="button" value="이미지 첨부"/>						
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12">
					<input type="file" class="form-control" file-model="readItem.attachFile[0]"> 
					<input type="file" class="form-control" file-model="readItem.attachFile[1]">
					<input type="file" class="form-control" file-model="readItem.attachFile[2]">
					<ul>
						<li data-ng-repeat="f in attachMapList[readItem.articleSeq] track by $index">
							{{f.originalName}}  <input type="checkbox" value="{{f.attachFileSeq}}" name="deleteattachFileSeq"/>삭제
						</li>
					</ul>
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-12 col-lg-offset-2">
					<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
					<button type="submit" class="btn btn-primary" data-ng-click="writeOrUpdateSummit()" data-ng-disabled="writeForm.title.$invalid">쓰기</button>
				</div>
			</div>
		</fieldset>
	</form>
</div>