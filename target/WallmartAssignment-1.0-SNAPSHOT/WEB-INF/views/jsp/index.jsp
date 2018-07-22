<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>User Login Form</title>
</head>

<body>
	<div class="container">
		<h2 align="center">${msg}</h2>
<form:form id="regindexform" modelAttribute="loginBean" action="indexProcessing"
			method="post" class="form-horizontal">
		<%-- <form:form name="submitForm" method="POST" class="form-horizontal"> --%>

			<div class="form-group" align="center">
				<label class="control-label col-sm-2">User Name</label>
				<div class="col-sm-10">
					<input type="text" name="userName" required="required"/>
				</div>
			</div>

			<div class="form-group" align="center">
				<label class="control-label col-sm-2">Password</label>
				<div class="col-sm-10">
					<input type="password" name="password" required="required"/>
				</div>
			</div>

			<div class="form-group" align="center">
				<div class="col-sm-10">

					<input type="submit" value="Submit" />
				</div>
			</div>

			<div align="center">
				<div style="color: red">${error}</div>
			</div>
		</form:form>
		<footer>
			<p>© Wallmart.com 2018</p>
		</footer>
	</div>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>