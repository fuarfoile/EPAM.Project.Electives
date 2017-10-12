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
				<table>
					<tr><td><h1>${course.getName()}</h1></td> <td><h1><fmt:message key="course.text.group.size"/>: ${course.getStudentsCount()} / ${course.getMaxStudentsCount()}</h1></td></tr>
					<c:choose>
						<c:when test = "${user.getPosition() == 'ADMIN' && course.getStatus() != 'FINISHED'}">
							<tr><td colspan="2" class="review"><fmt:message key="course.text.teacher"/>:</td></tr>
							<tr><td colspan="2" class="review">
								<form action ="controller" method=POST>
									<input type="hidden" name="TypeCommand" value="COURSE_CHANGE"/>
									<input type="hidden" name="courseId" value="${course.getId()}"/>
									<input type="hidden" name="description" value="${course.getDescription()}"/>
									<mytag:teacherSelect teacherId="${course.getTeacherId()}" onchange="true"/>
								</form>
							</td></tr>
						</c:when>
						<c:otherwise>
							<tr><td colspan="2" class="review"><fmt:message key="course.text.teacher"/>: ${course.getTeacherSurname()} ${course.getTeacherName()}</td></tr>
						</c:otherwise>
					</c:choose>
					<c:if test = "${not empty course.getDescription() && course.getDescription().length() > 0 && (user.getPosition() != 'ADMIN' || course.getStatus() == 'FINISHED')}">
						<tr><td colspan="2" class="review"><fmt:message key="course.text.description"/>: ${course.getDescription()}<td><tr>
					</c:if>
					<c:if test = "${user.getPosition() == 'ADMIN' && course.getStatus() != 'FINISHED'}">
						<tr><td colspan="2" class="review"><fmt:message key="course.text.description"/>:</td></tr>
						<tr><td colspan="2" class="review">
							<form action ="controller" method=POST>
								<fmt:message key="course.input.review.placeholder" var="val"/>
								<input type="hidden" name="TypeCommand" value="COURSE_CHANGE"/>
								<input type="hidden" name="courseId" value="${course.getId()}"/>
								<input type="hidden" name="teacherId" value="${course.getTeacherId()}"/>
								<input type="text" name="description" placeholder="${val}" value="${course.getDescription()}" onchange="submit()"/>
							</form>
						</td></tr>
					</c:if>
					<c:if test = "${user.getPosition() == 'ADMIN'}">
						<tr><td colspan="2" class="review"><fmt:message key="course.input.status"/>:</td></tr>
						<tr><td colspan="2" class="review">
							<form action ="controller" method=POST>
								<input type="hidden" name="TypeCommand" value="COURSE_CHANGE"/>
								<input type="hidden" name="courseId" value="${course.getId()}"/>
								<input type="hidden" name="teacherId" value="${course.getTeacherId()}"/>
								<input type="hidden" name="description" value="${course.getDescription()}"/>
								<mytag:courseStatusSelect status="${course.getStatus()}" onchange="true"/>
							</form>
						</td></tr>
					</c:if>
				</table>
				
				<c:if test = "${course.getStatus() != 'DEVELOPING'}">
					<table>
						<thead>
							<tr>
								<th><fmt:message key="course.text.student"/></th>
								<c:if test = "${course.getStatus() == 'RUNNING'}">
									<th><fmt:message key="course.input.mark.current"/></th>
								</c:if>
								<c:if test = "${course.getStatus() == 'FINISHED'}">
									<th><fmt:message key="course.input.mark"/></th>
								</c:if>
							</tr>
						</thead>
						<c:forEach items="${studentCourses}" var="entry">
							<tr>
								<td>${entry.getStudentSurname()} ${entry.getStudentName()}</td>
								<c:choose>
									<c:when test = "${user.getPosition() != 'STUDENT' && user.getId() == entry.getTeacherId() && course.getStatus() == 'RUNNING'}">
										<td><form action ="controller" method=POST>
											<input type="hidden" name="TypeCommand" value="MARK_CHANGE"/>
											<input type="hidden" name="studentCourseId" value="${entry.getId()}"/>
											<input type="hidden" name="review" value="${entry.getReview()}"/>
											<fmt:message key="course.input.mark.placeholder" var="val"/>
											<input type="number" min="0" max="100" name="mark" placeholder="${val}" value="${entry.getMark() == '0' ? '-' : entry.getMark()}" onchange="submit()"/>
										</form></td>
									</c:when>
									<c:otherwise>
										<c:if test = "${course.getStatus() != 'REGISTRATION' && course.getStatus() != 'DEVELOPING'}">
											<td>${entry.getMark() == "0" ? "-" : entry.getMark()}</td>
										</c:if>
									</c:otherwise>
								</c:choose>
							</tr>
							<c:if test = "${user.getPosition() != 'STUDENT' && course.getStatus() != 'REGISTRATION' && course.getStatus() != 'DEVELOPING'}">
								<tr>
									<c:choose>
										<c:when test = "${user.getId() == entry.getTeacherId() && course.getStatus() == 'RUNNING'}">
											<td colspan="2" class="review">
												<form action ="controller" method=POST>
												<input type="hidden" name="TypeCommand" value="MARK_CHANGE"/>
												<input type="hidden" name="studentCourseId" value="${entry.getId()}"/>
												<input type="hidden" name="mark" value="${entry.getMark()}"/>
												<fmt:message key="course.input.review.placeholder" var="val"/>
												<input type="text" name="review" placeholder="${val}" value="${entry.getReview()}" onchange="submit()"/>
											</td>
										</c:when>
										<c:otherwise>
											<td colspan="2" class="review">${entry.getReview()}</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</c:if>
				<c:if test = "${not empty msg}">
					<div class="error-message">${msg}</div>
				</c:if>
				<c:if test = "${user.getPosition() == 'STUDENT' && course.getStatus() == 'REGISTRATION'}">
					<form action ="controller" method=POST>
						<input type="hidden" name="courseId" value="${course.getId()}"/>
						<mytag:applyButton course = "${course}"/>
					</form>
					<br>
				</c:if>
				<c:if test="${fromPage == 'COURSES'}">
					<div class="group">
						<button form="searchForm" class="button" type="submit"><fmt:message key="course.button.return.courses"/></button>
					</div>
				</c:if>
				<button form="profileForm" class="button" type="submit"><fmt:message key="course.button.return.profile"/></button>
			</div>
		</div>
    </body>

</html>
