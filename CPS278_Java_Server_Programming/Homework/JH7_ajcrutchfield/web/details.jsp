<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Details</title>
    </head>
    <body>
        <h1>${sessionScope.partNum} Details</h1>
        <br/>
        
        <a href="viewInventory.jsp">Back</a><br/>
            <form action="PartServlet">
                <c:forEach var="details" items="${sessionScope.DetailsCollection}" varStatus="loopStatus">
                    <table border="2">
                    <tr>
                        <th>Location</th>
                        <td>${details.location}</td>
                    </tr>
                    <tr>
                        <th>Count</th>
                        <td>${details.count}</td>
                    </tr>
                    <tr>
                        <th>Total</th>
                        <td>${details.total}</td>
                    </tr>
                        <th>Date</th>
                        <td>${details.date}</td>
                    <tr></tr>
                        <td><input type="Submit" name="action" value="Remove"></td>
                        <td><input type="Submit" name="action" value="Move Location"></td>
                        <input type="hidden" name="id" value="${details.id}"/>
                        <input type="hidden" name="currentLocation" value="${details.location}"/>
                    </tr>
                    </table>
                    <br/>
                </c:forEach>
            </form>
        <br/><br/>
        <h3>${errorMessage}</h3>
    </body>
</html>