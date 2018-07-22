<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>List Of Task Created</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
  <a class="btn btn-primary btn-lg" href="home" role="button">Home</a>
	<div class="container" align="center">
		<div>
			<table class="table">
			<caption>
					<h2 align="center">Task List</h2>
				</caption>	
				<tr>
					<th scope="col">Created By User</th>
					<th scope="col">Created For User</th>
					<th scope="col">Task</th>
					<th scope="col">Status</th>
					<th scope="col">Rank</th>
					<th scope="col">Created Time</th>
					<th scope="col">Last Modified Time</th>
					<th scope="col">Update task</th>
					<th scope="col">Delete</th>
				</tr>
				<c:forEach items="${listofTask}" var="task">
					<tr>
						<td><c:out value="${task.createdUserId}" /></td>
						<td><c:out value="${task.userId}" /></td>
						<td><c:out value="${task.task}" /></td>
						<td><c:out value="${task.status}" /></td>
						<td><c:out value="${task.rank}" /></td>
						<td><c:out value="${task.createdDate}" /></td>
						<td><c:out value="${task.lastModifiedDate}" /></td>
						<td><a href="updateTask/${task.taskId}">Update task</a></td>
						<td><a href="deleteTask/${task.taskId}">Delete task</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>


	</div>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>