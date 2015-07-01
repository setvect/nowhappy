<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<div class="well bs-component">
		<form class="form-horizontal" name="writeForm">
			<fieldset>
				<legend>암호 문자열</legend>
				<div class="form-group">
					<label for="encode" class="col-lg-2 control-label">암호 문자열</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" name="encode" id="encode" required>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-2">
						<button type="submit" class="btn btn-default" data-ng-click="listback()">취소</button>
						<button type="submit" class="btn btn-default" data-ng-click="encodeRead()">확인</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
