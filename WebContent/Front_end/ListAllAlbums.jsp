<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>

	<jsp:useBean id="albSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
	<form action="<%= request.getContextPath() %>/album/album.do" method="post"></form>
	<table>
		<c:forEach var="albVO" items="${albSvc.all }" >
			<tr>
				<td>${albVO.name }</td>
				<td><img src='/BA102G2/ShowPictureServletDAO?alb_no=${albVO.alb_no }' width="200"><td>
				<td><c:out value="${albVO.alb_no }"></c:out></td>
				<td><input type="checkbox" name="alb_no" value="${albVO.alb_no }"></td>
				<input type="hidden" name="action" value="delete_Album">
				
			</tr>
			
		</c:forEach>
		<tr>
			<td>
			<input type="submit" value="刪除相簿" >
			</td>
		</tr>	
	</table>
</body>
</html>