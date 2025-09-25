package com.training.todo.controller.servlet;

import com.training.todo.application.TaskManager;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.controller.service.TaskService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/TodoList")
public class TaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        TaskManager taskManager = (TaskManager) context.getAttribute("taskManager");
        List<TaskDto> taskList = taskManager.getValues().stream().map(TaskService::mapToDto).toList();
        req.setAttribute("taskList", taskList);
        req.getRequestDispatcher("todoList.jsp").forward(req, resp);
    }
}
