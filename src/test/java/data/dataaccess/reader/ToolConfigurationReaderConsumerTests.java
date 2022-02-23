package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import org.junit.Test;

import static data.dataaccess.common.ToolConfigurationReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.PORTION_SEPARATOR;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.STOP_WORDS_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ToolConfigurationReaderConsumerTests {

    protected ToolConfigurationConsumer getConsumer() {
        ToolConfigurationConsumer consumer = new ToolConfigurationConsumer();
        // baseline
        ToolConfiguration configuration = consumer.getToolConfiguration();
        assertNotNull(configuration);
        return consumer;
    }

    @Test
    public void simpleTest() {
        ToolConfigurationConsumer consumer = getConsumer();
        String word1 = "Word1";
        String word2 = "word2";
        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(STOP_WORDS_TOKEN + PORTION_SEPARATOR + word1 + PORTION_SEPARATOR + word2);

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(2, stopWords.length);
        assertEquals(word1, stopWords[0]);
        assertEquals(word2, stopWords[1]);
    }

    @Test
    public void testProfileNoHeader() {
        ToolConfigurationConsumer consumer = getConsumer();
        String word1 = "Word1";
        String word2 = "word2";
        // setup
        consumer.accept(STOP_WORDS_TOKEN + PORTION_SEPARATOR + word1 + PORTION_SEPARATOR + word2);

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(0, stopWords.length);
    }

    @Test
    public void testEmptyProfile() {
        ToolConfigurationConsumer consumer = getConsumer();

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(0, stopWords.length);
    }

    @Test
    public void testNoStopWords() {
        ToolConfigurationConsumer consumer = getConsumer();

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(STOP_WORDS_TOKEN + PORTION_SEPARATOR);

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(0, stopWords.length);
    }

    @Test
    public void testNoStopWordLine() {
        ToolConfigurationConsumer consumer = getConsumer();

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(0, stopWords.length);
    }

}
