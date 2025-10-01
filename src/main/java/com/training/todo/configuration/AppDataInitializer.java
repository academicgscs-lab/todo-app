package com.training.todo.configuration;

import com.training.todo.core.usecases.*;
import com.training.todo.core.usecases.repository.ILabelRepository;
import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.infrastructure.data_providers.postgres_data_provider.PsqlLabelRepository;
import com.training.todo.infrastructure.data_providers.postgres_data_provider.PsqlTaskRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppDataInitializer implements ServletContextListener {
    private final static String HOME = String.format("%s/%s", "C:\\Users\\Santiago.Concha\\Documents\\tomcatDemoSample", "persistence");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        ITaskRepository taskRepository = new PsqlTaskRepository(
                "jdbc:postgresql://localhost:5432/devtodo",
                "postgres",
                "admin123"
        );
        ILabelRepository labelRepository = new PsqlLabelRepository(
                "jdbc:postgresql://localhost:5432/devtodo",
                "postgres",
                "admin123"
        );

        ICreateTaskUseCase createTaskUseCase = new DefaultCreateTaskUseCase(taskRepository, labelRepository);
        IUpdateTaskUseCase updateTaskUseCase = new DefaultUpdateTaskUseCase(taskRepository);
        IDeleteTaskUseCase deleteTaskUseCase = new DefaultDeleteTaskUseCase(taskRepository);
        ISearchTaskUseCase searchTaskUseCase = new DefaultSearchTaskUseCase(taskRepository);
        ISearchLabelUseCase searchLabelUseCase = new DefaultSearchLabelUseCase(labelRepository);

        context.setAttribute("createTaskUseCase", createTaskUseCase);
        context.setAttribute("updateTaskUseCase", updateTaskUseCase);
        context.setAttribute("deleteTaskUseCase", deleteTaskUseCase);
        context.setAttribute("searchTaskUseCase", searchTaskUseCase);

        context.setAttribute("searchLabelUseCase", searchLabelUseCase);
    }
}
