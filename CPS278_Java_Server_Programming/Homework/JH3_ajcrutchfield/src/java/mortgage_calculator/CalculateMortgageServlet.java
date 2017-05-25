/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mortgage_calculator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AaronC
 */
public class CalculateMortgageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CalculateMortgageServlet</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1> Mortgage Calculations </h1>");
            out.println("<b>Principal: $"+ principal +"</b><br/>");
            out.println("<b>Payment: $"+ payment+"</b><br/>");
            out.println("<b>Interest: "+ interest+"%</b><br/>");
            out.println("<b>Years: "+ years+"</b><br/><br/>");
            //if the error message exist, a header will be created saying Bad User Inputs
            //with the error message following
            if (errorMessage.length() > 0) {
                out.println("<h2> Bad User Inputs:</h2>" + errorMessage);
            } else {

                out.println("<table border=\"4\" >");
                out.println("<tr><th>Month</th> <th>Principal</th><th>Interest</th>");

                //doing the actual calculations using getRadioActveArr() method
                ArrayList<MorgCalc> morgArray
                        = AllCalculations.getMorgCalcArray(interest, years, principal, payment);

                double lastInterest = 0;
                double lastPayment = 0;
                for (int y = 0; y < morgArray.size(); y++) {
                    out.println("<tr>");
                    //show the year
                    out.println("<td>" + (y+1) + "</td>");
                    //get the row calculation object
                    MorgCalc row = morgArray.get(y);
                    //get the values for the calculations that are already done
                    out.println("<td>" + row.getPrincipal() + "</td>");
                    out.println("<td>" + row.getInterestPaid() + "</td>");
                    double tempPrinc = Double.parseDouble(row.getPrincipal());
                    
                    if (tempPrinc < payment){
                        lastInterest = (tempPrinc * interest) / (12*100);
                        lastPayment = tempPrinc + lastInterest;
                        break;
                    }
                }
                

                out.println("</table>");
                
                //last payment
                out.println("Last Payment = " + String.format("%.2f", lastPayment) + "<br/>");
                out.println("Include interest = " + String.format("%.2f", lastInterest));
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
