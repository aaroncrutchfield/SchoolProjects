/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package people;

import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AaronC
 */
public class PeopleCollection {
    
    public static String update(HttpServletRequest request, Statement statement){
        String errorMessage = "";
        String action = request.getParameter("action");
        
        if (action != null){
            //Get all parameters from the form
            String name = request.getParameter("name");
            String eyeColor = request.getParameter("eyeColor");
            String hairColor = request.getParameter("hairColor");
            String height = request.getParameter("height");
            String weight = request.getParameter("weight");
            
            //Construct an instance of [People]
            People people = new People(name, eyeColor, hairColor, height, weight);
            
            String strIndex;
            int index;
            
            switch(action){
                case "Clear List":
                    errorMessage = People.remove(-1, statement);
                    break;
                case "Add":
                    errorMessage = people.insert(statement);
                    break;
                case "Remove":
                    strIndex = request.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = People.remove(index, statement);
                    break;
                case "Update":
                    strIndex = request.getParameter("index");
                    People.log("Update.StrIndex", strIndex);
                    index = Integer.parseInt(strIndex);
                    errorMessage = people.update(index, statement);
                    break;
            }
        }
        ArrayList<People> peopleCollection = new ArrayList<People>();
        errorMessage += People.getPeople(statement, peopleCollection);
        
        //store the [peopleCollection] ArrayList in the [request] object
        request.setAttribute("PeopleCollection", peopleCollection);
        
        return errorMessage;
    }
    
    
}
