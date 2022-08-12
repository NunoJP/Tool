package domain.entities.domainobjects;

import common.LogLineTests;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.Warning;
import org.junit.Test;
import presentation.common.GuiMessages;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static domain.entities.common.WarningTests.makeWarningMessage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MetricsReportTests extends LogLineTests {

    @Test
    public void testStartDateEndDate() throws ParseException {
        MetricsProfile profile = getMetricsProfile();
        setupLogLineData(); // produces 5 lines, start date is ...01, and last date is ...05
        MetricsReport report = new MetricsReport(profile, data);
        assertNotNull(report.getStartDate());
        assertNotNull(report.getEndDate());
        assertTrue(report.getStartDate().toInstant().isBefore(report.getEndDate().toInstant()));
        assertEquals(LogLineTests.DATE_BASE + 1, dateFormat.format(report.getStartDate()));
        assertEquals(LogLineTests.DATE_BASE + 5, dateFormat.format(report.getEndDate()));
    }


    @Test
    public void testLogLevelDistributionAllSamePercentage(){
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[4];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i);
            data[i] = line;
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] logLevelData = report.getLogLevelData();
        String expectedPercentage = "25,00 %";
        for (String[] logLevelDatum : logLevelData) {
            assertEquals(expectedPercentage, logLevelDatum[1]);
        }
    }

    @Test
    public void testLogLevelDistributionOneLargePercentage(){
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[100];
        for (int i = 0; i < 10; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i%2);
            data[i] = line;
        }

        for (int i = 10; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + 3);
            data[i] = line;
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] logLevelData = report.getLogLevelData();
        String expectedPercentageL = "90,00 %";
        String expectedPercentageS = "5,00 %";
        for (String[] logLevelDatum : logLevelData) {
            if (logLevelDatum[0].contains("0") || logLevelDatum[0].contains("1")) {
                assertEquals(expectedPercentageS, logLevelDatum[1]);
            } else {
                assertEquals(expectedPercentageL, logLevelDatum[1]);
            }
        }
    }

    @Test
    public void testLogLevelDistributionSinglePercentage(){
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[100];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + 3);
            data[i] = line;
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] logLevelData = report.getLogLevelData();
        String expectedPercentageL = "100,00 %";
        assertEquals(expectedPercentageL, logLevelData[0][1]);
    }

    @Test
    public void testLogLevelDistributionNoPercentage(){
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[100];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            data[i] = line;
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] logLevelData = report.getLogLevelData();
        assertEquals(0, logLevelData.length);
    }


    @Test
    public void testLogLevelDistributionSmallPercentages(){
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[1000];
        for (int i = 0; i < 10; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i%2);
            data[i] = line;
        }

        for (int i = 10; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + 3);
            data[i] = line;
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] logLevelData = report.getLogLevelData();
        String expectedPercentageL = "99,00 %";
        String expectedPercentageS = "0,50 %";
        for (String[] logLevelDatum : logLevelData) {
            if (logLevelDatum[0].contains("0") || logLevelDatum[0].contains("1")) {
                assertEquals(expectedPercentageS, logLevelDatum[1]);
            } else {
                assertEquals(expectedPercentageL, logLevelDatum[1]);
            }
        }
    }

    @Test
    public void testMostCommonWords() {
        MetricsProfile profile = getMetricsProfile();
        int [] amounts = new int[] { 100, 20, 10, 1, 1 };
        data = new LogLine[132];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] wordsData = report.getMostCommonWordsData();

        assertEquals(amounts.length, wordsData.length);
        for (int i = 0; i < amounts.length; i++) {
            assertEquals(amounts[i], Integer.parseInt(wordsData[i][1]));
        }
    }

    @Test
    public void testMostCommonWordsNoStopWords() {
        MetricsProfile profile = getMetricsProfile();
        int [] amounts = new int[] { 100, 20, 10, 1, 1 };
        data = new LogLine[132];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        MetricsReport report = new MetricsReport(profile, data, new String[0]);
        String[][] wordsData = report.getMostCommonWordsData();

        assertEquals(amounts.length, wordsData.length);
        for (int i = 0; i < amounts.length; i++) {
            assertEquals(amounts[i], Integer.parseInt(wordsData[i][1]));
        }
    }

    @Test
    public void testMostCommonWordsSomeStopWords() {
        MetricsProfile profile = getMetricsProfile();
        int [] amounts = new int[] { 100, 20, 10, 1, 1 };
        data = new LogLine[132];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        String [] stopWords = new String[]{ "Word1", "Word2", "Test" };

        MetricsReport report = new MetricsReport(profile, data, stopWords);
        String[][] wordsData = report.getMostCommonWordsData();

        assertTrue(amounts.length > wordsData.length);
        assertEquals(amounts[0], Integer.parseInt(wordsData[0][1]));
        assertEquals(amounts[3], Integer.parseInt(wordsData[1][1]));
        assertEquals(amounts[4], Integer.parseInt(wordsData[2][1]));

    }

    @Test
    public void testMostCommonWordsAllStopWords() {
        MetricsProfile profile = getMetricsProfile();
        int [] amounts = new int[] { 100, 20, 10, 1, 1 };
        data = new LogLine[132];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        String [] stopWords = new String[]{ "Word0", "Word1", "Word2", "Word3", "Word4" };

        MetricsReport report = new MetricsReport(profile, data, stopWords);
        String[][] wordsData = report.getMostCommonWordsData();

        assertEquals(0, wordsData.length);
    }

    @Test
    public void testMostCommonWordsNoWords() {
        MetricsProfile profile = getMetricsProfile();
        data = new LogLine[132];

        for (int i = 0; i < data.length; i++) {
            data[i] = new LogLine();
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] wordsData = report.getMostCommonWordsData();

        assertEquals(0, wordsData.length);
    }

    private MetricsProfile getMetricsProfile() {
        MetricsProfile profile = new MetricsProfile();
        profile.setHasKeywordHistogram(true);
        profile.setHasMostCommonWords(true);
        profile.setHasKeywordThreshold(true);
        profile.setHasKeywordOverTime(true);
        return profile;
    }


    @Test
    public void testThresholdOccNonCaseSensitive() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = getMetricsProfile();
        Keyword kwd0 = makeKeywordOccurrences(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, 1, false);
        wordKeyword.put(kwd0.getKeywordText().toLowerCase(), kwd0);
        Keyword kwd1 = makeKeywordOccurrences(kwds, "Word1", ThresholdTypeEnum.BIGGER_THAN, 1, false);
        wordKeyword.put(kwd1.getKeywordText().toLowerCase(), kwd1);
        Keyword kwd2 = makeKeywordOccurrences(kwds, "Word2", ThresholdTypeEnum.EQUAL_TO, 1, false);
        wordKeyword.put(kwd2.getKeywordText().toLowerCase(), kwd2);
        Keyword kwd3 = makeKeywordOccurrences(kwds, "Word3", ThresholdTypeEnum.SMALLER_THAN, 2, false);
        wordKeyword.put(kwd3.getKeywordText().toLowerCase(), kwd3);
        Keyword kwd4 = makeKeywordOccurrences(kwds, "Word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, 1, false);
        wordKeyword.put(kwd4.getKeywordText().toLowerCase(), kwd4);
        int [] amounts = new int[] { 1, 2, 1, 1, 1, 100 };
        profile.setKeywords(kwds);
        data = new LogLine[106];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("word" + i);
                data[idx] = line;
                idx++;
            }
            wordValues.put("word" + i, amounts[i]);
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(amounts.length -1, thresholdData.length);
        for (int i = 0; i < amounts.length -1; i++) {
            assertEquals(makeThresholdMessage(wordKeyword.get(thresholdData[i][0].toLowerCase()),
                    String.valueOf(wordValues.get(thresholdData[i][0].toLowerCase())), String.valueOf(data.length)), thresholdData[i][1]);
        }
    }
    @Test
    public void testThresholdOccCaseSensitive() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = getMetricsProfile();
        Keyword kwd0 = makeKeywordOccurrences(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, 1, true);
        wordKeyword.put(kwd0.getKeywordText(), kwd0);
        Keyword kwd1 = makeKeywordOccurrences(kwds, "word1", ThresholdTypeEnum.BIGGER_THAN, 1, true);
        wordKeyword.put(kwd1.getKeywordText(), kwd1);
        Keyword kwd2 = makeKeywordOccurrences(kwds, "word2", ThresholdTypeEnum.EQUAL_TO, 1, true);
        wordKeyword.put(kwd2.getKeywordText(), kwd2);
        Keyword kwd3 = makeKeywordOccurrences(kwds, "word3", ThresholdTypeEnum.SMALLER_THAN, 2, true);
        wordKeyword.put(kwd3.getKeywordText(), kwd3);
        Keyword kwd4 = makeKeywordOccurrences(kwds, "word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, 1, true);
        wordKeyword.put(kwd4.getKeywordText(), kwd4);
        int [] amounts = new int[] { 1, 2, 1, 1, 1, 100 };
        profile.setKeywords(kwds);
        data = new LogLine[106];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
            wordValues.put("Word" + i, amounts[i]);
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(5, thresholdData.length);

        HashMap<String, Integer> expectedResults = new HashMap<>();
        expectedResults.put(kwd0.getKeywordText(), 1);
        expectedResults.put(kwd1.getKeywordText(), 0);
        expectedResults.put(kwd2.getKeywordText(), 0);
        expectedResults.put(kwd3.getKeywordText(), 0);
        expectedResults.put(kwd4.getKeywordText(), 0);


        for (int i = 0; i < 5; i++) {
            assertEquals(
                    makeThresholdMessage(wordKeyword.get(thresholdData[i][0]), String.valueOf(expectedResults.get(thresholdData[i][0])), String.valueOf(data.length)),
                    thresholdData[i][1]
            );
        }


    }


    @Test
    public void testThresholdPercentageLargerOrEqual() {
        // test where the value should generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 11, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, true);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 11, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, true);

        // test where the value should not generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 10, 11, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, false);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 10, 11, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, false);

    }

    @Test
    public void testThresholdPercentageLarger() {
        // test where the value should generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 16, 15, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.BIGGER_THAN, true);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 16, 15, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.BIGGER_THAN, true);

        // test where the value should not generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 15, 16, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.BIGGER_THAN, false);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 15, 16, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.BIGGER_THAN, false);

    }

    @Test
    public void testThresholdPercentageEqual() {
        // test where the value should generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 10, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET, ThresholdTypeEnum.EQUAL_TO, true);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 10, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET, ThresholdTypeEnum.EQUAL_TO, true);

        // test where the value should not generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 9, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET, ThresholdTypeEnum.EQUAL_TO, false);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 9, 10, GuiMessages.WARNING_MESSAGE_PARTICLE_MET, ThresholdTypeEnum.EQUAL_TO, false);

    }

    @Test
    public void testThresholdPercentageSmaller() {
        // test where the value should generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 4, 5, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.SMALLER_THAN, true);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 4, 5, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.SMALLER_THAN, true);

        // test where the value should not generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 5, 4, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.SMALLER_THAN, false);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 5, 4, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, ThresholdTypeEnum.SMALLER_THAN, false);

    }

    @Test
    public void testThresholdPercentageSmallerOrEqual() {
        // test where the value should generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 49, 50, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, true);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 49, 50, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, true);

        // test where the value should not generate warning
        // non case sensitive test
        testThresholdPercentage("Word0", 50, 49, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, false);
        // case sensitive test
        testThresholdPercentageCaseSensitive("Word0", 50, 49, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, false);

    }


    public void testThresholdPercentage(String word, int numberOfOccurrences, int kwdThValue, String effect, ThresholdTypeEnum typeEnum, boolean shouldHaveWarning) {
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = getMetricsProfile();
        Keyword kwd = makeKeywordPercentage(kwds, word, typeEnum, kwdThValue, false);

        int [] amounts = new int[] { numberOfOccurrences, 100 - numberOfOccurrences };
        profile.setKeywords(kwds);
        data = new LogLine[100];
        String wordTrunk = word.substring(0, word.length() -1);

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage(wordTrunk + i);
                data[idx] = line;
                idx++;
            }
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(1, thresholdData.length);
        assertEquals(makeThresholdMessage(kwd, String.valueOf(numberOfOccurrences), String.valueOf(data.length)), thresholdData[0][1]);

        ArrayList<Warning> warningsData = report.getWarningsData();
        if(shouldHaveWarning) {
            assertEquals(1, warningsData.size());
            assertEquals(makeWarningMessage(kwd, effect), warningsData.get(0).getMessage());
        } else {
            assertEquals(0, warningsData.size());
        }

    }


    public void testThresholdPercentageCaseSensitive(String word, int numberOfOccurrences, int kwdThValue, String effect, ThresholdTypeEnum typeEnum, boolean shouldHaveWarning) {
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = getMetricsProfile();
        Keyword kwd = makeKeywordPercentage(kwds, word, typeEnum, kwdThValue, true);

        int [] amounts = new int[] { numberOfOccurrences, 100 - numberOfOccurrences };
        profile.setKeywords(kwds);
        data = new LogLine[100];

        // We use the same word but for the second amount we make it fully CAPS to see if the case sensitive is working
        int idx = 0;
        for (int i = 0; i < amounts[0]; i++) {
            LogLine line = new LogLine();
            line.setMessage(word);
            data[idx] = line;
            idx++;
        }

        for (int i = 0; i < amounts[1]; i++) {
            LogLine line = new LogLine();
            line.setMessage(word.toUpperCase());
            data[idx] = line;
            idx++;
        }


        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(1, thresholdData.length);
        assertEquals(makeThresholdMessage(kwd, String.valueOf(numberOfOccurrences), String.valueOf(data.length)), thresholdData[0][1]);

        ArrayList<Warning> warningsData = report.getWarningsData();
        if(shouldHaveWarning) {
            assertEquals(1, warningsData.size());
            assertEquals(makeWarningMessage(kwd, effect), warningsData.get(0).getMessage());
        } else {
            assertEquals(0, warningsData.size());
        }
    }

    @Test
    public void testThresholdForNonExistingWord() {
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = getMetricsProfile();
        makeKeywordOccurrences(kwds, "WordNon", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, 10, false);
        int [] amounts = new int[] { 10, 15, 10, 5, 10, 50 };
        profile.setKeywords(kwds);
        data = new LogLine[100];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(1, thresholdData.length);
        assertEquals("WordNon Th: 10 Current: 0", thresholdData[0][1]);
    }

    @Test
    public void testExpectedNoThreshold() {
        MetricsProfile profile = getMetricsProfile();
        int [] amounts = new int[] { 10, 15, 10, 5, 10, 50 };
        data = new LogLine[100];

        int idx = 0;
        for (int i = 0; i < amounts.length; i++) {
            for (int am = 0; am < amounts[i]; am++) {
                LogLine line = new LogLine();
                line.setMessage("Word" + i);
                data[idx] = line;
                idx++;
            }
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(0, thresholdData.length);
    }



    private String makeThresholdMessage(Keyword standard, String actualValue, String total) {
        if(ThresholdUnitEnum.PERCENTAGE.getName().equals(standard.getThresholdUnit().getName())) {
            return String.format(GuiMessages.THRESHOLD_VALUE_BASE_PERCENTAGE, standard.getKeywordText(),
                    standard.getThresholdValue(), actualValue, total);
        }
        return String.format(GuiMessages.THRESHOLD_VALUE_BASE_OCCURRENCE, standard.getKeywordText(),
                standard.getThresholdValue(), actualValue);
    }


    private Keyword makeKeywordPercentage(ArrayList<Keyword> kwds, String word, ThresholdTypeEnum comparison, int kwdValue, boolean caseSensitive) {
        return makeKeyword(kwds, word, comparison, kwdValue, caseSensitive, ThresholdUnitEnum.PERCENTAGE);
    }

    private Keyword makeKeywordOccurrences(ArrayList<Keyword> kwds, String word, ThresholdTypeEnum comparison, int kwdValue, boolean caseSensitive) {
        return makeKeyword(kwds, word, comparison, kwdValue, caseSensitive, ThresholdUnitEnum.OCCURRENCES);
    }


    private Keyword makeKeyword(ArrayList<Keyword> kwds, String word, ThresholdTypeEnum comparison, int kwdValue, boolean caseSensitive, ThresholdUnitEnum type) {
        Keyword kwd0 = new Keyword(word, caseSensitive);
        kwd0.setThresholdTrio(comparison, type, new BigDecimal(kwdValue));
        kwds.add(kwd0);
        return kwd0;
    }
}
