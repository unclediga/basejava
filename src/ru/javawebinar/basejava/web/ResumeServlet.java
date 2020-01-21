package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String realPath = config.getServletContext().getRealPath("/config/resumes.properties");
        storage = Config.get(realPath).getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isNew = Objects.equals(request.getParameter("new"), "true");
        Resume resume = null;
        if (isNew) {
            resume = new Resume(uuid, fullName);
        } else {
            resume = storage.get(uuid);
        }
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(type, new ListSection((value.split("\\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<OrganizationSection.Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<OrganizationSection.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(
                                                new OrganizationSection.Position(
                                                        DateUtil.parse(startDates[j]),
                                                        DateUtil.parse(endDates[j]),
                                                        titles[j],
                                                        descriptions[j]));
                                    }
                                }
                                organizations.add(new OrganizationSection.Organization(name, urls[i], positions));
                            }
                        }
                        resume.setSection(type, new OrganizationSection(organizations));
                        break;
                }
            }
        }
        if (isNew) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : new SectionType[]{SectionType.PERSONAL, SectionType.OBJECTIVE}) {
                    if (resume.getSection(type) == null) {
                        resume.setSection(type, new TextSection(""));
                    }
                }
                for (SectionType type : new SectionType[]{SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS}) {
                    if (resume.getSection(type) == null) {
                        resume.setSection(type, new ListSection());
                    }
                }
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    OrganizationSection section = (OrganizationSection) resume.getSection(type);
                    ArrayList<OrganizationSection.Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(OrganizationSection.Organization.EMPTY);
                    if (section != null) {
                        for (OrganizationSection.Organization organization : section.getContent()) {
                            List<OrganizationSection.Position> positions = organization.getPositions();
                            List<OrganizationSection.Position> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(OrganizationSection.Position.EMPTY);
                            emptyFirstPositions.addAll(positions);
                            organization.setPositions(emptyFirstPositions);
                            emptyFirstOrganizations.add(organization);
                        }
                    }
                    resume.setSection(type, new OrganizationSection(emptyFirstOrganizations));
                }
                break;
            case "new":
                resume = new Resume("New Resume");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
