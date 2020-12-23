<%@page import="com.cos.hello.model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<h1>User Info</h1>
<%
// 	String result = (String)session.getAttribute("result");
	String result = (String)request.getAttribute("result");
	//여기 부분은 쓰지않음
%>
<%=result %>
<h1>${result}</h1> <!-- 적는게 좋음 -->
<h1>${requestScope.result}</h1> <!--request는 적지않는게 좋음-->
</body>
</html>