<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Курс JavaSE + Web.</title>
</head>
<body>
<table border="1" cellpadding="20" cellspacing="0">
    <tr>
        <th>Имя</th>
        <th>Contact</th>
    </tr>
    <%
        for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
    %>
    <tr>
        <td><a href="<%=resume.getUuid()%>"><%=resume.getFullName()%>
        </a></td>
        <td><%=resume.getContact(ContactType.EMAIL)%>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
