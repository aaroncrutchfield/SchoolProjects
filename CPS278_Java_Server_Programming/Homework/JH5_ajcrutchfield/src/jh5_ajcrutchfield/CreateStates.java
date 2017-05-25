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

public class CreateStates {

    public static void main(String[] args) {

        Connection myConnection = MyConnection.getConnection(args);
        String createTable;
        createTable = "create table states "
                + "(stateName varchar(20), "
                + "region ENUM('east', 'midwest', 'west', 'south', 'southwest'), "
                + "largestCity varchar(20), "
                + "capital varchar(20), "
                + "population int)";

        Statement statement;

        if (myConnection == null) {
            System.out.println("Unable to create connection");
            return;
        }

        try {
            statement = myConnection.createStatement();
            try{
            statement.executeUpdate("drop table states");
            } catch (SQLException e){
                System.out.println("states table doesn't exist");
            }
            statement.executeUpdate(createTable);

            String insert1 = "insert into states "
                    + "values('Michigan', 'midwest', 'Detroit', 'Lansing', 8000000)";
            String insert2 = "insert into states "
                    + "values('Texas', 'southwest ', 'Dallas ', 'Austin ', 10000000)";
            String insert3 = "insert into states "
                    + "values('California', 'west', 'Los Angelos', 'Sacramento', 20000000)";
            String insert4 = "insert into states "
                    + "values('Ohio ', 'midwest ', 'Cleveland ', 'Columbus ', 8500000)";

            statement.executeUpdate(insert1);
            statement.executeUpdate(insert2);
            statement.executeUpdate(insert3);
            statement.executeUpdate(insert4);

            //Create select statement
            Statement selectStatement = myConnection.createStatement();
            ResultSet results = selectStatement.executeQuery("select * from states");
            
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
            
            statement.close();
            myConnection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

    }

}
