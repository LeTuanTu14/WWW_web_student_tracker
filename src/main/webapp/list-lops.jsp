
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Lop Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">

 </head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Lop -->
			
			<input type="button" value="Add Lop Hoc" 
				   onclick="window.location.href='add-lop-form.jsp'; return false;"
				   class="add-student-button"
			/>
			
			<table>
			
				<tr>
					<th>Ma Lop</th>
					<th>Ten Lop</th>
					<th>Day Nha</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempLop" items="${lop_list}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="LopHocControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="ma_lop" value="${tempLop.maLop}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="LopHocControllerServlet">
						<c:param name="command" value="Student" />
						<c:param name="ma_lop" value="${tempLop.maLop}" />
					</c:url>																		
					<tr>
						<td> ${tempLop.maLop} </td>
						<td> ${tempLop.tenLop} </td>
						<td> ${tempLop.dayNha} </td>
						<td> 
							<a href="${tempLink}">Update</a> 							 | 
							<a href="${deleteLink}"
							>
							Student</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>
</html>








