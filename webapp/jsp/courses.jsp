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
		
		<header><h1>${user.getSurname()} ${user.getName()}</h1>
			<div class="search-box">
				<fmt:message key="tag.header.search.placeholder" var="val"/>
				<input form="searchForm" type="text" name="searchText" placeholder="${val}" value="${searchText == null ? "" : searchText}">
				<button form="searchForm" type="submit">></button>
			</div>
			<nav>
				<input form="accountForm" type="hidden" name="fromPage" value="ACCOUNT_UPDATE"/>
				<button form="accountForm" type="submit"><fmt:message key="tag.header.nav.account"/></button>
				<button form="logoutForm" type="submit"><fmt:message key="tag.header.nav.exit"/></button>
			</nav>
		</header>
		
		<div class="profile-page">
			<div class="form">
				<mytag:coursesTable courses="${courses}"/>
				<button form="profileForm" class="button" type="submit"><fmt:message key="courses.button.return"/></button>
			</div>
		</div>
    </body>

</html>
