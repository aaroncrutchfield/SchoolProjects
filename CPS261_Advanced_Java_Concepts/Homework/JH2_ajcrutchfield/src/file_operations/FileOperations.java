package file_operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileOperations 
{
    StringTokenizer parseCommand;


    public void delete()
    {
        // code for handling the delete command
        // Make sure you check the return code from the 
        // File delete method to print out success/failure status
    	
    	File f = getFile();
    	System.out.println("****Processing: delete " + f.getName()+"****");
    	
    	if(f.exists()){
    		if (f.delete())
    			System.out.println("Delete successful");
    		else
    			System.out.println("Delete failed");
    	}
    	else
    		System.out.println("File doesn't exist");
    	System.out.println();
    }
    public void rename()
    {
       // code for handling the rename command
        // Make sure you check the return code from the 
        // File rename method to print out success/failure status
    	
    	File f = getFile();
    	System.out.println("****Processing: rename " + f.getName()+"****");
    	
    	if (f.exists()) {
    	if (f.renameTo(getFile()))
    		System.out.println("Rename successful");
    	else
    		System.out.println("Rename failed");
    	}
    	else
    		System.out.println("File "+f+" doesn't exist");
    	System.out.println();
    }
    public void list()
    {
        // code for handling the list command
    	File f = getFile();
    	System.out.println("****Listing files for " + f.getAbsolutePath()+"****");
    	
    	String [] dirList = f.list();
    	for (int i = 0; i < dirList.length; i++) {
			System.out.println(dirList[i]);
		}
    	System.out.println();
    }
    public void size()
    {
        // code for handling the size command
    	File f = getFile();
    	System.out.println("****Processing: size " + f.getName()+"****");
    	
    	if (f.exists()) {
    	long size = f.length();
    	System.out.println(f + " size is "+ size + "bytes");
    	}
    	else
    		System.out.println("File "+f+" doesn't exist");
    	System.out.println();
    }
    public void lastModified()
    {
        // code for handling the lastModified command
    	File f = getFile();
    	System.out.println("****Processing: lastModified " + f.getName()+"****");
    	
    	if (f.exists()) {
    	long time = f.lastModified();
    	Date d = new Date(time);
    	System.out.println(f.getName() + " last date modified= " + d);
    	}
    	else
    		System.out.println("File "+ f + " doesn't exist");
    	System.out.println();
    }
    public void mkdir()
    {
        // code for handling the mkdir command
        // Make sure you check the return code from the 
        // File mkdir method to print out success/failure status
    	File f = new File(getNextToken());
    	System.out.println("****Processing: mkdir " + f.getName()+"****");
    	f.mkdir();
    	if (f.exists())
    		System.out.println("mkdir successful");
    	else
    		System.out.println("mkdir failed");
    	System.out.println();
    }
    public void createFile()
    {
        // code for handling the createFile command
    	String filename = getNextToken();
    	File f = new File(filename);
    	System.out.println("****Processing: createFile " + f.getName()+"****");
    	String next;
    	
    	try {
			FileWriter outsrc = new FileWriter(filename);
			f.createNewFile();

			if (f.exists()) 
				while(parseCommand.hasMoreTokens()) {
					next = getNextToken();
					outsrc.write(next);
				}
				System.out.println("createFile successful");
				outsrc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println();
    }

    public void printFile()
    {
        // code for handling the printFile command
    	File f = getFile();
    	try {
			System.out.println("****Printing "+ f.getName()+"****");
			
			FileReader insrc = new FileReader(f);
			Scanner in = new Scanner(insrc);
			while(in.hasNextLine()){
				System.out.println(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File "+ f.getName() + " doesn't exist");
		}
    	System.out.println();
    }
       
    void printUsage()
    {
       // process the "?" command
    	System.out.println("*******Listing commands********");
    	System.out.println("delete filename");
    	System.out.println("rename oldFilename newFilename");
    	System.out.println("size filename");
    	System.out.println("lastModified filename");
    	System.out.println("list filename");
    	System.out.println("printFile filename");
    	System.out.println("createFile filename");
    	System.out.println("mkdir filename");
    	System.out.println("*******************************");
    	System.out.println();
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
            System.out.println("Missing a File name");                  
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
    	
    	String command = getNextToken();
    	
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
			break;
		}
    	return proceed;
    }

    void processCommandFile(String commandFile)
    {
    // Open up a scanner based on the commandFile file name.  Read the commands from this file 
    // using the Scanner line by line.  For each line read, call processCommandLine.   Continue reading 
    // from this file as long as processCommandLine returns true and there are more lines in the file.  
    // At the end, close the Scanner.
    	
    	try {
			FileReader input = new FileReader(commandFile);
			Scanner scanner = new Scanner(input);
			boolean proceed = true;
			
			while(scanner.hasNextLine() && proceed){
				String line = scanner.nextLine();
				proceed = processCommandLine(line);
			}
			scanner.close();
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file input");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public static void main(String[] args) 
    {
        FileOperations fo= new FileOperations();
        for (int i=0; i < args.length; i++)
        {
            System.out.println("\n\n============  Processing " + args[i] +" =======================\n");
            fo.processCommandFile(args[i]);
        }

        System.out.println("Done with FileOperations");
    }
}
