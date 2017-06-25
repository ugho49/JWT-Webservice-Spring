<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Demo Webservice</title>

    <c:url var="css_bundle" value="/static/dist/css/bundle.css"/>
    <link rel="stylesheet" href="${css_bundle}">
</head>
<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Hello World</h1>
        </div>
    </div>

    <c:url var="js_bundle" value="/static/dist/js/bundle.js"/>
    <script src="${js_bundle}" type="application/javascript"></script>
</body>
</html>