package domain.entities.domainobjects;

import common.LogLineTests;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import org.junit.Test;
import presentation.common.GuiMessages;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MetricsReportTests extends LogLineTests {

    @Test
    public void testStartDateEndDate() throws ParseException {
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
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
        MetricsProfile profile = new MetricsProfile();
        data = new LogLine[132];

        for (int i = 0; i < data.length; i++) {
            data[i] = new LogLine();
        }

        MetricsReport report = new MetricsReport(profile, data);
        String[][] wordsData = report.getMostCommonWordsData();

        assertEquals(0, wordsData.length);
    }


    @Test
    public void testThresholdOccNonCaseSensitive() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = new MetricsProfile();
        Keyword kwd0 = makeKeyword(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, 1, false);
        wordKeyword.put(kwd0.getKeywordText().toLowerCase(), kwd0);
        Keyword kwd1 = makeKeyword(kwds, "Word1", ThresholdTypeEnum.BIGGER_THAN, 1, false);
        wordKeyword.put(kwd1.getKeywordText().toLowerCase(), kwd1);
        Keyword kwd2 = makeKeyword(kwds, "Word2", ThresholdTypeEnum.EQUAL_TO, 1, false);
        wordKeyword.put(kwd2.getKeywordText().toLowerCase(), kwd2);
        Keyword kwd3 = makeKeyword(kwds, "Word3", ThresholdTypeEnum.SMALLER_THAN, 2, false);
        wordKeyword.put(kwd3.getKeywordText().toLowerCase(), kwd3);
        Keyword kwd4 = makeKeyword(kwds, "Word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, 1, false);
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
                    String.valueOf(wordValues.get(thresholdData[i][0].toLowerCase()))), thresholdData[i][1]);
        }
    }
    @Test
    public void testThresholdOccCaseSensitive() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = new MetricsProfile();
        Keyword kwd0 = makeKeyword(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, 1, true);
        wordKeyword.put(kwd0.getKeywordText(), kwd0);
        Keyword kwd1 = makeKeyword(kwds, "word1", ThresholdTypeEnum.BIGGER_THAN, 1, true);
        wordKeyword.put(kwd1.getKeywordText(), kwd1);
        Keyword kwd2 = makeKeyword(kwds, "word2", ThresholdTypeEnum.EQUAL_TO, 1, true);
        wordKeyword.put(kwd2.getKeywordText(), kwd2);
        Keyword kwd3 = makeKeyword(kwds, "word3", ThresholdTypeEnum.SMALLER_THAN, 2, true);
        wordKeyword.put(kwd3.getKeywordText(), kwd3);
        Keyword kwd4 = makeKeyword(kwds, "word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, 1, true);
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

        assertEquals(1, thresholdData.length);

        assertEquals(makeThresholdMessage(wordKeyword.get(thresholdData[0][0]),
                    String.valueOf(wordValues.get(thresholdData[0][0]))), thresholdData[0][1]);

    }


    @Test
    public void testThresholdPercentage() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = new MetricsProfile();
        Keyword kwd0 = makeKeyword(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, false);
        wordKeyword.put(kwd0.getKeywordText().toLowerCase(), kwd0);
        Keyword kwd1 = makeKeyword(kwds, "Word1", ThresholdTypeEnum.BIGGER_THAN, false);
        wordKeyword.put(kwd1.getKeywordText().toLowerCase(), kwd1);
        Keyword kwd2 = makeKeyword(kwds, "Word2", ThresholdTypeEnum.EQUAL_TO, false);
        wordKeyword.put(kwd2.getKeywordText().toLowerCase(), kwd2);
        Keyword kwd3 = makeKeyword(kwds, "Word3", ThresholdTypeEnum.SMALLER_THAN, false);
        wordKeyword.put(kwd3.getKeywordText().toLowerCase(), kwd3);
        Keyword kwd4 = makeKeyword(kwds, "Word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, false);
        wordKeyword.put(kwd4.getKeywordText().toLowerCase(), kwd4);
        int [] amounts = new int[] { 10, 15, 10, 5, 10, 50 };
        profile.setKeywords(kwds);
        data = new LogLine[100];

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
                    String.valueOf(wordValues.get(thresholdData[i][0].toLowerCase()))), thresholdData[i][1]);
        }
    }


    @Test
    public void testThresholdPercentageCaseSensitive() {
        HashMap<String, Integer> wordValues = new HashMap<>();
        HashMap<String, Keyword> wordKeyword = new HashMap<>();
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = new MetricsProfile();
        Keyword kwd0 = makeKeyword(kwds, "Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, true);
        wordKeyword.put(kwd0.getKeywordText(), kwd0);
        Keyword kwd1 = makeKeyword(kwds, "Word1", ThresholdTypeEnum.BIGGER_THAN, true);
        wordKeyword.put(kwd1.getKeywordText(), kwd1);
        Keyword kwd2 = makeKeyword(kwds, "Word2", ThresholdTypeEnum.EQUAL_TO, true);
        wordKeyword.put(kwd2.getKeywordText(), kwd2);
        Keyword kwd3 = makeKeyword(kwds, "Word3", ThresholdTypeEnum.SMALLER_THAN, true);
        wordKeyword.put(kwd3.getKeywordText(), kwd3);
        Keyword kwd4 = makeKeyword(kwds, "Word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, true);
        wordKeyword.put(kwd4.getKeywordText(), kwd4);
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
            wordValues.put("Word" + i, amounts[i]);
        }
        MetricsReport report = new MetricsReport(profile, data);
        String[][] thresholdData = report.getKwdThresholdData();

        assertEquals(amounts.length -1, thresholdData.length);
        for (int i = 0; i < amounts.length -1; i++) {
            assertEquals(makeThresholdMessage(wordKeyword.get(thresholdData[i][0]),
                    String.valueOf(wordValues.get(thresholdData[i][0]))), thresholdData[i][1]);
        }
    }

    @Test
    public void testThresholdForNonExistingWord() {
        ArrayList<Keyword> kwds = new ArrayList<>();
        MetricsProfile profile = new MetricsProfile();
        Keyword kwd0 = makeKeyword(kwds, "WordNon", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, false);
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

        assertEquals(0, thresholdData.length);
    }

    @Test
    public void testExpectedNoThreshold() {
        MetricsProfile profile = new MetricsProfile();
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

    private String makeThresholdMessage(Keyword standard, String actualValue) {
        return String.format(GuiMessages.THRESHOLD_VALUE_BASE, standard.getKeywordText(),
                standard.getThresholdValue(), actualValue);
    }


    private Keyword makeKeyword(ArrayList<Keyword> kwds, String word, ThresholdTypeEnum biggerOrEqualThan, boolean caseSensitive) {
        Keyword kwd0 = new Keyword(word, caseSensitive);
        kwd0.setThresholdTrio(biggerOrEqualThan, ThresholdUnitEnum.PERCENTAGE, new BigDecimal("0.1"));
        kwds.add(kwd0);
        return kwd0;
    }

    private Keyword makeKeyword(ArrayList<Keyword> kwds, String word0, ThresholdTypeEnum biggerOrEqualThan, int i2, boolean caseSensitive) {
        Keyword kwd0 = new Keyword(word0, caseSensitive);
        kwd0.setThresholdTrio(biggerOrEqualThan, ThresholdUnitEnum.OCCURRENCES, new BigDecimal(i2));
        kwds.add(kwd0);
        return kwd0;
    }
}
