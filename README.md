Concept: Training Data
In machine learning and artificial intelligence, training data is data you input into a model so it ‘learns’ how to do something. For example, to build an AI that can tell if an image contains a cat, you might have to first train it with thousands of images and tell it which ones contain cats and which ones don’t. That way, the model can extract patterns or features about images that contain cats, and then use them in guessing about an image it has never seen before. The thousands of photos input to the model would be the ‘training data’ in this example. In this project, the training data will be collections of tweets obtained from real twitter data.

Concept: Data Cleaning
Generally speaking, higher quality training data produces higher quality results. Now, what exactly makes training data ‘high quality’ depends on the model you are using and the task at hand. For this tweet bot, the training data will be hundreds of tweets from a real twitter account. Using our understanding of what these tweets look like and how our TwitterBot will generate new tweets, we can improve the bot’s performance by filtering and cleaning the raw tweets, yielding ‘cleaned’ training data with which to build the model. For example, we don’t want any URLs to show up in our processed data so we filter out all the tweets that contain URLs.

Concept: Regular Expression
To filter out undesirable tweets, we specify patterns of string data that are considered “bad”. A regular expression is a simple way to define such patterns.

Concept: Markov Chain
The specific model you will use in this assignment is called a Markov Chain. To train your Markov Chain, you’ll pass in tweets as sequences of words, which will then be analyzed in pairs so that your model can figure out which words should follow which other words. Don’t worry if this doesn’t make sense to you yet! We’ll go into a lot more detail about how this works later in the assignment.

Concept: CSV File
CSV stands for Comma Separated Value. It’s a commonly used file format for storing data. A properly formatted CSV file will have the following shape:

line1_value1, line1_value2, ... line1_valueN  
line2_value1, line2_value2, ... line2_valueN  
...
lineK_value1, lineK_value2, ... lineK_valueN 
As you can see, each line of the file has different values that are, well, separated by commas. CSVs are commonly used to represent tables. Each line in the file would be a row in a table, and each value in the line would represent a cell in the table. For example, if I wanted to use a CSV as a phonebook, I’d write something like:

Tupac Shakur, New York City, 212-779-1269, tupac@deathrowrecords.com  
John Lennon, Liverpool, +44 151-496-0367, john@johnlennon.com  
Prince Rogers Nelson, Minneapolis, 612-200-5655, contact@prince.com  
...
This is great because since each “column” contains the same type of data, it’s easy to write code to extract whatever you need from the file. In this particular assignment, we have provided several CSV files for you to try out your code on. You can open a CSV file using notepad or another text editor, where you see the data like above in a CSV format. We recommend not using Excel to avoid possible formatting changes.

In this assignment, one of your tasks will involve figuring out how to extract just the raw tweets from the file. Then, you will work with that raw data in various ways, including cleaning and validating to improve the performance of the TwitterBot (see the Concept: Training Data and Data Cleaning sections).

File Breakdown
The list below gives a brief description of the provided source files. The ones marked by * are the only ones you need to modify.

*FileLineIterator.java - This class is a wrapper around the Java I/O class BufferedReader (see lecture slides here) that provides a simple way to work with file data line-by-line. By writing this class, you are creating a simple, nifty file reading utility for yourself.

*TweetParser.java - This class reads in tweet data from a file, and then cleans, filters and formats the data to improve the quality of the training data. Its interface also makes it easy for you to add that cleaned data to the Markov Chain.

*MarkovChain.java - You will implement a Markov Chain in this class. The MarkovChain will count all the times that certain words follow other words in the training data. In addition to storing that data, it implements Iterator, and does so to provide a convenient way to continuously pick successive random elements. This means a MarkovChain instance is an iterator, which you can call next() and hasNext() on to generate words for your new tweet!

*TwitterBot.java - This is the class where you put everything together. Here, you will use TweetParser to clean and parse tweet data, write logic for adding that data to an instance of MarkovChain, and then use the populated MarkovChain to generate tweets.

ProbabilityDistribution.java - This class is useful for counting occurrences of things. You will use it in building MarkovChain. It contains a Map<T, Integer>, where the keys are instances of objects of type T and the Integer represents the count for the number of times that object occurred. It also provides a convenient way to randomly pick one of its keys.

NumberGenerator.java - This is an interface for any class that can generate numbers. It is used in ProbabilityDistribution for picking keys.

RandomNumberGenerator.java - This class implements NumberGenerator and just generates numbers randomly.

ListNumberGenerator.java - This class implements NumberGenerator and also just generates numbers. Instead of being random, though, it takes a list of numbers as an input, so the user has control over what numbers it outputs. You don’t need to worry about using this class, but our fixDistribution methods use this to guarantee that our ProbabilityDistribution outputs what we want it to.

The configuration of your project files should mirror that of the provided zip file. It has two directories for code, named src/main/java/org/cis120 and src/test/java/org/cis120, and one called files that stores the CSV twitter data.

How These Files Work Together
We have a CSV file of tweets and the first thing we need to do is pass the file path of this CSV file to FileLineIterator.java, which will allow us to iterate through each line of the file.

Then TweetParser.java uses this FileLineIterator to extract the tweet from each line. TweetParser.java also cleans and formats each tweet and breaks each tweet down into sentences and then words. So after all the processing that TweetParser does, we end up with a list of sentences, where each sentence is a list of words.

We then give this list to an instance of MarkovChain, which adds these words to a dictionary as bigrams. The dictionary maps from a word, w, to a ProbabilityDistribution, which stores the number of times another word, m, comes after w in our tweets.

ProbabilityDistribution.java has a function pick(), which returns one of words, m, that follow word w. This function uses a NumberGenerator to choose which word to return. MarkovChain.java uses this pick() function to generate the words in our new tweet.

Finally, we have TwitterBot.java, the class that puts all of this together. TwitterBot has an instance of MarkovChain, which we train with the input reader of tweets. Since the instance of MarkovChain is an iterator, we can repeatedly call next() on the MarkovChain to keep generating words for our new tweet. Every time next() is called, the MakovChain returns a word that is most likely to follow the word that we generated before it.
