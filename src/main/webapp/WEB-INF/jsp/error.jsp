<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page isErrorPage="true" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
		<title>Error</title>
		<link rel="stylesheet" href="css/style.css">
    </head>

    <body>
		<div class="login-page">
			<div class="form">
					<p class="error-message">Something goes wrong. Try again later</p>
					<p class="error-message">${error_msg}</p>
			</div>
		</div>
    </body>

</html>
