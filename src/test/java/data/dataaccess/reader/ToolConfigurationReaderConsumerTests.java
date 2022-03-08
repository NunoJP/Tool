package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.common.WarningLevel;
import org.junit.Test;

import java.awt.Color;
import java.util.HashMap;

import static data.dataaccess.common.ToolConfigurationReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.PORTION_SEPARATOR;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.STOP_WORDS_TOKEN;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.WARNING_COLORS_DEFAULT_VALUES;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.WARNING_COLORS_TOKEN;
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


    @Test
    public void testStopWordsAndWarningColors() {
        ToolConfigurationConsumer consumer = getConsumer();
        String word1 = "Word1";
        String word2 = "word2";
        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(STOP_WORDS_TOKEN + PORTION_SEPARATOR + word1 + PORTION_SEPARATOR + word2);
        consumer.accept(WARNING_COLORS_TOKEN + PORTION_SEPARATOR + WARNING_COLORS_DEFAULT_VALUES);

        ToolConfiguration toolConfiguration = consumer.getToolConfiguration();
        String[] stopWords = toolConfiguration.getStopWords();
        assertEquals(2, stopWords.length);
        assertEquals(word1, stopWords[0]);
        assertEquals(word2, stopWords[1]);

        HashMap<WarningLevel, Color> warningColorMap = toolConfiguration.getWarningColorMap();
        assertEquals(new Color(0xFFFDF7), warningColorMap.get(WarningLevel.NONE));
        assertEquals(new Color(0xC21510), warningColorMap.get(WarningLevel.CRITICAL));
        assertEquals(new Color(0xDE4909), warningColorMap.get(WarningLevel.HIGH));
        assertEquals(new Color(0xDE9009), warningColorMap.get(WarningLevel.MEDIUM));
        assertEquals(new Color(0xE6CC0E), warningColorMap.get(WarningLevel.LOW));
        assertEquals(new Color(0x17ABE6), warningColorMap.get(WarningLevel.INFO));
    }

}
