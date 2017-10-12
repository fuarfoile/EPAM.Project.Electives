<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "mytag" uri = "customtaglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title>${course.getName()}</title>
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
				<c:if test = "${user.getPosition() == 'ADMIN'}">
					<form id="createForm" action="controller" method=POST>
						<input type="hidden" name="TypeCommand" value="COURSE_CREATE"/>

							<div class="group">
								<label><fmt:message key="course.create.input.name"/>:</label>
								<fmt:message key="course.create.input.name.placeholder" var="val"/>
								<input type="text" name="name" value="${name}" placeholder="${val}" required />
							</div>
							
							<div class="group">
								<label><fmt:message key="course.create.input.maxstudents"/>:</label>
								<fmt:message key="course.create.input.maxstudents.placeholder" var="val"/>
								<input type="number" name="maxStudentsCount" min="0" value="${maxStudentsCount}" placeholder="${val}" required />
							</div>
							
							<div class="group">
								<label><fmt:message key="course.text.description"/>:</label>
								<fmt:message key="course.input.review.placeholder" var="val"/>
								<input type="text" name="description" value="${description}" placeholder="${val}" />
							</div>
							
							<div class="group">
								<label><fmt:message key="course.create.input.teacher"/>:</label>
								<mytag:teacherSelect teacherId="${course.getTeacherId()}"/>
							</div>
							
							<div class="group">
								<label><fmt:message key="course.input.status"/>:</label>
								<mytag:courseStatusSelect status="${course.getStatus()}"/>
							</div>

					</form>
				</c:if>
				
				<c:if test = "${not empty msg}">
					<pre class="message">${msg}</pre>
				</c:if>
				<c:if test = "${not empty error_msg}">
					<pre class="error-message">${error_msg}</pre>
				</c:if>
				
				<div class="group">
					<button form="createForm" class="button" type="submit"><fmt:message key="course.create.button.create"/></button>
				</div>
				<button form="profileForm" class="button" type="submit"><fmt:message key="course.button.return.profile"/></button>
			</div>
		</div>
    </body>

</html>
