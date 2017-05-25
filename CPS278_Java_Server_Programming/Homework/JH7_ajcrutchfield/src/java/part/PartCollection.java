/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;

/**
 *
 * @author AaronC
 */
public class PartCollection {
    
    public static String update(HttpServletRequest request, Statement statement, ServletContext application){
        String errorMessage = "";
        String action = request.getParameter("action");
        
        HttpSession session = request.getSession();
                
        if (action != null){
            
            Part.log("PartCollection.update.action", action);
            if (errorMessage.equals("")){
                switch (action) {
                    case "Submit":
                        errorMessage = SubmitFormData(request, statement, application);
                        break;
                    case "Details":
                        ViewDetails(request, statement, session);
                        break;
                    case "Delete":
                        int removeId = (int) session.getAttribute("removeId");
                        Part.delete(removeId, statement);
                        break;
                    case "Move":
                        int moveId = (int) session.getAttribute("moveId");
                        String newLocation = request.getParameter("newLocation");
                        Part.updateLocation(moveId, statement, newLocation);
                        
                        break;
                }
            }
            
            //Called every time to ensure table is updated
            ArrayList<Part> partCollection = new ArrayList<Part>();
            errorMessage += Part.getParts(statement, partCollection);
            //store the partCollection ArrayList in the application object
            application.setAttribute("PartCollection", partCollection);
        }
        return errorMessage;
    }

    //Get data from form and add it to DB
    private static String SubmitFormData(HttpServletRequest request, Statement statement, ServletContext application) {
        Part part;
        String errorMessage = "";
        //Get parameters from the form
        String partNumber = request.getParameter("partNumber");
        String location = request.getParameter("location");
        String count = request.getParameter("count");
        errorMessage = validateInput(partNumber, location, count);
        if (errorMessage.equals("")){
            part = new Part(partNumber, location, count, Part.getTodaysDate());
            errorMessage = part.insert(statement);
        }
        return errorMessage;
    }

    //Show results from querying DB for entries with specific partNumber
    private static void ViewDetails(HttpServletRequest request, Statement statement, HttpSession session) {
        ArrayList<Part> detailsCollection = new ArrayList<Part>();
        String partNum = request.getParameter("partNum");
        
        //Query DB for entries where partNumer = partNum
        Part.getDetails(statement, detailsCollection, partNum);
        //store arrayList in request object (maybe session)
        session.setAttribute("DetailsCollection", detailsCollection);
        session.setAttribute("partNum", partNum);
        Part.log("viewDetails.partNum", partNum);
    }

    //validates entries from all 3 text inputs in form
    private static String validateInput(String partNumber, String location, String count) {
        String partRegex = "[0-9]{4,}";
        String locationRegex = "[A-Z][0-9]+";
        String countRegex = "[0-9]+@[0-9]+";
        
        Pattern p = Pattern.compile(partRegex);
        Pattern l = Pattern.compile(locationRegex);
        Pattern c = Pattern.compile(countRegex);
        
        Matcher mp = p.matcher(partNumber);
        Matcher ml = l.matcher(location);
        Matcher mc = c.matcher(count);
        
        if (!mp.matches())
            return "Invalid Part Number. </br>Valid Ex: 26667";
        if (!ml.matches())
            return "Invalid Location. </br>Valid Ex: F12";
        if (!mc.matches())
            return "Invalid Count. </br>Valid Ex: 18@30";
        
        return "";
    }
}
