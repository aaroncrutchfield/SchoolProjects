<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Remove</title>
    </head>
    <body>
        <h1>Remove ${sessionScope.partNum}</h1>
        <br/>
        <form action="PartServlet">
            Are you sure you want to delete ${sessionScope.partNum}?<br/><br/>
            <input type="submit" name="action" value="Delete"/>
        </form>
        <br/><br/>
        <h3>${errorMessage}</h3>
        <hr/>
        <a href="details.jsp">Back</a>
    </body>
</html>
