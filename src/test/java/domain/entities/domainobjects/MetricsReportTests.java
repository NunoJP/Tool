package domain.entities.domainobjects;

import common.LogLineTests;
import org.junit.Test;

import java.text.ParseException;

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
}
