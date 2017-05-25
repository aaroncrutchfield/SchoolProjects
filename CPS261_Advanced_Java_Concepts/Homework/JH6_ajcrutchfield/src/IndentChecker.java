import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

class BadIndentationException extends RuntimeException
{
	BadIndentationException(String error)
	{
		super(error);
	}
}
public class IndentChecker {
	Stack<Integer> indentStack = new Stack<Integer>();

	
	

	private int findFirstNonBlank(String line)
	{
		int i = 0;
		// return index of first non-blank character
		for (i = 0; i < line.length(); i++) {
			char parentChar = line.charAt(i);
			if (parentChar != ' ') {
				break;
			}
		}

		// return -1 if the line doesn't contain a non-blank character
		if (i == line.length())
			i = -1;
		return i;
	}

	private void processLine(String line, int lineNumber)
	{
		int index = findFirstNonBlank(line);



		// Skip blank lines ... i.e. return immediately
		if (line.trim().isEmpty())
			return;

		// If the stack is empty, then push this index onto the stack and return
		if (indentStack.isEmpty()) {
			indentStack.push(index);
			return;
		}

		// If this index > than the top of the stack, then push this index onto the stack and return
		if (index > indentStack.peek()) {
			indentStack.push(index);
			return;
		}

		// Pop off all Indentation indexes > index
		Iterator<Integer> stackIter = indentStack.iterator();
		while(stackIter.hasNext()) {
			int temp = stackIter.next();
			if(temp > index)
				stackIter.remove();
		}

		// At his point the top of the stack should match the current index.  If it 
		// doesn't throw a BadIndentationException.  In the error message, include the source Line Number
		if (indentStack.peek() != index)
			throw new BadIndentationException("Bad indentation at line " + lineNumber);

	}

	public void checkIndentation(String fileName)
	{
		// Clear the stack
		indentStack.clear();

		Scanner input = null;
		try {
			input = new Scanner (new File(fileName));
			int lineNumber = 1;

			// read through the file line by line
			while(input.hasNextLine()) {
				String line = input.nextLine();
				System.out.println(lineNumber + ":" + line);

				// for each line, call processLine to check indentation
				processLine(line, lineNumber);
				lineNumber += 1;
			}
			System.out.println("***"+fileName + " is indented correctly***");
		} 
		catch (BadIndentationException error)
		{
			System.out.println(error);
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("Can't open file: "+fileName);
		}
		finally
		{
			if (input != null)
				input.close();
		}
	}


	public static void main(String[] args) {
		IndentChecker indentChecker = new IndentChecker();

		for (int i=0; i < args.length; i++)
		{
			System.out.println("Processing file: " + args[i]);
			indentChecker.checkIndentation(args[i]);
			System.out.println();
		}
	}
}
