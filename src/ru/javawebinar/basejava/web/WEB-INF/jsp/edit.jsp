<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <h4><%=sectionEntry.getKey().getTitle()%></h4>
            <c:choose>
                <c:when test="${sectionEntry.key eq SectionType.PERSONAL or sectionEntry.key eq SectionType.OBJECTIVE}">
                    <input type="text" name="s_${sectionEntry.key}" size=50 value="<%= ((TextSection)(sectionEntry.getValue())).getContent() %>"><br/>
                </c:when>
                <c:when test="${sectionEntry.key eq SectionType.ACHIEVEMENT or sectionEntry.key eq SectionType.QUALIFICATIONS}">
                    <ul>
                        <%
                            pageContext.setAttribute("listSection",(ListSection)(sectionEntry.getValue()));
                        %>
                        <c:forEach var="subsection" items="${listSection.content}">
                            <jsp:useBean id="subsection" type="java.lang.String"/>
                            <input type="text" name="section:${sectionEntry.key.name()}" size=50 value="${subsection}"><br/>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    Если не одно условие не есть верно.
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

