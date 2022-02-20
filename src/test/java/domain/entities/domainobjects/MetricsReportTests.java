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

}
