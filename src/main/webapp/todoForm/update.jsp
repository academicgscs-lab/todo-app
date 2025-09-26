<%@ page import="com.training.todo.infrastructure.controller.model.TaskDto" %>
<%@ page import="com.training.todo.infrastructure.controller.model.LabelDto" %>
<%@ page import="java.util.Collection" %>
<%
    TaskDto taskDto = (TaskDto) request.getAttribute("taskDto");
%>

<form action="new" method="post">
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

    <button type="submit">Save</button>
</form>