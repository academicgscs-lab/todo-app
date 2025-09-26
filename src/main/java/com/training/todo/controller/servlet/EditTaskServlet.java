package com.training.todo.controller.servlet;

import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.controller.model.LabelDto;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.controller.service.LabelService;
import com.training.todo.controller.service.TaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/TodoList/edit")
public class EditTaskServlet extends HttpServlet {

    private TaskService taskService;
    private LabelService labelService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = getServletContext();
        labelService = (LabelService) context.getAttribute("labelService");
        taskService = (TaskService) context.getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("labelList", labelService.getLabels());
        String id = req.getParameter("id");
        req.setAttribute("taskDto", taskService.getTask(id));
        req.getRequestDispatcher("/editTaskForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskDto dto = mapToDto(req);
        try {
            taskService.updateTask(dto);
            resp.sendRedirect(req.getContextPath());
        } catch (InvalidTaskException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/editTaskForm.jsp").forward(req, resp);
        }
    }

    public TaskDto mapToDto(HttpServletRequest req) {
        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
        LocalDate dueDate = LocalDate.parse(req.getParameter("dueDate"));
        LabelDto label = labelService.getLabel(req.getParameter("statusId"));

        return TaskDto.builder()
                .id(req.getParameter("id"))
                .title(req.getParameter("title"))
                .description(req.getParameter("description"))
                .labelDto(label)
                .startDate(startDate)
                .dueDate(dueDate)
                .build();
    }
}
