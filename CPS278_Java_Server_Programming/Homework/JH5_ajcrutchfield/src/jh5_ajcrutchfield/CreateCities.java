/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jh5_ajcrutchfield;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author AaronC
 */
public class CreateCities {
    
    
    public static void createTableCities(Connection myConnection) throws SQLException{
        String createTable;
        createTable = "create table cities "
                + "(cityName varchar(20) PRIMARY KEY, "
                + "stateName varchar(20), "
                + "population int)";

        Statement statement;
        
        statement = myConnection.createStatement();
            try{
            statement.executeUpdate("drop table cities");
            } catch (SQLException e){
                System.out.println("states table doesn't exist");
            }
            statement.executeUpdate(createTable);

            statement.executeUpdate("insert into cities values('San Francisco', 'California', 980000)");
            statement.executeUpdate("insert into cities values('San Pedro', 'California', 120000)");
            statement.executeUpdate("insert into cities values('Fresno', 'California', 500000)");
            statement.executeUpdate("insert into cities values('Ann Arbor', 'Michigan', 140000)");
            statement.executeUpdate("insert into cities values('Lansing', 'Michigan', 90000)");
            statement.executeUpdate("insert into cities values('Detroit', 'Michigan', 2100000)");
            statement.executeUpdate("insert into cities values('Austin', 'Texas', 500000)");
            statement.executeUpdate("insert into cities values('Cleveland', 'Ohio', 1200000)");
            statement.executeUpdate("insert into cities values('Columbus', 'Ohio', 200000)");
            statement.executeUpdate("insert into cities values('Dallas', 'Texas', 2100000)");
            
            //clos
            statement.close();
    }
    
    public static void main(String[] args) {
        Connection myConnection = MyConnection.getConnection(args);


        if (myConnection == null) {
            System.out.println("Unable to create connection");
            return;
        }

        try {

            createTableCities(myConnection);

            //Create select statement
            Statement selectStatement = myConnection.createStatement();
            ResultSet results = selectStatement.executeQuery("select * from cities");
            
            //get metadata
            ResultSetMetaData metaData = results.getMetaData();
            
            //get number of columns and rows
            results.last();
            int numberOfRows = results.getRow();
            int numberOfColumns = metaData.getColumnCount();
            
            //store column names
            String [] columnNames = new String[numberOfColumns];
            for (int i = 1; i <= numberOfColumns; i++) {
                String columnName = metaData.getColumnName(i);
                columnNames[i-1] = columnName;
            }
            
            //print results
            results.first();
            for (int i = 0; i < numberOfRows; i++) {
                
                String columnName = "";
                for (int c = 0; c < numberOfColumns; c++) {
                    columnName = columnNames[c];
                    String value;
                    if (columnName.equals("population"))
                        value = String.valueOf(results.getInt(columnName));
                    else
                        value = results.getString(columnName);
                    System.out.print(columnName + "= " +value + "  ");
                }
                System.out.println();
                results.next();
            }
            
//            statement.close();
            myConnection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

    }
}
