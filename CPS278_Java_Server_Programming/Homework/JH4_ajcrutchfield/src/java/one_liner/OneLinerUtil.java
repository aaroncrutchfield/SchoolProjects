/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one_liner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AaronC
 */
public class OneLinerUtil {
    String filepath;
    
    
    public OneLinerUtil(String filepath){
        this.filepath = filepath;
    }
    
    
    public ArrayList<String> getContents() {
        //open the file
        File file = new File(filepath);
        //ArrayList containing one liners
        ArrayList<String> sb = new ArrayList<>();

        try {
            Scanner scan = new Scanner(file);
            //loop to scan each line of the file
            while (scan.hasNextLine()) {
                sb.add(scan.nextLine());
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            sb.add("No One Liners Found");
            System.out.println("FileNotFound for file: "+ filepath);
        }
        //convert string builder to String
        return sb;
    }
    
    
    public boolean updateFromRequest(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action == null){
            return false; // No form submission ... must be going straight to the servlet
        }
        return true;
    }

    
    public static String init(ServletConfig sc, ServletContext sctx){
        String fullFilePath = "";
        
        String oneLineFile = sc.getInitParameter("oneLineFile");
        String pathSeparator = System.getProperty("file.separator");
        
        System.out.println("oneLineFile= "+oneLineFile);
        //tried storing web directory 
//      fullFilePath = sctx.getRealPath("/") + oneLineFile;
        fullFilePath = sctx.getRealPath("/WEB-INF/") + pathSeparator +oneLineFile;

        System.out.println("fullFilePath= "+fullFilePath);

        return fullFilePath;
    }
}

