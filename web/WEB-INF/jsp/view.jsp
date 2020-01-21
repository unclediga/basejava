<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>

    <c:forEach var="sectionEntry" items="${resume.sections}" varStatus="status">
        <c:if test="${status.first}">
            <h2>Секции:</h2>
        </c:if>
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section"
                     type="ru.javawebinar.basejava.model.AbstractSection"/>
        <h2>${type.title}</h2>
        <c:choose>
            <c:when test="${type == SectionType.OBJECTIVE}">
                <h3><%= ((TextSection) (section)).getContent()%>
                </h3>
            </c:when>
            <c:when test="${type == SectionType.PERSONAL}">
                <%= ((TextSection) (section)).getContent()%>
            </c:when>
            <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                <ul>
                    <c:forEach var="subsection" items="<%=((ListSection) (section)).getContent()%>">
                        <li>${subsection}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                <c:forEach var="org" items="<%=((OrganizationSection) section).getContent()%>">
                    <c:choose>
                        <c:when test="${empty org.link.homePage}">
                            <h3>${org.link.title}</h3>
                        </c:when>
                        <c:otherwise>
                            <h3><a href="${org.link.homePage}">${org.link.title}</a></h3>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="position" items="${org.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.OrganizationSection.Position"/>
                        <br/>
                        <%=HtmlUtil.formatDates(position)%>
                        <b>${position.title}</b>
                        <br/>
                        ${position.description}
                    </c:forEach>
                </c:forEach>
            </c:when>
            <c:otherwise>
                Если не одно условие не есть верно.
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

