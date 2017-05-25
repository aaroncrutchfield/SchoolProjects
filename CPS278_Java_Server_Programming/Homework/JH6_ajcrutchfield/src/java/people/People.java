/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package people;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AaronC
 */
public class People {
    private String name;
    private String eyeColor;
    private String hairColor;
    private String height;
    private String weight;
    private int index;

    //Default constructor
    public People(String name, String eyeColor, String hairColor, String height, String weight, int index) {
        this.name = name;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.height = height;
        this.weight = weight;
        this.index = index;
    }
    
    //Constructor for use with DB
    public People(String name, String eyeColor, String hairColor, String height, String weight) {
        this(name, eyeColor, hairColor, height, weight, -1);
    }  
    
    //Executes an update and returns an [errorString] if there is an SQLException caught
    private static String executeUpdate(String sql, Statement statment){
        try {
            System.out.println("sql= " + sql);
            statment.executeUpdate(sql);
        } catch (SQLException e) {
            return e.toString();
        }
        return "";
    }
    
    //Updates DB entries by index
    public String update(int index, Statement statement){
        String sql = "update PeopleCollection set name=" + q_surround(name) 
                + ", eyeColor=" + q_surround(eyeColor) + ", hairColor=" + q_surround(hairColor)
                + ", height=" + q_surround(height) + ", weight=" + q_surround(weight)
                + " where id=" + index;
        return executeUpdate(sql, statement);
    }
    
    //Inserts data into DB if [people] instance doesn't exist. Otherwise SQLException is caught
    public String insert (Statement statement){
        String sql = "select name from PeopleCollection where name=" + q_surround(name)
                + " AND eyeColor=" + q_surround(eyeColor) + " AND hairColor=" + q_surround(hairColor)
                + " AND height=" + q_surround(height) + " AND weight=" + q_surround(weight);
                System.out.println("SELECT SQL= " + sql);
        try {
            ResultSet rs = statement.executeQuery(sql);
            
            //If even one row exist, then [people] exists
            if (rs.next()){
                return "Book already exists";
            }
        } catch (SQLException e){
            return e.toString();
        }
        
        //if [people] instance doesn't exist, insert into DB
        sql = "insert into PeopleCollection values(" + q_surround(name) + ","
                + q_surround(eyeColor) + "," + q_surround(hairColor) + ","
                + q_surround(height) + "," + q_surround(weight) + ", null)";
        System.out.println("INSERT SQL= " + sql);
        return executeUpdate(sql, statement);
    }
    
    //Removes row with given index, otherwise clears all rows in DB
    public static String remove(int index, Statement statement){
        String sql = "";
        if (index >= 0) {
            sql =  "delete from PeopleCollection where id=" + index;
        } else {
            //TODO test this
            sql = "delete from PeopleCollection";
        }
        return executeUpdate(sql, statement);
    }
    
    //Surround with single quote
    private String q_surround(String s) {
        return "\'" + s + "\'";
    }

    //Needed for auto-generated equals method
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.eyeColor);
        hash = 61 * hash + Objects.hashCode(this.hairColor);
        hash = 61 * hash + Objects.hashCode(this.height);
        hash = 61 * hash + Objects.hashCode(this.weight);
        return hash;
    }

    //Auto-generated equals method from NetBeans
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final People other = (People) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.eyeColor, other.eyeColor)) {
            return false;
        }
        if (!Objects.equals(this.hairColor, other.hairColor)) {
            return false;
        }
        if (!Objects.equals(this.height, other.height)) {
            return false;
        }
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        return true;
    }
    
    public static String getPeople(Statement statement, ArrayList<People> peopleCollection){
        try {
            String sql = "select * from PeopleCollection";
            System.out.println("sql= " + sql);
            
            ResultSet rs = statement.executeQuery(sql);
            peopleCollection.clear();
            while (rs.next()) {
                String name = rs.getString("name");
                String eyeColor = rs.getString("eyeColor");
                String hairColor = rs.getString("hairColor");
                String height = rs.getString("height");
                String weight = rs.getString("weight");
                int index = rs.getInt("id");
                
                People people = new People(name, eyeColor, hairColor, height, weight, index);
                peopleCollection.add(people);
            }
        } catch (SQLException e){
            String errorMessage = e.toString();
            
            //if errorMessage contains  Table * doesn't exist
            //create the table and modify the errorString
            String regex = ".* Table .* doesn't exist$";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(errorMessage);
            boolean noTable = m.matches();
            
            if (noTable) {
                //fcom then TAB to create editor-fold
                //<editor-fold defaultstate="collapsed" desc="String sql">
                String sql = "create table PeopleCollection(" +
                            "name text, " +
                            "eyeColor text, " +
                            "hairColor text, " +
                            "height text, " +
                            "weight text, " +
                            "id int Primary Key Auto_Increment)";//</editor-fold>
                try{
                    statement.execute(sql);
                    errorMessage = "PeopleCollection table created";
                } catch (SQLException ex){
                    errorMessage = "Table creation failed";
                }
            }
            return errorMessage;
        }
        return "";
    }
    
    //Convinience method for logging
    public static void log(String label, String message){
        System.out.println("[LOG] " + label + "= " + message);
    }
    
    //Auto-generated getters and setters for private variables
    public int getIndex() {
        return index;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    
}
