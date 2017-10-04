<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
        <meta charset="UTF-8" />
		<title><fmt:message key="forgot.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="forgotForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="FORGOT"/>
		</form>
		<form id="loginForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_LOGIN"/>
		</form>
		
		<div class="login-page">
			<div class="form">
					<c:choose>
						<c:when test = "${not empty msg}">
							<div class="group">
								<p class="b-message">${msg}</p>
							</div>
							<button form="loginForm" class="button" type="submit"><fmt:message key="forgot.button.back"/></button>
						</c:when>
						<c:otherwise>
							<div class="group">
								<label><fmt:message key="forgot.input.email"/></label>
								<fmt:message key="forgot.input.email.placeholder" var="val"/>
								<input form="forgotForm" type="email" name="login" value="${login}" placeholder="${val}" required>
							</div>
							<c:if test = "${not empty error_msg}">
								<p class="error-message">${error_msg}</p>
							</c:if>
							<button form="forgotForm" class="button" type="submit"><fmt:message key="forgot.button.send"/></button>
						</c:otherwise>
					</c:choose>
			</div>
		</div>
    </body>

</html>
