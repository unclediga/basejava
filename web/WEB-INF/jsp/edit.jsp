<%@ page import="ru.javawebinar.basejava.model.*" %>
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
        <input type="hidden" name="new" value="${param['action'] == 'new'}">
        <%-- ************* UUID | Ф И О  ************ --%>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <%-- *************  К О Н Т А К Т Ы  ************ --%>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <%-- *************  С Е К Ц И И  ************ --%>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h4>${type.title}</h4>
            <c:set var="curSection" value="${resume.getSection(type)}"/>
            <c:choose>
                <c:when test="${curSection != null}">
                    <jsp:useBean id="curSection"
                                 type="ru.javawebinar.basejava.model.AbstractSection"/>
                    <c:choose>
                        <c:when test="${type == 'PERSONAL' or type eq 'OBJECTIVE'}">
                            <input type="text" name="${type}" size=50
                                   value="<%= ((TextSection)(curSection)).getContent() %>"><br/>
                        </c:when>
                        <c:when test="${type == 'ACHIEVEMENT' or type == 'QUALIFICATIONS'}">
                            <textarea name="${type}" rows="5"
                                      cols="50"><%=String.join("\n", ((ListSection) (curSection)).getContent())%></textarea>
                        </c:when>
                        <c:otherwise>
                            Если не одно условие не есть верно.
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <textarea name="${type}" rows="5" cols="50"></textarea>
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

