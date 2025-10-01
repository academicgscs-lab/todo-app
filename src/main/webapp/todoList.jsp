<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.training.todo.infrastructure.controller.model.TaskDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div>
    <a href="TodoList/new" class="add-button">Add +</a>
</div>

<div class="task-list">
    <%
        List<TaskDto> taskList = (List<TaskDto>) request.getAttribute("taskList");
        if (taskList != null && !taskList.isEmpty()) {
            for (TaskDto task : taskList) {
    %>
    <div class="todo-card">
        <h3><%= task.getTitle() %></h3>
        <p><strong>Description:</strong> <%= task.getDescription() %></p>
        <p><strong>Status:</strong> <%= task.getLabelDto().getTitle() %></p>
        <p><strong>Start Date:</strong> <%= task.getStartDate() %></p>
        <p><strong>Target Date:</strong> <%= task.getDueDate() %></p>

        <form action="TodoList/edit" method="get">
            <input type="hidden" name="id" value="<%= task.getId() %>">
            <button type="submit">Edit</button>
        </form>

        <a href="TodoList?action=delete&id=<%= task.getId() %>" class="btn btn-danger">Delete</a>

    </div>
    <%
        } // End of for loop
    } else {
    %>
    <p>NO AVAILABLE TODOs</p>
    <%
        } // End of if-else
    %>
</div>
</body>
</html>
