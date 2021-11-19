package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/** Tests for FileLineIterator */
public class FileLineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     *
     * You don't need to create any files for your tests though. (Our submission
     * infrastructure actually won't accept any files you make for testing)
     */

    @Test
    public void testHasNextAndNext() {
        FileLineIterator li = new FileLineIterator("files/simple_test_data.csv");
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }
    
    @Test 
    public void testConstructorArgumentException() {
    	
    	assertThrows(IllegalArgumentException.class, 
    			() -> {new FileLineIterator("files/java.java");});
    	
    }
    
    @Test 
    public void testConstructorArgumentExceptionNull() {
    	
    	assertThrows(IllegalArgumentException.class, 
    			() -> {new FileLineIterator(null);});
    	
    }
    @Test
    public void testSingletonHasNext() {
        FileLineIterator li = new FileLineIterator("files/just_one_tweets.csv");
        assertTrue(li.hasNext());
       
    }
    
    @Test
    public void testNormalHasNext() {
        FileLineIterator li = new FileLineIterator("files/simple_test_data.csv");
        assertTrue(li.hasNext());
        li.next();
        assertTrue(li.hasNext());
    }
   
    @Test
    public void testHasNextFalse() {
        FileLineIterator li = new FileLineIterator("files/just_one_tweets.csv");
        assertTrue(li.hasNext());
        li.next();
        assertFalse(li.hasNext());
    }
    
    @Test
    public void testHasNextEmpty() {
        FileLineIterator li = new FileLineIterator("files/empty.csv");
        assertTrue(!li.hasNext());
        
    }
    
    
    @Test
    public void testSingletonNext() {
        FileLineIterator li = new FileLineIterator("files/just_one_tweets.csv");
        assertEquals("twitterbot,2019-11-08 02:03:04,this is a test", li.next());
        assertFalse(li.hasNext());
    }
    @Test
    public void testErrorNext() {
        FileLineIterator li = new FileLineIterator("files/just_one_tweets.csv");
        assertTrue(li.hasNext());
        assertEquals("twitterbot,2019-11-08 02:03:04,this is a test", li.next());
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, 
    			() -> {li.next();});
        
    }
    
    @Test
    public void testNextEmpty() {
        FileLineIterator li = new FileLineIterator("files/empty.csv");
        assertTrue(!li.hasNext());
        assertThrows(NoSuchElementException.class, 
    			() -> {li.next();});
        
    }
    
    @Test
    public void testNormalNext() {
        FileLineIterator li = new FileLineIterator("files/noaa_tweets.csv");
        assertEquals("NOAA,2019-11-15 15:20:17,A major "
        		+ "storm is expected intensify over the weekend "
        		+ "along the Southeast/Mid-Atlantic coasts and deliver"
        		+ " a number of weather impacts including high winds and "
        		+ "coastal flooding. Head to https://t.co/VyWINDk3xP "
        		+ "for the latest in your area. https://t.co/iFH2CrzJTL", li.next());
        li.next();
        li.next();
        assertEquals("NOAA,2019-11-14 20:24:32,Great day hosting the White House"
        		+ " Summit on Partnerships in Ocean Science and Technology!"
        		+ " The United States is poised to lead a new bold era of innovation"
        		+ " that will shape and expand human knowledge of the ocean. #WHoceansummit"
        		+ " @NOAA @NSF @WHCEQ https://t.co/Zf1AHzbU0x", li.next());
        
        
    }
  


}
