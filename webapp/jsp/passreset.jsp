<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title><fmt:message key="password.reset.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="resetForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="PASS_RESET"/>
			<input type="hidden" name="login" value="${login}"/>
			<input type="hidden" name="passRecoveryKey" value="${passRecoveryKey}"/>
		</form>
		<form id="loginForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_LOGIN"/>
		</form>
		
		<div class="login-page">
			<div class="form">
					<div class="group">
						<p class="b-message">${login}</p>
					</div>
					<c:choose>
						<c:when test = "${not empty msg}">
							<div class="group">
								<p class="b-message">${msg}</p>
							</div>
							<button form="loginForm" class="button" type="submit"><fmt:message key="password.reset.button.back"/></button>
						</c:when>
						<c:otherwise>
							<div class="group">
								<label><fmt:message key="password.reset.input.password"/>*</label>
								<fmt:message key="password.reset.input.password.placeholder" var="val"/>
								<input form="resetForm" type="password" name="password" value="${password}" placeholder="${val}" required>
							</div>
							
							<div class="group">
								<label><fmt:message key="password.reset.input.password.repeat"/>*</label>
								<fmt:message key="password.reset.input.password.repeat.placeholder" var="val"/>
								<input form="resetForm" type="password" name="rPassword" value="${rPassword}" placeholder="${val}" required>
							</div>
							<c:if test = "${not empty error_msg}">
								<p class="error-message">${error_msg}</p>
							</c:if>
							<button form="resetForm" class="button" type="submit"><fmt:message key="password.reset.button.apply"/></button>
						</c:otherwise>
					</c:choose>
			</div>
		</div>
    </body>

</html>
