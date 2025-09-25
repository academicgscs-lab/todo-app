<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.training.todo.controller.model.TaskDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .task-list { display: flex; flex-wrap: wrap; gap: 20px; }
        .todo-card { border: 1px solid #ccc; border-radius: 8px; padding: 15px; width: 250px; box-shadow: 2px 2px 8px rgba(0,0,0,0.1); }
        .todo-card h3 { margin-top: 0; }
        .todo-card p { margin: 5px 0; }
    </style>
</head>
<body>
<h1>Todo App</h1>
<div class="task-list">
    <%
        // Retrieve the movie list from the request attribute
        List<TaskDto> taskList = (List<TaskDto>) request.getAttribute("taskList");

        // Check if the list is not null and not empty
        if (taskList != null && !taskList.isEmpty()) {
            // Loop through each movie in the list
            for (TaskDto task : taskList) {
    %>
    <div class="todo-card">
        <h3><%= task.getTitle() %></h3>
        <p><strong>Description:</strong> <%= task.getDescription() %></p>
        <p><strong>Status:</strong> <%= task.getStatus() %></p>
        <p><strong>Start Date:</strong> <%= task.getStartDate() %></p>
        <p><strong>Target Date:</strong> <%= task.getDueDate() %></p>
    </div>
    <%
        } // End of for loop
    } else {
    %>
    <p>No movies available at the moment.</p>
    <%
        } // End of if-else
    %>
</div>
</body>
</html>
