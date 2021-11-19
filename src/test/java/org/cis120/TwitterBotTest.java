package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/** Tests for TwitterBot class */
public class TwitterBotTest {

    String simpleData = "files/simple_test_data.csv";
    String testData = "files/test_data.csv";

    /*
     * This tests whether your TwitterBot class itself is written correctly
     *
     * This test operates very similarly to our MarkovChain tests in its use of
     * `fixDistribution`, so make sure you know how to test MarkovChain before
     * testing this!
     */
    @Test
    public void simpleTwitterBotTest() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".",
                        "the", "end", "should", "come", "."
                )
        );

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(12));
        assertEquals(expected, actual);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    @Test
    public void testConstructorEdge() {

		
		assertThrows(IllegalArgumentException.class, 
    			() -> {new TwitterBot("fake file", 3);});
    		
    }
    @Test
    public void testConstructorEdgeNull() {

		
		assertThrows(IllegalArgumentException.class, 
    			() -> {new TwitterBot(null, 3);});
		
    		
    }
    @Test
    public void testConstructorEdgeNegativeColumn() {

		TwitterBot t = new TwitterBot(simpleData, -5);
        

        String expected = "";
        String actual = TweetParser.replacePunctuation(t.generateTweet(40));
        assertEquals(expected, actual);
    		
    }
    
    @Test
    public void testConstructorEdgeHighColumn() {

		TwitterBot t = new TwitterBot(simpleData, 40);
        

        String expected = "";
        String actual = TweetParser.replacePunctuation(t.generateTweet(40));
        assertEquals(expected, actual);
    		
    }
    
    
    @Test
    public void testWriteStringsToFileSingleton() {
    	List<String> writingString = new LinkedList<String>();
    	writingString.add("I love pie");
    	String filepath = "files/write.csv";
    	TwitterBot t = new TwitterBot(filepath, 3);
    	t.writeStringsToFile(writingString, filepath, false);
    }
    
    @Test
    public void testWriteStringsToFileManyLines() {
    	List<String> writingString = new LinkedList<String>();
    	writingString.add("I love pie");
    	writingString.add("I love cake");
    	String filepath = "files/write.csv";
    	TwitterBot t = new TwitterBot(simpleData, 3);
    	t.writeStringsToFile(writingString, filepath, false);
    }
    
    @Test
    public void testWriteStringsFilePath() {
    	List<String> writingString = new LinkedList<String>();
    	writingString.add("I love to eat pie");
    	writingString.add("I love to eat cake");
    	
    	TwitterBot t = new TwitterBot(simpleData, 3);
    	t.writeStringsToFile(writingString, "fakefile", true);
    	
//    	assertThrows(IllegalArgumentException.class, 
//    			() -> {t.writeStringsToFile(writingString, "fakefile", false);});
    }
    
    @Test
    public void testGenerateTweetSingleton() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "is", "a", "test", "."
                )
        );

        TwitterBot t = new TwitterBot("files/just_one_tweets.csv", 2);
        t.fixDistribution(desiredTweet);
        

        String expected = "this is a test.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(4));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGenerateTweetNegNumWord() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "is", "a", "test", "."
                )
        );

        TwitterBot t = new TwitterBot("files/just_one_tweets.csv", 2);
        t.fixDistribution(desiredTweet);
        

        assertThrows(IllegalArgumentException.class, 
    			() -> {TweetParser.replacePunctuation(t.generateTweet(-5));});

    }
    
    @Test
    public void testGenerateTweetZeroNumWord() {

        TwitterBot t = new TwitterBot("files/empty.csv", 0);
        
        String actual = TweetParser.replacePunctuation(t.generateTweet(10));
        assertEquals("", actual);
        

        
    }
    


    
}
