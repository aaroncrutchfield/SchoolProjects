<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Move Location</title>
    </head>
    <body>
        <h1>Move ${sessionScope.partNum} Location</h1>
        <br/>
        <form action="PartServlet">
            Current Location: ${sessionScope.currentLocation}<br/>
            New Location:
            <input type="text" name="newLocation"/>
            <br/>
            <input type="submit" name="action" value="Move"/>
        </form>
        <br/><br/>
        <h3>${errorMessage}</h3>
        <hr/>
        <a href="details.jsp">Back</a>
    </body>
</html>