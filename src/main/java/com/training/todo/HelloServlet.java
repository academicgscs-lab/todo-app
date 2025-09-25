package com.training.todo;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("message", message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("helloWorld.jsp");
        requestDispatcher.forward(request, response);
    }

    public void destroy() {
    }
}