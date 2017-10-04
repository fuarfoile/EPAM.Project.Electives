<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
        <meta charset="UTF-8" />
		<title><fmt:message key="signup.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="loginForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_LOGIN"/>
		</form>
		<form id="signupForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="SIGNUP"/>
		</form>
		
		<div class="login-page">
			<div class="form">
				<div class="group">
					<label><fmt:message key="signup.input.email"/>*</label>
					<fmt:message key="signup.input.email.placeholder" var="val"/>
					<input form="signupForm" type="email" name="login" value="${login}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.password"/>*</label>
					<fmt:message key="signup.input.password.placeholder" var="val"/>
					<input form="signupForm" type="password" name="password" value="${password}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.password.repeat"/>*</label>
					<fmt:message key="signup.input.password.repeat.placeholder" var="val"/>
					<input form="signupForm" type="password" name="rPassword" value="${rPassword}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.name"/>*</label>
					<fmt:message key="signup.input.name.placeholder" var="val"/>
					<input form="signupForm" type="text" name="name" value="${name}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.surname"/>*</label>
					<fmt:message key="signup.input.surname.placeholder" var="val"/>
					<input form="signupForm" type="text" name="surname" value="${surname}" placeholder="${val}" required>
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.phone.number"/></label>
					<fmt:message key="signup.input.phone.number.placeholder" var="val"/>
					<input form="signupForm" type="text" name="phoneNumber" value="${phoneNumber}" placeholder="${val}">
				</div>
				
				<div class="group">
					<label><fmt:message key="signup.input.position"/>*</label>
					<select form="signupForm" name="position" required>
					  <c:forEach items="${positions}" var="pos">
						<option value="${pos}" ${position == pos ? 'selected' : ''}>
							${pos.getPosition(language)}
						</option>
					  </c:forEach>
					</select>
				</div>
				
				<div class="group">
					<input form="signupForm" type="checkbox" class="check" name="rememberMe" id="rememberMe" value="true">
					<label for="rememberMe"><span class="icon"></span> <fmt:message key="signup.checkbox.remember"/></label>
				</div>
				
				<c:if test = "${not empty error_msg}">
					<pre class="error-message">${error_msg}</pre>
				</c:if>

				<button form="signupForm" class="button" type="submit"><fmt:message key="signup.button.signup"/></button>
				<p class="message"><fmt:message key="signup.text.registered"/>
					<button form="loginForm" class="btn-link" type="submit"><fmt:message key="signup.button.signin"/></button>
				</p>
			</div>
		</div>
    </body>

</html>
