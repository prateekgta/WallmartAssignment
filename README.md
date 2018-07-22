Maven - Spring 3 WallMart Assignment
===============================
System Description
Today’s Replenishment manager is required to complete a variety of tasks, working with many people to
ensure optimal product movement through the supply chain.
The goal of this challenge to create an application which allows experienced business users to curate a
list of recurring tasks for individual contributors to execute. 
Individuals should also be able to create
independent tasks to be added to their personalized task list. Finally, specialized tasks may also be
created by external processes via service calls.
In addition to providing this app to drive task execution, we also intend to utilize metrics captured to
drive future enhancements and features, including potential automation.

Features of app include:
1) Display a sorted list of pending tasks
2) Sorted by first Status, second Rank
3) Rank should use an algorithm which weighs inputted priority against time estimated to
perform the task
4) User should have ability to track the status for an individual task
5) This status will be used to drive other features of app
6) Track time in each status after “Started” until “Finished”
7) Recurring tasks should be duplicated based on recurrence frequency/schedule
8) Be extensible


###1. Technologies used
* Maven 3
* Spring 3.2.13.RELEASE
* MySql 5
* ApacheTomat Server 7
* Java 6

###2. To Run this project locally
```shell
$ git clone https://github.com/prateekgta/WallmartAssignment
```
Access ```http://localhost:8080/WallmartAssignment```

###3. To import this project into Eclipse IDE
1. ```$ mvn eclipse:eclipse```
2. Import into Eclipse via **existing projects into workspace** option.
3. Done.

###4. SQL queries
1. Sql queries are included in the resource path, import the file in your database.

###5. WebServices
Project support jersey webservices

URL to get User list of Task
MethodType : GET
1.http://localhost:8080/WallmartAssignment/rest/task/getTaskList/{userID}
Here userID is the user you created.
Eg http://localhost:8080/WallmartAssignment/rest/task/getTaskList/962

URL to add user task
MethodType : POST
2.  http://localhost:8080/WallmartAssignment/rest/task/create/{userID}/{taskData}
Here userId is the user you created and task data is the task you want to insert.
Eg : http://localhost:8080/WallmartAssignment/rest/task/create/962/mytaskdatafor testing

By default, task rank will be least and task status will be submitted.

Please check screenshot of POSTMAN and JerseyClientGet and JerseyClientPost files [copy the file in test environment and run] in resources.

###6. Credentials

Admin Credentials

username: prateek
password: prateek

Admin is authorized to create new admin and new user. He can assign task to any admin and user. He can create the task for himself. He can update or modify the task and delete.



User Credentials

username: user
password: user

User can create a task for himself. He will also see the task created by admin. he can update and delete that task.

** Some of the assumptions are used in this project. Every user should remember their userId which is unique to use the web services **


###7. Contact Details
prateekucer@gmail.com

