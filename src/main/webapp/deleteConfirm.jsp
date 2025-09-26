<%@ page import="com.training.todo.controller.model.TaskDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Delete</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .modal {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
            max-width: 500px;
            width: 90%;
        }
        .modal h2 {
            color: #dc3545;
            margin-top: 0;
        }
        .task-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            margin: 20px 0;
        }
        .task-info p {
            margin: 5px 0;
        }
        .btn {
            padding: 10px 20px;
            margin: 0 10px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .button-group {
            text-align: center;
            margin-top: 20px;
        }
        .warning-icon {
            color: #dc3545;
            font-size: 48px;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
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