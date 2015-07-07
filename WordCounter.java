package wordCounter;

import javax.swing.JFileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class reads a text file, compares it to a stop words text file and identifies various notable
 * words- unique, most common, etc.
 * 
 * @author Simon Carter
 * @author Richard Vernon
 *
 * Program Name: BSc Computing
 * Submission date: 16-02-14
 */
public class WordCounter {
    final static JFileChooser chooser = new JFileChooser();
    private static ArrayList<String> stopWordsFile;			// storage for stop words file
    private static ArrayList<String> fileToBeProcessed;		// storage for text file to be processed
    
    
    /**
     * This method loads the files to be processed.
     * 
     * @throws IOException On unreadable file.
     */
    public WordCounter() throws IOException {
    	stopWordsFile = loadFile("Choose the Stop Words file");		// get stop words file
    	fileToBeProcessed = loadFile("Choose the file to process"); // get text file
    	fileToBeProcessed = prepTextFile(fileToBeProcessed);		// prepare text file	 
    }
    
    /**
     * This method prepares text file by removing multiple spaces and punctuation, then splits into individual words.
     * 
     * @param list Text to be processed.
     * @return An array of individual words.
     */
    ArrayList<String> prepTextFile(ArrayList<String> list){
    	ArrayList<String> newList = new ArrayList<String>();
        for(String i:list){
        	newList.add(i.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2")
            		.replaceAll("-", " ").replaceAll(" +", " "));    // remove punctuation and multiple spaces
        }
        
        ArrayList<String> words = new ArrayList<String>();
        for(String i:newList){
            String[] indivWords = i.toLowerCase().split("\\s+");	// break the words by space in the text
            words.addAll(Arrays.asList(indivWords)); 				// convert array to arraylist
        }
        return words;
    }
    
    /**
     * This method finds the word that has the specified rank, either including or ignoring stop words.
     * 
     * @param i The rank of the word to be found.
     * @param includeStopWords Whether to include the stop words.
     * @return The word with rank "i".
     */
    String getWord(int i, boolean includeStopWords){
        return getMostFreqWords(includeStopWords).get(i-1);	// get the word of rank "i"
    }
    
    /**
     * This method finds how many unique words are contained in the text, ignores stop words.
     * 
     * @return Count of unique words.
     */
    int countUniqueWords(){
        return getMostFreqWords(false).size();      // get the number of unique words (ignoring stop words)
    }
    
    /**
     * This method the rank of the supplied word.
     * 
     * @param word The word to check the rank of.
     * @param includeStopWords Whether to include stop words.
     * @return The rank of the supplied word.
     */
    int getRank(String word, boolean includeStopWords){
        return getMostFreqWords(includeStopWords).indexOf(word)+1;  //get the rank for specified word
    }
    
    /**
     * This method keeps count of the number of times each word is found and stores them in a hashmap. 
     * 
     * @param includeStopWords Whether to include stop words.
     * @return An arrayList of words, ordered by the number of times they occur.
     */
    private ArrayList<String> getMostFreqWords(boolean includeStopWords){        
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        for(String str:fileToBeProcessed){
        	if(!includeStopWords && stopWordsFile.contains(str))
        		continue;	// skip any stop words in the txt if stop words aren't to be included
        	
        	/*if(!includeStopWords){			// if stop words are not to be included
        		if(stopWordsFile.contains(i))     	// skip if the current word is a stop word
        			continue;				//  *** put into one if statement
        	}*/
        	if(map.containsKey(str)){			// if key already exists in the hashmap 
        		map.put(str, map.get(str)+1);	// increment value held in that key
        	}
        	else{
        		map.put(str, 1);				// otherwise create a new key in hashmap
        	}
        }
        return sortFreqWords(map);
    }
    
    /**
     * This method sorts the passed hashmap by the value and then converts to an arrayList.
     * 
     * @param map The hashmap to be sorted.
     * @return An arrayList of words sorted by occurrence.
     */
    private ArrayList<String> sortFreqWords(HashMap<String,Integer> map){
        ArrayList<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet()); // put hashmap into arrayList so it can be sorted
       
        Collections.sort(list, new Comparator() {
                public int compare(Object o1, Object o2) {	// returns -1 if o1 less than o2, 1 if o1 more than o2, 0 if equal
                    Map.Entry e1 = (Map.Entry) o1;
                    Map.Entry e2 = (Map.Entry) o2;
                    return ((Comparable) e1.getValue()).compareTo(e2.getValue());
                }
         });        //sort the words according to number of times the word appears

        Collections.reverse(list);		// reverse the order of the list
        ArrayList<String> sortedWords = new ArrayList<String>();
        
        for(Entry e:list){     			// move into an ArrayList<String> to pass back
            sortedWords.add((String) e.getKey());
        }
    	return sortedWords;
    }

    /**
     * This method provides the mechanism to choose files to be processed.
     * 
     * @param message Text to be shown in dialog box.
     * @return Text from within file.
     * @throws IOException Thrown if file is not readable.
     */
    private static ArrayList loadFile(String message) throws IOException {
        BufferedReader reader = null;
        ArrayList lines = null;

        System.err.println(message);

        chooser.setDialogTitle(message);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
                String fileName = file.getCanonicalPath();
                reader = new BufferedReader(new FileReader(fileName));
                lines = new ArrayList<>();
                String line = reader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }

            }
        }
        return lines;
    }
}