/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AaronC
 */
public class Part {
    private String partNumber;
    private String location;
    private String count;
    private int total;
    private String date;
    private int id;

    //Default constructor
    public Part(String partNumber, String location, String count, String date, int id) {
        this.partNumber = partNumber;
        this.location = location;
        //TODO create method to consolidate and append counts
        this.count = count;
        //TODO create method to calculate total
        calculateTotal(count);
        this.date = date;
        this.id = id;
    } 

    //Constructor for use with DB
    public Part(String partNumber, String location, String count, String date) {
        this(partNumber, location, count, date, -1);
        
        //
    }
    
    //Updates DB entry by index
    public static String updateLocation(int id, Statement statement, String location){
        String sql = "udpate " + Constants.TABLE + " set location=" +
                qSurround(location) + ", date=" + qSurround(getTodaysDate()) +
                " where id=" + id;
        log("updateLocation.sql", sql);
        return executeUpdate(sql, statement);
    }
    
    //Inserts data into DB
    public String insert(Statement statement){
        String sql = "insert into " + Constants.TABLE + " values("
                + qSurround(partNumber) + "," + qSurround(location) + ","
                + qSurround(count) + "," + qSurround(String.valueOf(total)) + "," 
                + qSurround(getTodaysDate()) + "," + "null)";
        
        log("insert.sql", sql);
        return executeUpdate(sql, statement);
    }
    
    //Deletes part from DB by index
    public static String delete(int id, Statement statement){
        String sql = "delete from " + Constants.TABLE + " where id=" + id;
        
        log("delete.sql", sql);
        return executeUpdate(sql, statement);
    }
    
    //Query DB for all parts
    public static String getParts (Statement statement, ArrayList<Part> partCollection){
        try {
            String sql = "select partNumber, location, count, date, SUM(total) as totalSum, id " +
                    "from " + Constants.TABLE +
                    " group by partNumber " +
                    " order by partNumber";
            log("getParts.sql", sql);
            
            partCollection.clear();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String partNumber = rs.getString("partNumber");
                String location = rs.getString("location");
                String count = rs.getString("count");
                String date = rs.getString("date");
                String totalSum = rs.getString("totalSum");
                int id = rs.getInt("id");
                
                Part part = new Part(partNumber, location, count, date, id);
                part.setTotal(totalSum);
                partCollection.add(part);
            }
        } catch (SQLException e){
            String errorMessage = e.toString();
            
            //if errorMessage contains Table ... doesn't exist
            //create the table and modify the errorString
            String regex = ".* Table .* doesn't exist$";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(errorMessage);
            boolean noTable = m.matches();
            
            if (noTable){
                //<editor-fold defaultstate="collapsed" desc="String sql = create table PartInventory">
                String sql = "create table " + Constants.TABLE + "(" +
                            "partNumber text, " +
                            "location text, " +
                            "count text, " +
                            "total text, " +
                            "date text, " +
                            "id int Primary Key Auto_Increment)";//</editor-fold>
                try{
                    statement.execute(sql);
                    errorMessage = Constants.TABLE + " table created";
                } catch (SQLException ex){
                    errorMessage = "Table creation failed";
                }
                return errorMessage;
            }
        }
        return "";
    }
    
    //Query DB for parts where partNumber = partNum
    public static String getDetails(Statement statement, ArrayList<Part> detailsCollection, String partNum) {
        String errorMessage = "";
        try {
            String sql = "select * from " + Constants.TABLE +
                    " where partNumber= " + partNum +
                    " order by location";
            log("getDetails.sql", sql);
            
            detailsCollection.clear();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String partNumber = rs.getString("partNumber");
                String location = rs.getString("location");
                String count = rs.getString("count");
                String date = rs.getString("date");
                int id = rs.getInt("id");
                
                Part part = new Part(partNumber, location, count, date, id);
                detailsCollection.add(part);
            }
        } catch (SQLException e){
            errorMessage = e.toString();
        }
        return errorMessage;
    }
    
    //TODO Fix
    public String calculateTotal(String input){
        int index = 0;
        if (input.contains("@")){
            index  = input.indexOf("@");
        }
        if (input.contains("x")){
            index  = input.indexOf("x");
        }
        if (input.contains("*")){
            index  = input.indexOf("*");
        }

        int total =0;
        if (index > 0) {
            String operand1 = input.substring(0, index);
            String operand2 = input.substring(index + 1);
            
            log("calculateTotal.operand1", operand1);
            log("calculateTotal.operand2", operand2);

            total = Integer.valueOf(operand1) * Integer.valueOf(operand2);
            log("calculateTotal.total", String.valueOf(total));
        }
        else{
            total = Integer.valueOf(input);
            input = "1@"+input;
        }
        //store total and update totalField textView
        //TODO store/update total in DB total entry
        this.total += total;
        



        return input;
    }
    
    //Calculates the total from count String
    public static int getTotal(String count){
        int atIndex=0;
        String firstOperandStr="", secondOperandStr="";
        int firstOperand=0, secondOperand=0, answer=0;

        if(count.contains("@"))
            atIndex = count.indexOf("@");
        else if(count.contains("*"))
            atIndex = count.indexOf("*");
        else if(count.contains("x"))
            atIndex = count.indexOf("x");
        else if(count.contains("+"))
            atIndex = count.indexOf("+");
        else {
            answer = Integer.parseInt(count);
            return answer;
        }
        firstOperandStr = count.substring(0, atIndex);
        secondOperandStr = count.substring(atIndex+1);

        //convert strings to ints to do math
        firstOperand = Integer.parseInt(firstOperandStr);
        secondOperand = Integer.parseInt(secondOperandStr);

        //multiply operators
        if(count.contains("@"))
            answer = firstOperand * secondOperand;
        if(count.contains("*"))
            answer = firstOperand * secondOperand;
        if(count.contains("x"))
            answer = firstOperand * secondOperand;
        //addition operator
        if(count.contains("+"))
            answer = firstOperand + secondOperand;

        return answer;
    }
    
    //Getters and setters
    public String getPartNumber() {
        return partNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getCount() {
        return count;
    }

    public String getTotal() {
        return String.valueOf(total);
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setTotal(String total) {
        this.total = Integer.valueOf(total);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    //Executes an update. Returns errorMessage if SQLException is caught
    private static String executeUpdate(String sql, Statement statement){
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e){
            return e.toString();
        }
        
        return "";
    }
    
    //Surround string with single quotes
    private static String qSurround(String s) {
        return "\'" + s + "\'";
    }
    
    //Returns current date
    public static String getTodaysDate(){
        Date tempDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(tempDate);
    }
    
    //Convinience method for logging 
    public static void log(String tag, String message){
        System.out.println("[LOG] " + tag + "= " + message);
    }
}
