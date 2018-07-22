<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<title>Registration</title>

</head>
<body>
	<a class="btn btn-primary btn-lg" href="home" role="button">Home</a>
	<div class="container">
		<h2 align="center">Add New User</h2>

		<form:form id="regForm" modelAttribute="user" action="registerProcess"
			method="post" class="form-horizontal">


			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="username">USER NAME</form:label>
				<div class="col-sm-10">
					<form:input path="username" name="username" id="username" required="required"/>
				</div>
			</div>

			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="password">PASSWORD</form:label>
				<div class="col-sm-10">
					<form:password path="password" name="password" id="password" required="required"/>
				</div>
			</div>

			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="email">EMAIL</form:label>
				<div class="col-sm-10">
					<form:input type="email" path="email" placeholder='example@email.com' required="required"/>
				
					<%-- <form:input path="email" name="email" id="email" required="required"/> --%>
				</div>
			</div>

			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="access">ACCESS</form:label>
				<div class="col-sm-10">
					<form:select path="access" items="${accessList}" />
				</div>
			</div>

			<div class="form-group" align="center">
				<div class="col-sm-10">
					<form:button id="register" class="btn btn-success btn-send"
						name="register">Register</form:button>
				</div>
			</div>

		</form:form>
	</div>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>