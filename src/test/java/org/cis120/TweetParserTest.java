package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;

/** Tests for TweetParser */
public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<String>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<String>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(
                " This is a tweet.",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 3
                )
        );
    }

    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testCsvFileToTrainingDataSimpleCSV() {
        List<List<String>> tweets = TweetParser
                .csvFileToTrainingData("files/simple_test_data.csv", 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvFileToTweetsSimpleCSV() {
        List<String> tweets = TweetParser.csvFileToTweets("files/simple_test_data.csv", 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    
    @Test
    public void testExtractColumnEdge() {
        assertNull(
                null,
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", -2
                )
        );
    }
    
    @Test
    public void testExtractTooManyColumn() {
        assertNull(
                null,
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 100
                )
        );
    }
    
    @Test
    public void testExtractColumnNullLine() {
        assertNull(
                null,
                TweetParser.extractColumn(
                        null, 3
                )
        );
    }
   
    @Test
    public void testExtractColumnFirstColumn() {
        assertEquals(
                "wrongColumn",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 0
                )
        );
    }
    
    @Test
    public void testCsvFileToTweetsNullPath() {
        
    	assertThrows(IllegalArgumentException.class, 
    			() -> {TweetParser.csvFileToTweets(null, 1);});
    }
    
    @Test
    public void testCsvFileToTweetsSingletonV() {
        List<String> tweets = TweetParser.csvFileToTweets("files/just_one_tweets.csv", 2);
        List<String> expected = new LinkedList<String>();
        expected.add("this is a test");
        assertEquals(expected, tweets);
    }
    
    @Test
    public void parseAndCleanSentenceDoubleSpace() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc ");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }
    
    @Test
    public void parseAndCleanSentenceMultipleCapital() {
        List<String> sentence = TweetParser.parseAndCleanSentence("I Love To Cook");
        List<String> expected = new LinkedList<String>();
        expected.add("i");
        expected.add("love");
        expected.add("to");
        expected.add("cook");
        assertEquals(expected, sentence);
    }
    
    @Test
    public void testParseAndCleanTweetNull() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet(" ");
        List<List<String>> expected = new LinkedList<List<String>>();
        
        assertEquals(expected, sentences);
    }
    
    @Test
    public void testParseAndCleanTweetNull2() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("");
        List<List<String>> expected = new LinkedList<List<String>>();
        
        assertEquals(expected, sentences);
    }
    @Test
    public void testParseAndCleanNormal() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("I love pie. I love cakes");
        List<List<String>> expected = new LinkedList<List<String>>();
        List<String> list1 = new LinkedList<String>();
        list1.add("i");
        list1.add("love");
        list1.add("pie");
        List<String> list2 = new LinkedList<String>();
        list2.add("i");
        list2.add("love");
        list2.add("cakes");
        expected.add(list1);
        expected.add(list2);
        assertEquals(expected, sentences);
    }
    @Test
    public void testCsvFileToTrainingDataError() {
    	assertThrows(IllegalArgumentException.class, 
    			() -> {TweetParser
                    .csvFileToTrainingData(null, 1);});
        
    }
    
    @Test
    public void testCsvFileToTrainingDataSingleton() {
        List<List<String>> tweets = TweetParser
                .csvFileToTrainingData("files/just_one_tweets.csv", 2);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("this is a test".split(" ")));
        assertEquals(expected, tweets);
    }

    ////
  
    


    
    

}
