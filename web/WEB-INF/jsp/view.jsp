<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <h3>Секции:</h3>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
    <h4><%=sectionEntry.getKey().getTitle()%>
    </h4>
    <c:choose>
        <c:when test="${sectionEntry.key eq SectionType.PERSONAL or sectionEntry.key eq SectionType.OBJECTIVE}">
            <%= ((TextSection) (sectionEntry.getValue())).getContent() %>
        </c:when>
        <c:when test="${sectionEntry.key eq SectionType.ACHIEVEMENT or sectionEntry.key eq SectionType.QUALIFICATIONS}">
            <ul>
                <c:forEach var="subsection" items="<%=((ListSection) (sectionEntry.getValue())).getContent()%>">
                    <li>${subsection}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            Если не одно условие не есть верно.
        </c:otherwise>
    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

