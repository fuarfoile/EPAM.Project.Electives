<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "mytag" uri = "customtaglib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization\translation" />
<html lang="${language}">
    <head>
		<title><fmt:message key="profile.teacher.title"/></title>
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
		
		<mytag:header text="${user.getSurname()} ${user.getName()}"/>
		
		<div class="profile-page">
			<c:if test = "${user.getPosition() == 'ADMIN'}">
				<div class="form">
					<form action ="controller" method=POST>
						<input type="hidden" name="TypeCommand" value="R_COURSE_CREATE"/>
						<td><button class="button" type="submit"><fmt:message key="profile.teacher.button.create.course"/></button></td>
					</form>
				</div>
			</c:if>
			<div class="form">
				<h1><fmt:message key="profile.teacher.courses.future"/></h1>
				<table>
					<thead>
						<tr>
							<th><fmt:message key="profile.teacher.table.courses"/></th>
							<th><fmt:message key="profile.teacher.table.group.size"/></th>
						</tr>
					</thead>
					<c:forEach items="${courses}" var="entry">
						<c:if test = "${entry.getStatus() == 'DEVELOPING'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="TEACHER_PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getId()}"/>
									<td><button class="button" type="submit">${entry.getName()}</button></td>
								</form>
								<td>${entry.getMaxStudentsCount()}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="form">
				<h1><fmt:message key="profile.teacher.courses.registered"/></h1>
				<table>
					<thead>
						<tr>
							<th><fmt:message key="profile.teacher.table.courses"/></th>
							<th><fmt:message key="profile.teacher.table.group.size"/></th>
						</tr>
					</thead>
					<c:forEach items="${courses}" var="entry">
						<c:if test = "${entry.getStatus() == 'REGISTRATION'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="TEACHER_PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getId()}"/>
									<td><button class="button" type="submit">${entry.getName()}</button></td>
								</form>
								<td>${entry.getStudentsCount()} / ${entry.getMaxStudentsCount()}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="form">
				<h1><fmt:message key="profile.teacher.courses.current"/></h1>
				<table>
					<thead>
						<tr>
							<th><fmt:message key="profile.teacher.table.courses"/></th>
							<th><fmt:message key="profile.teacher.table.group.size"/></th>
						</tr>
					</thead>
					<c:forEach items="${courses}" var="entry">
						<c:if test = "${entry.getStatus() == 'RUNNING'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="TEACHER_PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getId()}"/>
									<td><button class="button" type="submit">${entry.getName()}</button></td>
								</form>
								<td>${entry.getStudentsCount()} / ${entry.getMaxStudentsCount()}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="form">
				<h1><fmt:message key="profile.teacher.courses.finished"/></h1>
				<table>
					<thead>
						<tr>
							<th><fmt:message key="profile.teacher.table.courses"/></th>
							<th><fmt:message key="profile.teacher.table.group.size"/></th>
						</tr>
					</thead>
					<c:forEach items="${courses}" var="entry">
						<c:if test = "${entry.getStatus() == 'FINISHED'}">
							<tr>
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="R_COURSE"/>
									<input type="hidden" name="fromPage" value="TEACHER_PROFILE"/>
									<input type="hidden" name="courseId" value="${entry.getId()}"/>
									<td><button class="button" type="submit">${entry.getName()}</button></td>
								</form>
								<td>${entry.getStudentsCount()} / ${entry.getMaxStudentsCount()}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
    </body>

</html>
