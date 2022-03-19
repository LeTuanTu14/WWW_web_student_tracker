<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
<div>
	<h3>Add Lop</h3>
	
	<form action="LopHocControllerServlet" method="get">
	<input type="hidden" name="command" value="ADD">
	
	<table>
	<tr>
		<td><label>Ma Lop:</label></td>
		<td><input type="text" name="ma_lop"></td>
	</tr>
	<tr>
		<td><label>Ten Lop:</label></td>
		<td><input type="text" name="ten_lop"></td>
	</tr>
	<tr>
		<td><label>Day Nha:</label></td>
		<td><input type="text" name="day_nha"></td>
	</tr>
	<tr>
		<td><label></label></td>
		<td><input type="submit" value="Save"></td>
	</tr>
	</table>
	<a href="/web_student_tracker/LopHocControllerServlet">Quay lai</a>
	</form>
</div>

</body>
</html>