package reading_with_exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Reading_With_Exceptions {

	void process(String inputFilename)
	{
		Scanner scanner = null;
		PrintStream printStream = null;
		String outputFileName = "";
		
		// Here is where your work goes ... Steps that you will need to do:
		// 1.) Create a Scanner from the inputFilename.  Catch exceptions from errors.
		try {
			FileInputStream fi = new FileInputStream(inputFilename);
			scanner = new Scanner(fi);
			
			// 2.) Read the first String from the file and use it to create a PrintStream
			//     catching appropriate exceptions
			outputFileName = scanner.next();
			FileOutputStream fo = new FileOutputStream(outputFileName);
			
			printStream = new PrintStream(fo);
			printStream.printf("Output name: %s%n%n", outputFileName);
			// 3.) Using hasNextInt and nextInt, carefully read the count integer.  
			//          I recommend -1 for a count value if it is bad to indicate reading ALL
			int intCount = 0;
			if (scanner.hasNextInt() == true)
				intCount = scanner.nextInt();
			else
				intCount = -1;
			
			// 4.) Use copyNumbers method described below to complete the job
			copyNumbers(scanner, printStream, intCount);
		} catch (FileNotFoundException e) {
			System.out.println("Filename '" + inputFilename + "' not found!");
		}
		
		// 5.) Close Scanner and PrintStream objects
		finally {
			if (scanner != null)
				scanner.close();
			if (printStream != null)
				printStream.close();
		}
		
		// 6.) Call printToScreen to copy the output file to the screen
		printToScreen(outputFileName);

	}

	// The following routine is called to complete the job of copying integers to
	// the output file:
	// scan - a Scanner object to copy integers from
	// ps - A PrintStream to write the integers to
	// numIntsToRead - number of integers to read.  A value of -1 ==> read all integers

	void copyNumbers(Scanner scan, PrintStream ps, int numIntsToRead)
	{
		// hasNext() can be used to see if the scan object still has data
		// Note that hasNextInt() can be used to see if an integer is present
		// nextInt() will read an integer
		// next() can be used to skip over bad integers
		int counter = 0;
		while (scan.hasNext()) {

			try {
				if (numIntsToRead == -1) {
					int i = scan.nextInt();
					ps.printf("%3d", i);
					counter += 1;
					if ((counter % 10) == 0) 
						ps.println("");
				}

				if (counter >= 0 && counter < numIntsToRead) {
					int i = scan.nextInt();
					ps.printf("%3d", i);
					counter += 1;
					if ((counter % 10) == 0) 
						ps.println("");
				}
			} catch (Exception e) {
				System.out.println("Skipped bad data");
				scan.next();
			}
			
			if (numIntsToRead == counter)
				break;
		}


	}


	public static void main(String[] args) {
		Reading_With_Exceptions rwe = new Reading_With_Exceptions();
		for (int i=0; i < args.length; i++)
		{
			System.out.println("\n\n=========== Processing "+ args[i] + " ==========\n");
			rwe.process(args[i]);
		}

	}

	// For the last step, we Copy the contents of the file to the screen
	private void printToScreen(String filename)
	{
		Scanner scan = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			scan = new Scanner(fis);
			while (scan.hasNextLine())
			{
				System.out.println(scan.nextLine());
			}
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("printToScreen: can't open: " + filename);
		}
		finally
		{
			if (scan != null)
				scan.close();
		}
	}// end of printToScreen
} // end of class
