<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
        <meta charset="UTF-8" />
		<title>Error 404</title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<div class="login-page">
			<div class="form">
				<p class="b-error-message"><fmt:message key="error.404"/></p>
			</div>
		</div>
    </body>

</html>
