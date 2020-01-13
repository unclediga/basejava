package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String realPath = config.getServletContext().getRealPath("/config/resumes.properties");
        storage = Config.get(realPath).getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("<html>");
        writer.write("<body>");
        writer.write("<table border=\"1\" cellpadding=\"20\">");
        writer.write("<tr>");
        writer.write("<th>UUID</td>");
        writer.write("<th>Full Name</td>");
        writer.write("</tr>");
        for (Resume resume : storage.getAllSorted()) {
            writer.write("<tr>");
            writer.write("<td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td>");
            writer.write("</tr>");
        }
        writer.write("</table>");
        writer.write("</body>");
        writer.write("</html>");
        writer.flush();
        writer.close();
    }
}
