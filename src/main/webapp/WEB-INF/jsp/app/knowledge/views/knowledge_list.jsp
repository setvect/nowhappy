<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div>
	<div class="panel panel-default">
		<form class="navbar-form navbar-left" role="search">
			<div class="form-group">
				<select class="form-control">
					<option data-ng-repeat="cate in category" value="{{cate.minorCode}}">
						{{cate.codeValue}}
					</option>
				</select> 
				<input type="text" class="form-control" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">검색</button>
		</form>
	
		<!-- Table -->
		<table class="table">
			<thead>
				<tr>
					<th class="col-md-4">문제</th>
					<th class="col-md-3">해결</th>
					<th class="col-md-1 center">분류</th>
					<th class="col-md-1 center">조회</th>
					<th class="col-md-1 center">날짜</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="col-md-4">몹시 아픈 후 드는 생각</td>
					<td class="col-md-3">몹시 아픈 후 드는 생각</td>
					<td class="col-md-1 center">자바</td>
					<td class="col-md-1 center">11</td>
					<td class="col-md-1 center">2014-11-16</td>
				</tr>
				<tr>
					<td class="col-md-4">몹시 아픈 후 드는 생각</td>
					<td class="col-md-3">몹시 아픈 후 드는 생각</td>
					<td class="col-md-1 center">OS</td>
					<td class="col-md-1 center">11</td>
					<td class="col-md-1 center">2014-11-16</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="text-center">
		<ul class="pagination pagination-sm">
			<li><a href="#">«</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">»</a></li>
		</ul>
	</div>
</div>