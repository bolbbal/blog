<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@page import="domain.MemberVo"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 	아이디:<%=request.getAttribute("id")%><br>
	비밀번호:<%=request.getAttribute("pw") %><br>
	기술스펙:
	<%	
		String[] language = (String[])request.getAttribute("language");
		for(String lang : language) {
			out.print(lang + " ");
		}
	%><br>
	동의여부:
	<%
		String answer = (String)request.getAttribute("answer");
		if(answer.equals("yes")) {
			out.println("동의");
		} else {
			out.println("비동의");
		}
	%> --%>
	<%-- <%
		MemberVo vo = (MemberVo)request.getAttribute("mvo");
	%>
	아이디:<%=vo.getId() %><br>
	비밀번호:<%=vo.getPw() %><br>
	기술스펙:
	<%
		String[] language = (String[])vo.getLanguage();
		for(String lang : language) {%>
	<%=lang %>
	<%	}%>
	<br>
	동의여부:
	<%
		if(vo.getAnswer().equals("yes")) {%>
			<span>동의</span>	
	<% 	} else {%>
			<span>비동의</span>
	<% 	} %> --%>
	
	<!-- jstl 라이브러리와 표현식 -->
	아이디:${mvo.getId()}<br>
	패스워드:${mvo.getPw() }<br>
	기술스펙:
	<c:forEach var="lang" items="${mvo.getLanguage()}">
		${lang} &nbsp;
	</c:forEach><br>
	동의여부:
	<c:if test="${mvo.getAnswer() == 'yes' }">
		<span>동의</span>
	</c:if>
	<c:if test="${mvo.getAnswer().equals('no') }">
		<span>비동의</span>
	</c:if>
	<c:choose>
		<c:when test="${mvo.getAnswer() == 'yes' }">
			<span>동의</span>
		</c:when>
		<c:when test="${mvo.getAnswer().equals('no')}">
			<p>비동의</p>
		</c:when>
		<c:otherwise>
			<p>선택안함</p>
		</c:otherwise>
	</c:choose>
	<c:set var="result" value="${mvo.getAnswer() }"/>
	<p>${result}</p>
</body>
</html>