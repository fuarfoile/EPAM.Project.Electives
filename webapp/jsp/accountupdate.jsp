<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "mytag" uri = "customtaglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
        <meta charset="UTF-8" />
		<title><fmt:message key="account.update.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="logoutForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="LOGOUT"/>
		</form>
		<form id="searchForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="COURSE_SEARCH"/>
		</form>
		<form id="profileForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_PROFILE"/>
		</form>
		<form id="accountUpdateForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="ACCOUNT_UPDATE"/>
		</form>
		
		<header><h1>${user.getSurname()} ${user.getName()}</h1>
			<div class="search-box">
				<fmt:message key="tag.header.search.placeholder" var="val"/>
				<input form="searchForm" type="text" name="searchText" placeholder="${val}" value="${searchText == null ? "" : searchText}">
				<button form="searchForm" type="submit">></button>
			</div>
			<nav>
				<button form="logoutForm" type="submit"><fmt:message key="tag.header.nav.exit"/></button>
			</nav>
		</header>
		
		<div class="profile-page">
			<div class="form">
				<div class="group">
					<label><fmt:message key="account.update.input.email"/>*</label>
					<fmt:message key="account.update.input.email.placeholder" var="val"/>
					<input form="accountUpdateForm" type="email" name="login" value="${user.getEmail()}" placeholder="${val}" required />
				</div>
				
				<div class="group">
					<label><fmt:message key="account.update.input.password"/></label>
					<fmt:message key="account.update.input.password.placeholder" var="val"/>
					<input form="accountUpdateForm" type="password" name="password" value="${password}" placeholder="${val}">
				</div>
				
				<div class="group">
					<label><fmt:message key="account.update.input.password.repeat"/></label>
					<fmt:message key="account.update.input.password.repeat.placeholder" var="val"/>
					<input form="accountUpdateForm" type="password" name="rPassword" value="${rPassword}" placeholder="${val}">
				</div>
				
				<div class="group">
					<label><fmt:message key="account.update.input.name"/>*</label>
					<fmt:message key="account.update.input.name.placeholder" var="val"/>
					<input form="accountUpdateForm" type="text" name="name" value="${user.getName()}" placeholder="${val}" required />
				</div>
				
				<div class="group">
					<label><fmt:message key="account.update.input.surname"/>*</label>
					<fmt:message key="account.update.input.surname.placeholder" var="val"/>
					<input form="accountUpdateForm" type="text" name="surname" value="${user.getSurname()}" placeholder="${val}" required />
				</div>
				
				<div class="group">
					<label><fmt:message key="account.update.input.phone.number"/></label>
					<fmt:message key="account.update.input.phone.number.placeholder" var="val"/>
					<input form="accountUpdateForm" type="text" name="phoneNumber" value="${user.getPhoneNumber()}" placeholder="${val}">
				</div>
				
				<c:if test = "${not empty msg}">
					<div class="group">
						<p class="b-message">${msg}</p>
					</div>
				</c:if>
				
				<c:if test = "${not empty error_msg}">
					<pre class="error-message">${error_msg}</pre>
				</c:if>

				<div class="group">
					<button form="accountUpdateForm" class="button" type="submit"><fmt:message key="account.update.button.update"/></button>
				</div>
				<button form="profileForm" class="button" type="submit"><fmt:message key="account.update.button.return.profile"/></button>
			</div>
		</div>
    </body>

</html>
