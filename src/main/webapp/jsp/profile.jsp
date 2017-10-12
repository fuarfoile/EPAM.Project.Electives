<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "mytag" uri = "customtaglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title><fmt:message key="profile.title"/></title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<form id="logoutForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="LOGOUT"/>
		</form>
		<form id="searchForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="COURSE_SEARCH"/>
		</form>
		<form id="accountForm" action ="controller" method=POST>
			<input type="hidden" name="TypeCommand" value="R_ACCOUNT_UPDATE"/>
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
				<h1><fmt:message key="profile.courses.registered"/></h1>
				<table>
					<thead>
						<tr><th><fmt:message key="profile.table.courses"/></th></tr>
					</thead>
					<c:forEach items="${studentCourses}" var="entry">
						<c:if test = "${entry.getStatus() == 'REGISTRATION'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getCourseId()}"/>
									<td><button class="button" type="submit">${entry.getCourseName()}</button></td>
								</form>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<button form="searchForm" class="button" type="submit"><fmt:message key="profile.button.new.course"/></button>
			</div>
			<div class="form">
				<h1><fmt:message key="profile.courses.current"/></h1>
				<table>
					<thead>
						<tr><th><fmt:message key="profile.table.courses"/></th></tr>
					</thead>
					<c:forEach items="${studentCourses}" var="entry">
						<c:if test = "${entry.getStatus() == 'RUNNING'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getCourseId()}"/>
									<td><button class="button" type="submit">${entry.getCourseName()}</button></td>
								</form>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="form">
				<h1><fmt:message key="profile.courses.finished"/></h1>
				<table>
					<thead>
						<tr>
							<th><fmt:message key="profile.table.courses"/></th>
							<th><fmt:message key="profile.table.mark"/></th>
						</tr>
					</thead>
					<c:forEach items="${studentCourses}" var="entry">
						<c:if test = "${entry.getStatus() == 'FINISHED'}">
							<tr><form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getCourseId()}"/>
									<td><button class="button" type="submit">${entry.getCourseName()}</button></td>
								</form>
								<td>${entry.getMark()}</td>
							</tr>
							<c:if test = "${entry.getReview().length() > 0}">
								<tr><td colspan="2" class="review"><fmt:message key="profile.text.review"/>: ${entry.getReview()}</td></tr>
							</c:if>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
    </body>

</html>
