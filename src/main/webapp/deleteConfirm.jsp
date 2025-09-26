<%@ page import="com.training.todo.infrastructure.controller.model.TaskDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Delete</title>
    <link rel="stylesheet" type="text/css" href="../css/modal.css">
</head>

<body>
<div class="modal">
    <div class="warning-icon">⚠️</div>
    <h2>Confirm Delete</h2>
    <p>Are you sure you want to delete this item? This action cannot be undone.</p>


    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>
    <% if (errorMessage != null) { %>
    <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <%
        TaskDto task = (TaskDto) request.getAttribute("task");
        if (task != null) {
    %>

    <div class="task-info">
        <p><strong>Title:</strong> <%= task.getTitle() %></p>
        <p><strong>Description:</strong> <%= task.getDescription() %></p>
        <p><strong>Status:</strong> <%= task.getLabelDto().getName() %></p>
        <p><strong>Start Date:</strong> <%= task.getStartDate() %></p>
        <p><strong>Target Date:</strong> <%= task.getDueDate() %></p>
        <p><strong>Status:</strong> <%= task.getLabelDto().getName() %></p>
    </div>

    <div class="button-group">
        <form method="post" action="TodoList" style="display: inline;">
            <input type="hidden" name="action" value="confirmDelete">
            <input type="hidden" name="id" value="<%=task.getId()%>">
            <button type="submit" class="btn btn-danger">Yes, Delete</button>
        </form>
        <a href="TodoList" class="btn btn-secondary">Cancel</a>
    </div>

    <%
    } else {
    %>
    <p> Task not found not found.</p>
    <%
        }
    %>
</div>
</body>
</html>