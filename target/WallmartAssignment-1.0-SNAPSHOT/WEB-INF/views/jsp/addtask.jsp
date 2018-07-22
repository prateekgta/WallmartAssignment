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
		<h2 align="center">Add Task For User</h2>
		
		<form:form id="taskForm" modelAttribute="task" action="addTaskProcess"
			method="post" class="form-horizontal">

			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="task">TASK</form:label>

				<div class="col-sm-10">
					<form:textarea class="form-control" path="task" rows="4" cols="80" required="required"/>
				</div>
			</div>

			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="status">STATUS</form:label>
				<div class="col-sm-10">
					<form:select class="form-control" path="status"
						items="${statusList}" />
				</div>
			</div>
			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="userId">USER ID</form:label>
				<div class="col-sm-10">
					<form:select class="form-control" path="userId" items="${userList}" />
				</div>
			</div>
			<div class="form-group" align="center">
				<form:label class="control-label col-sm-2" path="rank">RANK</form:label>
				<div class="col-sm-10">
					<form:select class="form-control" path="rank" items="${rankList}" />
				</div>
			</div>

			<div class="form-group" align="center">
				<div class="col-sm-10">
					<form:button id="addtask" name="addtask"
						class="btn btn-success btn-send">CREATE TASK</form:button>
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