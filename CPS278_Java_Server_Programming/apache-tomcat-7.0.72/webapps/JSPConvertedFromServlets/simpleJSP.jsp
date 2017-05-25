<%@page import="utilities.Utilities,java.io.*" %>
<%
    String fullName = request.getParameter("fullName");
            
    // Write out fullName to some file
    ServletContext sc = getServletContext();
    String path= sc.getRealPath("/WEB-INF/")+ File.separator +"nameList.txt";
    System.out.println("path="+path); // So you can see where your pathname
    Utilities.add(fullName, path);
%>
<html>
    <body>
        <h3><%= fullName %> written to file</h3>
    </body>
</html>
