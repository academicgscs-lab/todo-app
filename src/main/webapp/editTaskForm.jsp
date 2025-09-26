<%@ page import="java.util.Collection" %>
<%@ page import="com.training.todo.infrastructure.controller.model.LabelDto" %>
<%@ page import="com.training.todo.infrastructure.controller.model.TaskDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
</head>

<jsp:include page="header.jsp"></jsp:include>
<body>
<div class="form-container">
    <h1>Edit TODO</h1>

    <%-- Display an error message if it exists in the request --%>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>
    <% if (errorMessage != null) { %>
    <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <%
        TaskDto taskDto = (TaskDto) request.getAttribute("taskDto");
        if (taskDto != null) {
    %>

    <form action="edit" method="post">
        <input type="hidden" name="id" value="<%= taskDto.getId() %>">

        <label for="title"><b>Title:</b></label>
        <input type="text" id="title" name="title" value="<%= taskDto.getTitle() %>" required>

        <label for="description"><b>Description:</b></label>
        <input type="text" id="description" name="description" value="<%= taskDto.getDescription() %>" required>

        <label for="startDate"><b>Start date:</b></label>
        <input type="date" id="startDate" name="startDate" value="<%= taskDto.getStartDate() %>"  required>

        <label for="dueDate"><b>Target date:</b></label>
        <input type="date" id="dueDate" name="dueDate" value="<%= taskDto.getDueDate() %>" required>

        <label for="statusId"><b>Status:</b></label>
        <select id="statusId" name="statusId" required>
            <%
                Collection<LabelDto> labelList = (Collection<LabelDto>) request.getAttribute("labelList");
                if (labelList != null) {
                    for (LabelDto item : labelList) {
                        String selected = (item.getId().equals(taskDto.getLabelDto().getId())) ? "selected" : "";
            %>
            <option
                    value="<%= item.getId() %>" <%= selected %>><%= item.getName() %>
            </option>
            <%
                    } // end for
                } // end if
            %>
        </select>

        <button type="submit">Update product</button>
    </form>
    <%
    } else {
    %>
    <p> Task not found.</p>
    <%
        }
    %>
</div>
</body>
</html>
