<%@page import="radioActive.*,java.util.*"%>
<%
    // Counting on our hidden variable being there and correct
        int pixelsInTotalBar = Integer.parseInt(request.getParameter("pixelsInTotalBar"));
       
        // text fields are assumed to never return null
        String extraTitleText = request.getParameter("substance").trim(); 
        if (extraTitleText.length() > 0)
            extraTitleText = " for "+ extraTitleText;
        
        String errorMsg ="";
        
        int years=0;
        double halfLife=1.0;
        try
        {
            years = Integer.parseInt(request.getParameter("years").trim());
        }
        catch (NumberFormatException e)
        {
            errorMsg += " Bad Number of Years. ";
        }
        
        try
        {
            halfLife = Double.parseDouble(request.getParameter("halfLife").trim());
            extraTitleText += ",  HalfLife = " + halfLife;
        }
        catch (NumberFormatException e)
        {
            errorMsg += " Bad Half Life. ";
        }
        
        ArrayList<RACalc> raArr = null;
        if (errorMsg.length() == 0)
            raArr = RadioActiveCalculations.getRadioActiveArr(halfLife,  years,  pixelsInTotalBar);
%>
<html>
    <head>
        <title>Servlet RadioActiveServlet</title>
    </head>
    <body>
        <h1> Radio Active Calculations  <%= extraTitleText %></h1>
        <%
        if (errorMsg.length() > 0)
                out.println("<h2> Bad User Inputs:</h2>" + errorMsg);
        else
        { // Go to Html Mode now
        %>
            <table border="4">
                <tr><th>Year</th><th>Amount Decayed</th><th>Fraction Remaining</th>
                <th>Bar Remaining</th></tr>
            <% // Back in Java Mode
                for (int y=0; y < raArr.size(); y++)
                { 
                    RACalc rac = raArr.get(y);
                    // go to html mode
             %>  <tr>
                    <td>  <%= y %>  </td>
                    <td>  <%= rac.getLostFraction() %>  </td>
                    <td>  <%= rac.getCurrFraction() %>   </td>
                    <td>  <img src="red.gif" height="10" 
                             width="<%=rac.getCurrFractionPixels()%>" /> </td>
                 </tr>
            <%   // Back in Java Mode
                }  // end of for loop
             
                
            out.println("</table>");
        
        } // end of the else part to if (errorMsg.length() > 0)
        // Go back to Html Mode
        %>
        
    </body>
</html>