<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Inventory</title>
    </head>
    <body>
        <h1>Add Inventory</h1>
        <br/>
        <form action="PartServlet">
            Part Number: <br/>
            <input type="text" name="partNumber"/>
            <br/>
            Quantity: <br/>
            <input type="text" name="count"/>
            <br/>
            Location: <br/>
            <input type="text" name="location"/>
            <br/><br/>
            <input type="submit" name="action" value="Submit"/>
        </form>
        <br/><br/>
        <h3>${errorMessage}</h3>
        <hr/>
        <a href="viewInventory.jsp">View Inventory</a>
    </body>
</html>
