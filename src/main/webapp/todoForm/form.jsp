<%@ page import="com.training.todo.infrastructure.controller.model.TaskDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
</head>
<jsp:include page="../header.jsp"/>
<body>
<div class="form-container">
    <h1>New TODO</h1>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <%
        TaskDto taskDto = (TaskDto) request.getAttribute("taskDto");
        if (taskDto == null) {
    %>
        <jsp:include page="create.jsp"/>
    <% } else { %>
        <jsp:include page="update.jsp"/>
    <% } %>
</body>
</html>
