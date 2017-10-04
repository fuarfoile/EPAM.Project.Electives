<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "mytag" uri = "customtaglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title><fmt:message key="courses.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="logoutForm" action="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="LOGOUT"/>
		</form>
		<form id="searchForm" action="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="COURSE_SEARCH"/>
		</form>
		<form id="accountForm" action="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_ACCOUNT_UPDATE"/>
		</form>
		<form id="profileForm" action="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_PROFILE"/>
		</form>
		
		<mytag:header text="${user.getSurname()} ${user.getName()}"/>
		
		<div class="profile-page">
			<div class="form">
				<mytag:coursesTable courses="${courses}"/>
				<button form="profileForm" class="button" type="submit"><fmt:message key="courses.button.return"/></button>
			</div>
		</div>
    </body>

</html>
