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
public class DeleteUpdate {

    public static void printToScreen(ResultSetMetaData metaData, ResultSet results) throws SQLException {
        //get number of columns and rows
        results.last();
        int numberOfRows = results.getRow();
        int numberOfColumns = metaData.getColumnCount();

        //store column names
        String[] columnNames = new String[numberOfColumns];
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            columnNames[i - 1] = columnName;
        }

        //print results
        results.first();
        for (int i = 0; i < numberOfRows; i++) {

            String columnName = "";
            for (int c = 0; c < numberOfColumns; c++) {
                columnName = columnNames[c];
                String value;
                if (columnName.equals("population")) {
                    value = String.valueOf(results.getInt(columnName));
                } else {
                    value = results.getString(columnName);
                }
                System.out.print(columnName + "= " + value + "  ");
            }
            System.out.println();
            results.next();
        }
    }

    public static void main(String[] args) {
        Connection myConnection = MyConnection.getConnection(args);

        if (myConnection == null) {
            System.out.println("Unable to create connection");
            return;
        }

        try {
            
            //in case table is already modified
            try {
                CreateCities.createTableCities(myConnection);
            } catch (SQLException e) {
                System.out.println("error recreating table");
            }
            
            //Create select statement
            Statement selectStatement = myConnection.createStatement();
            ResultSet results = selectStatement.executeQuery(
                    "select cityName, states.stateName, cities.population, region from cities, states "
                    + "where states.stateName = cities.stateName");

            //get metadata
            ResultSetMetaData metaData = results.getMetaData();

            printToScreen(metaData, results);

            //create statement for update and delete from cities table
            Statement updateStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet updateResults = updateStatement.executeQuery("select * from cities");

            //move to first row
            updateResults.first();

            //delete first row
            updateResults.deleteRow();

            //change population of the 2nd city to 9999
            updateResults.next();
            updateResults.updateInt("population", 9999);
            updateResults.updateRow();

            //separator
            System.out.println("\n\n**********After update and Delete***********\n\n");

            //create statement for last join
            Statement lastStatement = myConnection.createStatement();
            ResultSet lastResults = lastStatement.executeQuery(
                    "select cityName, states.stateName, cities.population, region from cities, states "
                    + "where states.stateName = cities.stateName");

            //get metadata
            ResultSetMetaData lastMetaData = lastResults.getMetaData();

            //print results to screen
            printToScreen(lastMetaData, lastResults);

            selectStatement.close();
            myConnection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

    }

}
