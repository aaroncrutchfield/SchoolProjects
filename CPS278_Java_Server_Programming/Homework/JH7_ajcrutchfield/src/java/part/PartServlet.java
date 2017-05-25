/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.ConnectionPool;

/**
 *
 * @author AaronC
 */
public class PartServlet extends HttpServlet {

    
    
    //Query DB for entries as soon as servlet starts
    @Override
    public void init(){
       //get [servletContext] then retrieve [connectionPool] from it
        ServletContext servletContext = getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");

        Connection connection;
        Statement statement;
        String errorMessage = "";
        
        try {
            //get a [connection] which is similar to getting a login
            connection = connectionPool.getConnection();
            //TODO check for errors from being UPDATABLE
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            if (statement != null) {
                ArrayList<Part> partCollection = new ArrayList<Part>();
                errorMessage += Part.getParts(statement, partCollection);

                //store the partCollection ArrayList in the application object
                servletContext.setAttribute("PartCollection", partCollection);

                //TODO if details
                statement.close();
            }

            if (connection != null) {
                //return the [connection] to the [connectionPool]
                connectionPool.free(connection);
            }
        } catch (SQLException e) {
            errorMessage = e.toString();
        }
        Part.log("Servlet.init.errorMessage", errorMessage);
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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

        ServletContext application = getServletContext();
        Connection connection;
        Statement statement;
        String errorMessage = "";
        HttpSession session = request.getSession();
        
        try {
            //get a [connection] which is similar to getting a login
            connection = connectionPool.getConnection();
            //TODO check for errors from being UPDATABLE
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            if (statement != null){
                //update method needs [statement] to do SQL and [request] to retrieve items from the form
                errorMessage = PartCollection.update(request, statement, application);
                statement.close();
            }
            
            if (connection != null) {
                //return the [connection] to the [connectionPool]
                connectionPool.free(connection);
            }
        } catch (SQLException e) {
            errorMessage = e.toString();
        }
        
        //store the [errorMessage] in the [request] object
        request.setAttribute("errorMessage", errorMessage);
        
        //redirect to appropriate jsp page for output
        String url = "";
        String action = request.getParameter("action");
        
        switch(action){
            case "Details":
                url = "/details.jsp";
                break;
            case "Move Location":
                url = "/moveLocation.jsp";
                session.setAttribute("moveId", request.getParameter("id"));
                break;
            case "Remove":
                url = "/remove.jsp";
                session.setAttribute("removeId", request.getParameter("id"));
                break;
            case "Submit":
                url = "/addInventory.jsp";
                break;
            case "Move":
                url = "/details.jsp";
                break;
        }

        RequestDispatcher dispatcher = 
                    getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
    
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);    
    }
}
