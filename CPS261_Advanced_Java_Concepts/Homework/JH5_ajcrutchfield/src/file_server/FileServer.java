package file_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;



public class FileServer extends EchoServer
{
    StringTokenizer parseCommand;
    
    PrintStream outputPs = null;

    public void delete()
    {
        // code for handling the delete command
        // Make sure you check the return code from the 
        // File delete method to print out success/failure status
    	
    	File f = getFile();
    	outputPs.println("****Processing: delete " + f.getName()+"****");
    	
    	if(f.exists()){
    		if (f.delete())
    			outputPs.println("Delete successful");
    		else
    			outputPs.println("Delete failed");
    	}
    	else
    		outputPs.println("File doesn't exist");
    }
    public void rename()
    {
       // code for handling the rename command
        // Make sure you check the return code from the 
        // File rename method to print out success/failure status
    	
    	File f = getFile();
    	outputPs.println("****Processing: rename " + f.getName()+"****");
    	
    	if (f.exists()) {
    	if (f.renameTo(getFile()))
    		outputPs.println("Rename successful");
    	else
    		outputPs.println("Rename failed");
    	}
    	else
    		outputPs.println("File "+f+" doesn't exist");
    }
    public void list()
    {
        // code for handling the list command
    	File f = getFile();
    	outputPs.println("****Listing files for " + f.getAbsolutePath()+"****");
    	
    	String [] dirList = f.list();
    	for (int i = 0; i < dirList.length; i++) {
			outputPs.println(dirList[i]);
		}
    }
    public void size()
    {
        // code for handling the size command
    	File f = getFile();
    	outputPs.println("****Processing: size " + f.getName()+"****");
    	
    	if (f.exists()) {
    	long size = f.length();
    	outputPs.println(f + " size is "+ size + "bytes");
    	}
    	else
    		outputPs.println("File "+f+" doesn't exist");
    }
    public void lastModified()
    {
        // code for handling the lastModified command
    	File f = getFile();
    	outputPs.println("****Processing: lastModified " + f.getName()+"****");
    	
    	if (f.exists()) {
    	long time = f.lastModified();
    	Date d = new Date(time);
    	outputPs.println(f.getName() + " last date modified= " + d);
    	}
    	else
    		outputPs.println("File "+ f + " doesn't exist");
    }
    public void mkdir()
    {
        // code for handling the mkdir command
        // Make sure you check the return code from the 
        // File mkdir method to print out success/failure status
    	File f = new File(getNextToken());
    	outputPs.println("****Processing: mkdir " + f.getName()+"****");
    	f.mkdir();
    	if (f.exists())
    		outputPs.println("mkdir successful");
    	else
    		outputPs.println("mkdir failed");
    }
    public void createFile()
    {
        // code for handling the createFile command
    	String filename = getNextToken();
    	File f = new File(filename);
    	outputPs.println("****Processing: createFile " + f.getName()+"****");
    	String next;
    	
    	try {
			FileWriter outsrc = new FileWriter(filename);
			f.createNewFile();

			if (f.exists()) 
				while(parseCommand.hasMoreTokens()) {
					next = getNextToken();
					outsrc.write(next);
				}
				outputPs.println("createFile successful");
				outsrc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void printFile()
    {
        // code for handling the printFile command
    	File f = getFile();
    	try {
			outputPs.println("****Printing "+ f.getName()+"****");
			
			FileReader insrc = new FileReader(f);
			Scanner in = new Scanner(insrc);
			while(in.hasNextLine()){
				outputPs.println(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			outputPs.println("File "+ f.getName() + " doesn't exist");
		}
    }
       
    void printUsage()
    {
       // process the "?" command
    	outputPs.println("*******Listing commands********");
    	outputPs.println("delete filename");
    	outputPs.println("rename oldFilename newFilename");
    	outputPs.println("size filename");
    	outputPs.println("lastModified filename");
    	outputPs.println("list filename");
    	outputPs.println("printFile filename");
    	outputPs.println("createFile filename");
    	outputPs.println("mkdir filename");
    	outputPs.println("*******************************");
    }

    
    // useful private routine for getting next string on  the command line
    private String getNextToken()
    {
        if (parseCommand.hasMoreTokens())
            return parseCommand.nextToken();
        else
            return null;
    }
    
    // useful private routine for getting a File class from the next string on  the command line
    private File getFile()
    {
        File f = null;
        String fileName = getNextToken();
        if (fileName == null)     
            outputPs.println("Missing a File name");                  
        else
            f = new File(fileName);      

        return f;
    }
 
    public boolean processCommandLine(String line)    
    {
    // if line is not null, then setup the parseCommand StringTokenizer.  Pull off the first string 
    // using getNextToken() and treat it as the "command" to be processed.  

    // It would be good to print out the command you are processing to make your output more readable.

    // If you are using at least java 1.7, you can use a switch statement on command.  
    // Otherwise, resort to if-then-else logic.  Call the appropriate routine to process the requested command:
    // i.e. delete, rename, mkdir list, etc.  
    // return false if command is quit or the line is null, otherwise return true.
    	
    	boolean proceed = true;
    	if(line != null)
    		parseCommand = new StringTokenizer(line);
    	else
    		return false;
    	
    	String command = "";
		try {
			command = getNextToken();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("processing: " + command);
    	switch (command) {
		case "delete":
			delete();
			break;
		case "rename":
			rename();
			break;
		case "size":
			size();
			break;
		case "lastModified":
			lastModified();
			break;
		case "list":
			list();
			break;
		case "printFile":
			printFile();
			break;
		case "createFile":
			createFile();
			break;
		case "mkdir":
			mkdir();
			break;
		case "?":
			printUsage();
			break;
		case "quit":
			proceed = false;
			break;
		default:
			outputPs.println("invalid input");
			break;
		}
    	
    	//echo command being processed and send blank line to outputPs
    	outputPs.println();
    	
    	return proceed;
    }

    public void processStream(InputStream is, OutputStream os)
	{
		System.out.println("FileServer.processStream begins");
		Scanner input = new Scanner(is);
		PrintStream ps = new PrintStream(os);
		outputPs = ps;

		String s = "";
		boolean proceed = true;
		while (input.hasNextLine() && proceed)
		{
			s= input.nextLine();
			proceed = processCommandLine(s);
		}
		input.close();
		ps.close();
		System.out.println("Exitting processStream");
	}
    
    public static void main(String[] args) {

		FileServer fs = new FileServer();
		fs.monitorServer();
		System.out.println("Exitting FileServer");

	}
    
}