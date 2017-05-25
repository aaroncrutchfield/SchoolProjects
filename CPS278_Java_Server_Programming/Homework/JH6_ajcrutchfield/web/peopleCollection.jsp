<%-- 
    Document   : peopleCollection
    Created on : Nov 26, 2016, 6:25:49 PM
    Author     : AaronC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>People Collection</title>
    </head>
    <body>
        <h1>People Collection</h1>
        <form action="PeopleServlet">
            <input type="submit" name="action" value="Clear List"/>
        </form>
        <p></p>
        <form action="PeopleServlet">
            Name:
            <br/><input type="text" name="name"/><br/>
            Eye Color: 
            <br/><input type="text" name="eyeColor"/><br/>
            Hair Color:
            <br/><input type="text" name="hairColor"/><br/>
            Height:
            <br/><input type="text" name="height"/><br/>
            Weight: 
            <br/><input type="text" name="weight"/><br/>
            <br/><input type="submit" name="action" value="Add"/>
        </form>
        <hr>
        <h3>${errorMessage}</h3>
        <table border="3">
            <tr>
                <!--Headers-->
                <th>Name</th>
                <th>Eye Color</th>
                <th>Hair Color</th>
                <th>Height</th>
                <th>Weight</th>
                <th></th>
            </tr>
            <c:forEach var="people" items="${PeopleCollection}" varStatus="loopStatus">
                <tr>
                <form action="PeopleServlet">
                    <!--Data-->
                    <td><input type="text" name="name" value="${people.name}"/></td>
                    <td><input type="text" name="eyeColor" value="${people.eyeColor}"/></td>
                    <td><input type="text" name="hairColor" value="${people.hairColor}"/></td>
                    <td><input type="text" name="height" value="${people.height}"/></td>
                    <td><input type="text" name="weight" value="${people.weight}"/></td>
                    
                    <!--remove and submit buttons-->
                    <td>
                        <input type="submit" name="action" value="Remove"/>
                        <input type="submit" name="action" value="Update"/>
                        <input type="hidden" name="index" value="${people.index}"/>
                    </td>
                </form>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
