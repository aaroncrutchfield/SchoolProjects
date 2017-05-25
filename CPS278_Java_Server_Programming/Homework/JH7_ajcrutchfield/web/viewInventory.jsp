<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Inventory</title>
    </head>
    <body>
        <h1>View Inventory</h1>
        <br/>
        <form action="PartServlet">
            Search: 
            <input type="text" name="searchNumber"/>
            <input type="submit" name="action" value="Submit"/>
            <br/><br/>
        </form>
        <a href="addInventory.jsp">Add Inventory</a><br/>
            <table border="2">
                <tr>
                    <!--Headers-->
                    <th>Part #</th>
                    <th>Total</th>
                    <th>Last Seen</th>
                </tr>
                <c:forEach var="part" items="${applicationScope.PartCollection}" varStatus="loopStatus">
                    <tr>
                        <form action="PartServlet">
                            <td>${part.partNumber}</td>
                            <td>${part.total}</td>
                            <td>${part.date}</td>
                            <td>
                                <input type="submit" name="action" value="Details"/>
                                <input type="hidden" name="partNum" value="${part.partNumber}"/>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </table>
        <br/><br/>
        <h3>${errorMessage}</h3>
    </body>
</html>