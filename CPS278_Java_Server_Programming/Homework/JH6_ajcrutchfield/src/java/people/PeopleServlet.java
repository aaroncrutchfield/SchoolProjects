/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package people;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.ConnectionPool;

/**
 *
 * @author AaronC
 */
public class PeopleServlet extends HttpServlet {

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
        //get [servletContext] then retrieve [connectionPool] from it
        ServletContext servletContext = getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
        
        Connection connection;
        Statement statement;
        String errorMessage = "";
        
        try {
            //get a [connection] which is similar to getting a login
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            
            if (statement != null){
                //update method needs [statement] to do SQL and [request] to retrieve items from the form
                errorMessage = PeopleCollection.update(request, statement);
                statement.close();
            }
            
            if (connection != null) {
                //return the [connection] to the [connectionPool]
                connectionPool.free(connection);
            }
        } catch (SQLException e) {
            errorMessage = e.toString();
            
            //if errorMessage contains  Table 'ajcrutchfield.PeopleCollection' doesn't exist
            //create the table and modify the errorString
            //Table * doesn't exist
            
            String regex = "doesn't exist$";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(errorMessage);
            
            boolean noTable = m.lookingAt();
                            errorMessage = "no table";
            if (noTable) {
                errorMessage = "no table";
            }
        }
        
        //store the [errorMessage] in the [request] object
        request.setAttribute("errorMessage", errorMessage);
        
        //redirect to appropriate jsp page for output
        RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher("/peopleCollection.jsp");
        dispatcher.forward(request, response);
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
        doGet(request, response);
    }

}
