package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/** Tests for MarkovChain */
public class MarkovChainTest {

    /*
     * Writing tests for Markov Chain can be a little tricky.
     * We provide a few tests below to help you out, but you still need
     * to write your own.
     */

    @Test
    public void testAddBigram() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        assertTrue(mc.chain.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.chain.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertEquals(1, pd.count("2"));
    }

    @Test
    public void testTrain() {
        MarkovChain mc = new MarkovChain();
        String sentence = "1 2 3";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(3, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(1, pd2.count("3"));
        ProbabilityDistribution<String> pd3 = mc.chain.get("3");
        assertTrue(pd3.getRecords().containsKey(null));
        assertEquals(1, pd3.count(null));
    }

    @Test
    public void testWalk() {
        /*
         * Using the sentences "CIS 120 rocks" and "CIS 120 beats CIS 160",
         * we're going to put some bigrams into the Markov Chain.
         *
         * While in the real world, we want the sentence we output to be random,
         * we don't want this in testing. For testing, we want to modify our
         * ProbabilityDistribution such that it will output a predictable chain
         * of words.
         *
         * Luckily, we've provided a `fixDistribution` method that will do this
         * for you! By calling `fixDistribution` with a list of words that you
         * expect to be output, the ProbabilityDistributions will be modified to
         * output your words in that order.
         *
         * See our below test for an example of how to use this.
         */

        String[] expectedWords = { "CIS", "120", "beats", "CIS", "120", "rocks" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 120 rocks";
        String sentence2 = "CIS 120 beats CIS 160";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());

        mc.reset("CIS"); // we start with "CIS" since that's the word our desired walk starts with
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords)));

        for (int i = 0; i < expectedWords.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords[i], mc.next());
        }

    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testAddBigramException() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, 
    			() -> {mc.addBigram(null, null);});
    }
    @Test
    public void testAddBigramMultipleTimes() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("One", "Two");
        mc.addBigram("One", "Three");
        assertTrue(mc.chain.containsKey("One"));
        assertTrue(!mc.chain.containsKey("Two"));
        ProbabilityDistribution<String> pd = mc.chain.get("One");
        assertTrue(!pd.getRecords().containsKey("One"));
        assertTrue(pd.getRecords().containsKey("Two"));
        assertTrue(pd.getRecords().containsKey("Three"));
        assertEquals(1, pd.count("Two"));
        assertEquals(1, pd.count("Three"));
        
    }
    
    @Test
    public void testAddBigramMultipleSame() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("One", "Two");
        mc.addBigram("One", "Two");
        assertTrue(mc.chain.containsKey("One"));
        assertTrue(!mc.chain.containsKey("Two"));
        ProbabilityDistribution<String> pd = mc.chain.get("One");
        assertTrue(!pd.getRecords().containsKey("One"));
        assertTrue(pd.getRecords().containsKey("Two"));
        assertEquals(2, pd.count("Two"));
    }
    
    @Test
    public void testTrainNull() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, 
    			() -> {mc.train(null);});
        
    }
    
    @Test
    public void testTrainNormal() {
        MarkovChain mc = new MarkovChain();
        String sentence = "i love love i things";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(3, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("i");
        assertTrue(pd1.getRecords().containsKey("love"));
        assertEquals(1, pd1.count("love"));
        assertEquals(1, pd1.count("things"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("love");
        assertTrue(pd2.getRecords().containsKey("love"));
        assertTrue(pd2.getRecords().containsKey("i"));
        assertEquals(1, pd2.count("love"));
        assertEquals(1, pd2.count("i"));
        assertTrue(!pd2.getRecords().containsKey(null));
        assertEquals(0, pd2.count(null));
        ProbabilityDistribution<String> pd3 = mc.chain.get("things");
        assertTrue(pd3.getRecords().containsKey(null));
        assertEquals(1, pd3.count(null));
    }
    
    @Test
    public void testTrainNormalDoubleWord() {
        MarkovChain mc = new MarkovChain();
        String sentence = "i love love";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(2, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("i");
        assertTrue(pd1.getRecords().containsKey("love"));
        assertEquals(1, pd1.count("love"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("love");
        assertTrue(pd2.getRecords().containsKey("love"));
        assertEquals(1, pd2.count("love"));
        assertTrue(pd2.getRecords().containsKey(null));
        assertEquals(1, pd2.count(null));
    }
    
    @Test
    public void testResetNull() {

        MarkovChain mc = new MarkovChain();
        mc.reset(null);
        assertTrue(!mc.hasNext());
    }
    @Test
    public void testResetNormalCase() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        assertEquals("pie", mc.next());

    }
    
    @Test
    public void testResetEdgeCase() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("none");
        assertTrue(mc.hasNext());
        assertEquals("none", mc.next());
        assertTrue(!mc.hasNext());
        assertThrows(NoSuchElementException.class, 
    			() -> {mc.next();});
       
    }
  
    @Test
    public void testResetSingleCase() {

        MarkovChain mc = new MarkovChain();
        
        List<String> list = new LinkedList<String>();
        list.add("pie");
        Iterator<String> iter = list.iterator();
        
        
        mc.train(iter);
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        mc.next();
        
       
    }
    
    @Test
    public void testHasNextNormalCase() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        mc.next();
        assertTrue(mc.hasNext());
        mc.next();
        assertTrue(!mc.hasNext()); 
    }
    
    @Test
    public void testHasNextEdgeCase() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("is");
        
        assertTrue(mc.hasNext());
        assertEquals("is", mc.next());
        assertTrue(!mc.hasNext());

    }
    
    @Test
    public void testHasNextSingleCase() {

        MarkovChain mc = new MarkovChain();
        
        List<String> list = new LinkedList<String>();
        list.add("pie");
        Iterator<String> iter = list.iterator();
        
        
        mc.train(iter);
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        mc.next();
        assertTrue(!mc.hasNext());
        
       
    }
    @Test
    public void testNextNormalCase() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        assertEquals("pie", mc.next());
        assertTrue(mc.hasNext());
        assertEquals("is", mc.next());
        assertTrue(!mc.hasNext());
 
    }
    
    @Test
    public void testNextException() {

        MarkovChain mc = new MarkovChain();
        
        
        String sentence1 = "pie is";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        
        mc.reset("is");
        
        assertTrue(mc.hasNext());
        assertEquals("is", mc.next());
        assertThrows(NoSuchElementException.class, 
    			() -> {mc.next();});

    }
    
    @Test
    public void testNextSingleCase() {

        MarkovChain mc = new MarkovChain();
        
        List<String> list = new LinkedList<String>();
        list.add("pie");
        Iterator<String> iter = list.iterator();
        
        
        mc.train(iter);
        
        mc.reset("pie");
        
        assertTrue(mc.hasNext());
        assertEquals("pie", mc.next());

    }
    

    
    
    
    
    
    
    
}
