<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=utf-8" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title><fmt:message key="login.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="loginForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="LOGIN"/>
		</form>
		<form id="forgotForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_FORGOT"/>
		</form>
		<form id="signupForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_SIGNUP"/>
		</form>
		
		<div class="language">
			<form action ="controller" method=POST>
				<input type="hidden" name="TypeCommand" value="LOCALIZATION"/>
				<select id="language" name="language" onchange="submit()">
					<option value="en" ${language == 'en' ? 'selected' : ''}>ENG</option>
					<option value="ru" ${language == 'ru' ? 'selected' : ''}>РУС</option>
				</select>
			</form>
		</div>
		
		<div class="login-page">
			<div class="form">
				
		
				<div class="group">
					<label><fmt:message key="login.input.email"/></label>
					<fmt:message key="login.input.email.placeholder" var="val"/>
					<input form="loginForm" type="email" name="login" value="${login}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="login.input.password"/></label>
					<fmt:message key="login.input.password.placeholder" var="val"/>
					<input form="loginForm" type="password" name="password" value="${password}" placeholder="${val}" required>
					<div class="forgot">
						<button form="forgotForm" class="btn-link" type="submit"><fmt:message key="login.button.password.forgot"/></button>
					</div>
				</div>
				
				<div class="group">
					<input form="loginForm" type="checkbox" class="check" name="rememberMe" id="rememberMe" value="remember me">
					<label for="rememberMe"><span class="icon"></span> <fmt:message key="login.checkbox.remember"/></label>
				</div>
				
				<c:if test = "${not empty error_msg}">
					<p class="error-message">${error_msg}</p>
				</c:if>

				<button form="loginForm" class="button" type="submit"><fmt:message key="login.button.signin"/></button>
				<p class="message"><fmt:message key="login.text.notregistered"/>
					<button form="signupForm" class="btn-link" type="submit"> <fmt:message key="login.button.signup"/></button>
				</p>
			</div>
		</div>
    </body>

</html>
