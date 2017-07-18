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
	<form action="" method="post" enctype="multipart/form-data">
		<table border='2px'>
			<tr>
				<td>相簿名稱:</td>
				<td> <input type='text' value='${java.util.Date }' name='name'> </td>
			</tr>
			<tr>
				<td>選擇相片</td>
				<td><input type="file" class="form-control" id="upload" onchange="preview_images()" multiple></td>
			</tr>
			<tr>
			<td>
			<input type='hidden' name='mem_no' value='1001'>
			<input type='hidden' name='action' value='create_Album'>
			<input type='submit' value='submit' name='submit'>
			</td>
			
			</tr>
		</table>
	</form>
</body>
</html>