<%@page contentType="application; charset=UTF-8"%>
<jsp:useBean id="bMgr" class="test.BoardMgr" />
<%
	  bMgr.downLoad(request, response, out, pageContext);
%>