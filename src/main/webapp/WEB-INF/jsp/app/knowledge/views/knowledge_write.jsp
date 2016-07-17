<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div>
	<div class="col-lg-12" style="padding-top: 30px;">
		<div class="well bs-component">
			<form class="form-horizontal ng-pristine ng-valid ng-valid-required" name="writeForm">
				<fieldset>
					<legend>쓰기</legend>
					<div class="form-group"> 
						<label for="title" class="col-lg-1 control-label">분야</label>
						<div class="col-lg-11">
							<select class="form-control" name="classifyC" data-ng-model="readItem.classifyC">
								<option data-ng-repeat="cate in category" value="{{cate.minorCode}}">
									{{cate.codeValue}}
								</option>
							</select>  
						</div>
					</div>									
					<div class="form-group">
						<label for="problemText" class="col-lg-1 control-label">문제</label>
						<div class="col-lg-11">
							<textarea id="problemText" rows="10" cols="100" data-ng-model="readItem.problem" style="width: 100%; height: 200px; display: none;"></textarea>
						</div>
					</div>									
					<div class="form-group">
						<label for="solutionText" class="col-lg-1 control-label">해결</label>
						<div class="col-lg-11">
							<textarea id="solutionText" rows="10" cols="100" data-ng-model="readItem.solution" style="width: 100%; height: 200px; display: none;"></textarea>
						</div>
					</div>										
					<div class="form-group">
						<div class="col-lg-12">
							<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
							<button type="submit" class="btn btn-primary" data-ng-click="writeOrUpdateKnowledgeSummit()" data-ng-disabled="writeForm.title.$invalid">쓰기</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>