package wordCounter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Simon Carter
 * 
 * 
 * Program Name: BSc Computing
 * Submission date: 16-02-14
 *
 */
public class WordCounterTest {

    static WordCounter wc;
    
    public WordCounterTest() throws IOException {
        
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
    	 wc = new WordCounter();
    }
    
    @Test
    public void testPrepTextFile(){
    	ArrayList<String> list = new ArrayList<String>
    		(Arrays.asList("don't. do' that! Two: boys' hats; up-to-date? Do it's     this- OK")); 
    	ArrayList<String> expected = new ArrayList<String>();
    	expected.add("don't");
    	expected.add("do");
    	expected.add("that");
    	expected.add("two");
    	expected.add("boys");
    	expected.add("hats");
    	expected.add("up");
    	expected.add("to");
    	expected.add("date");
    	expected.add("do");
    	expected.add("it's");
    	expected.add("this");
    	expected.add("ok");
        ArrayList<String> result = wc.prepTextFile(list);
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetWordWithStops(){
        int i = 1;
        boolean includeStopWords = true;
        String expResult = "the";
        String result = wc.getWord(i, includeStopWords);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetWordWithoutStops(){
        int i = 1;
        boolean includeStopWords = false;
        String expResult = "gunpowder";
        String result = wc.getWord(i, includeStopWords);
        assertEquals(expResult, result);
    }

    @Test
    public void testCountUniqueWords(){
        int expResult = 182;
        int result = wc.countUniqueWords();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRankWithStops() throws IOException {
        String word = "the";
        boolean includeStopWords = true;
        int expResult = 1;
        int result = wc.getRank(word, includeStopWords);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetRankWithoutStops() throws IOException {
        String word = "gunpowder";
        boolean includeStopWords = false;
        int expResult = 1;
        int result = wc.getRank(word, includeStopWords);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetRankWordNotInFile() throws IOException {
        String word = "asd";
        boolean includeStopWords = true;
        int expResult = 0;
        int result = wc.getRank(word, includeStopWords);
        assertEquals(expResult, result);
    }

}
