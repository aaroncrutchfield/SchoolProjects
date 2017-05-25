<%@page import="java.io.PrintWriter"%>
<%@page import="one_liner.OneLinerUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%!
    String oneLineFile = "";
    String fullFilePath = "";
    ArrayList<String> oneLiners = new ArrayList<String>();
    int nextOneLiner = 0;
    OneLinerUtil util;
    
    
    public void jspInit(){

        fullFilePath = util.init(getServletConfig(), getServletContext());
        util = new OneLinerUtil(fullFilePath);
        //Checking values
        System.out.println("***************************");
        System.out.println("oneLineFile="+ oneLineFile);
        System.out.println("fullFilePath= "+fullFilePath); 
        System.out.println("nextOneLiner= "+nextOneLiner); 
        System.out.println("***************************");

    }
    %>
    
    
      <% 
        oneLiners = util.getContents();
        int size = oneLiners.size();
        
        //if the button is pressed
        boolean buttonPressed = util.updateFromRequest(request);
        
        System.out.println("***************************");
        System.out.println("buttonPressed= " +buttonPressed);
        System.out.println("***************************");
        
        if(buttonPressed){
            out.println(oneLiners.get(nextOneLiner));
            out.println(nextOneLiner);
            
            nextOneLiner++;
        }
        else{
            nextOneLiner++;
        }
        
        //prevent index out of bounds
        if (nextOneLiner == size){
            nextOneLiner = 0;
        }
        %>
        <!DOCTYPE html>
            <html>
                <head>
                    <title>Servlet OneLinerServlet JSP</title>     
                </head>
                <body>
                <!--if action has a value, button was pressed-->
                <!--get action to work without file extention-->
                    <form action="oneLinerJSP">
                        <input type="submit" name="action" value="Next" />
                    </form>
            
            
                </body>
            </html>
        
    


