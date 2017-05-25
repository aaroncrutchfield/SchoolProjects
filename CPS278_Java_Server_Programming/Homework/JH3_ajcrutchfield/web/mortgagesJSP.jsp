
<%@page import="mortgage_calculator.AllCalculations"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mortgage_calculator.MorgCalc"%>
<%

    String errorMessage = "";

    int principal = 0;
    int payment = 0;
    double interest = 0.0;
    int years = 0;

    try {
        principal = Integer.parseInt(request.getParameter("principal").trim());
    } catch (NumberFormatException e) {
        errorMessage += " Bad Principal Input. ";
    }

    try {
        payment = Integer.parseInt(request.getParameter("payment").trim());
    } catch (NumberFormatException e) {
        errorMessage += "Bad Payment Input. ";
    }

    try {
        interest = Double.parseDouble(request.getParameter("interest").trim());
    } catch (NumberFormatException e) {
        errorMessage += " Bad Interest Input. ";
    }

    try {
        years = Integer.parseInt(request.getParameter("years").trim());
    } catch (NumberFormatException e) {
        errorMessage += " Bad Year Input. ";
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Servlet CalculateMortgageServlet</title>
    </head>
    <body>

        <h1> Mortgage Calculations </h1>
        <b>Principal: $<%=principal%></b><br/>
        <b>Payment: $<%=payment%></b><br/>
        <b>Interest: <%=interest%>%</b><br/>
        <b>Years: <%=years%></b><br/><br/>
        <!--if the error message exist, a header will be created saying Bad User Inputs-->
        <!--with the error message following-->
        <%if (errorMessage.length() > 0) {%>
        <h2> Bad User Inputs:</h2><%=errorMessage%>
        <%} else {
            //doing the actual calculations using getRadioActveArr() method
            ArrayList<MorgCalc> morgArray
                    = AllCalculations.getMorgCalcArray(interest, years, principal, payment);
            double lastInterest = 0;
            double lastPayment = 0;

        %>
        <table border=\"4\" >
            <tr>
                <th>Month</th>
                <th>Principal</th>
                <th>Interest</th>
            </tr>
            <% //loop through the array and get calculation object each time
               for (int y = 0; y < morgArray.size(); y++) {
                    MorgCalc row = morgArray.get(y);
            %>
                    <tr>
                        <!--show the year and get calculations-->
                        <td><%=y + 1%></td>
                        <td><%=row.getPrincipal()%></td>
                        <td><%=row.getInterestPaid()%></td>
                    </tr>
                    <%
                        double tempPrinc = Double.parseDouble(row.getPrincipal());

                            if (tempPrinc < payment) {
                                lastInterest = (tempPrinc * interest) / (12 * 100);
                                lastInterest = Double.valueOf(String.format("%.2f", lastInterest));
                                lastPayment = tempPrinc + lastInterest;
                                lastPayment = Double.valueOf(String.format("%.2f", lastPayment));
                                break;
                            }
                }
                    %>
        </table>

        <!--last payment-->
        <b>Last Payment = $<%=lastPayment%></b><br/>
        <b>Include interest = $<%=lastInterest%></b>
        <%}%>
    </body>
</html>

