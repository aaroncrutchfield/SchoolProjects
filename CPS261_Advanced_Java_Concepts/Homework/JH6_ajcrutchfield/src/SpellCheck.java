/**
 * Created by iOutDoU7 on 11/14/2015.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellCheck {

    // We could have also used TreeSet for the dictionary
    private HashSet<String> dictionary = new HashSet<String>();
    private TreeSet<String> miss_spelled_words = new TreeSet<String>();

    public SpellCheck() throws FileNotFoundException {

        // Add all of the words from "dictionary.txt" to the dictionary HashSet
        Scanner in = new Scanner(new File("dictionary.txt"));
        while (in.hasNext()){
            dictionary.add(in.next());
        }
//        System.out.println("exitting spellcheck");
    }

    public void checkSpelling(String fileName) throws FileNotFoundException
    {
        System.out.println("======== Spell checking " + fileName + " =========");
        // Clear miss_spelled_words
        miss_spelled_words.clear();
        int count = 0;

        // Read in each line from "fileName"
        Scanner in = new Scanner(new File(fileName));
        while (in.hasNextLine()){
            String line = in.nextLine();
            boolean firstTime = true;
            count += 1;

            // For each line, break the line into words using the following StringTokenizer
            StringTokenizer st = new StringTokenizer(line, " \t,.;:-%'\"");

            while (st.hasMoreTokens()){
                String word = st.nextToken();
                char firstLetter = word.charAt(0);
                
                if ((firstLetter >= 'a' && firstLetter <= 'z') || (firstLetter >= 'A' && firstLetter <= 'Z')){
                    word = word.toLowerCase();
                    if(!dictionary.contains(word)){
                    	
                        miss_spelled_words.add(word);
                        if (firstTime)
                        	System.out.println(count + ": " + line);
                        System.out.println(word + " is not in the dictionary. Add to dictionary? (y/n)");

                        Scanner input = new Scanner(System.in);
                        String add = input.next();
                        
                        firstTime = false;

                        if (add == "y"){
                            dictionary.add(input.next());
                        }
                    }
                }
            }
            
        }

        // lower case each word obtained from the StringTokenizer
        // skip word if the first character is not a letter

        // Skip over word if it can be found in either dictionary, or miss_spelled_words
        // If word ends with 's', then try the singular version of the word in the dictionary and miss_spelled_words ... skip if found

        // Print line containing miss-spelled word(make sure you only print it once if multiple miss-spelled words are found on this line)

        // Ask the user if he wants the word added to the dictionary or the miss-spelled words and add word as specified by the user
    }

    public void dump_miss_spelled_words()
    {
        // Print out the miss-spelled words
        Iterator<String> badWords = miss_spelled_words.iterator();
        
        System.out.println("*****Miss Spelled Words*****");

        while (badWords.hasNext()){
            System.out.println(badWords.next() + " ");
        }
    }


    public static void main(String[] args) {

        try {
            SpellCheck spellCheck = new SpellCheck();

            for (int i=0; i < args.length; i++)
            {
                spellCheck.checkSpelling(args[i]);
                spellCheck.dump_miss_spelled_words();
            }
        }
        
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }

    }
}
