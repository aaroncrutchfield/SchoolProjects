package people;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

// Name, eye color, hair color, height, and weight
public class PersonCollection {
    private ArrayList<Person> personArr = new ArrayList<Person>();
    String errorMessage = "";
    
    public static PersonCollection update(PageContext pageContext){
        HttpSession session = pageContext.getSession();
        PersonCollection pCollection = (PersonCollection) session.getAttribute("PersonCollection");
        
        if(pCollection == null){
            pCollection = new PersonCollection();
            session.setAttribute("PersonCollection", pCollection);
        }
        
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String action = request.getParameter("action");
        
        if(action != null){
            String name = request.getParameter("name");
            String eyeColor = request.getParameter("eyeColor");
            String hairColor = request.getParameter("hairColor");
            String height = request.getParameter("height");
            String weight = request.getParameter("weight");
            
            Person person = new Person(name, eyeColor, hairColor, height, weight);
            
            //clear list if button is pressed
            if ("Clear List".equals(action))
                pCollection.personArr.clear();
            //add person if button is pressed
            else if ("add".equals(action)){
                boolean found = false;
                
                //check if person exists
                for (int i = 0; i < pCollection.personArr.size(); i++) {
                    if (person.equals(pCollection.personArr.get(i)))
                        found = true;
                    break;
                }
                
                if (!found) {
                    pCollection.personArr.add(person);
                    pCollection.errorMessage = "";
                } 
                else {
                    pCollection.errorMessage = "Person already exists";
                }
            }
            //remove person if button is pressed
            else if ("remove".equals(action)){
                for (int i = 0; i < pCollection.personArr.size(); i++) {
                    if(person.equals(pCollection.personArr.get(i)))
                        pCollection.personArr.remove(i);
                }
            }
            //update person if button is pressed
            else if ("update".equals(action)){
                String strIndex = request.getParameter("index");
                int index = Integer.parseInt(strIndex);
                
                Person p = pCollection.personArr.get(index);
                p.setName(name);
                p.setEyeColor(eyeColor);
                p.setHairColor(hairColor);
                p.setHeight(height);
                p.setWeight(weight);
            }
        }
        return pCollection;
    }
    
    public int size() {
        return personArr.size();
    }
    
    public Person getPerson(int index){
        return personArr.get(index);
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}