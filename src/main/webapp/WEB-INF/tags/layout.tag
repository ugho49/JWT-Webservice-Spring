<%@tag description="Layout Template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="title" required="false" %>
<%@attribute name="css" fragment="true" required="false" %>
<%@attribute name="js" fragment="true" required="false" %>

<c:url var="dist_path" value="/static/dist"/>
<c:url var="css_path" value="/static/css"/>
<c:url var="js_path" value="/static/js"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <c:if test="${empty title}">
        <c:set var="title" value="Demo Webservice" />
    </c:if>

    <title>${title}</title>

    <%-- Styles --%>
    <link rel="stylesheet" href="${dist_path}/css/bundle.css">
    <link rel="stylesheet" href="${css_path}/app.css">

    <%-- Other styles --%>
    <jsp:invoke fragment="css"/>

</head>
<body>

    <jsp:include page="partials/header.jsp" />
    <jsp:doBody />
    <jsp:include page="partials/footer.jsp" />

    <%-- Scripts --%>
    <script src="${dist_path}/js/bundle.js" type="application/javascript"></script>
    <script src="${js_path}/app.js" type="application/javascript"></script>

    <%-- Other scripts --%>
    <jsp:invoke fragment="js"/>

</body>
</html>
