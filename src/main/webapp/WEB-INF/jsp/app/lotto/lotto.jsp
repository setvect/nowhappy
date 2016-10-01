<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebCommon"%>
<%@page import="com.setvect.nowhappy.ApplicationConstant.WebAttributeKey"%>
<%
	List<Set<Integer>> luckList = (List<Set<Integer>>)request.getAttribute(WebAttributeKey.LOTTO);
%>
<div class="panel panel-default">
	<h5>내 운명을  바꿀 숫자</h5>
	<table class="table">
		<tbody>
<%
	for(Set<Integer> luckSet : luckList){
%>
			<tr>
<%
		for(Integer luckNum : luckSet){
%>
				<td class="col-md-2 date"><%=luckNum %></td>
<%		
		}
%>		
			</tr>
<%		
	}
%>		
		</tbody>
	</table>
</div>