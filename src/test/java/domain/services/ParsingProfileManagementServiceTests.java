package domain.services;

import data.dataaccess.reader.ParsingProfileReader;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParsingProfileManagementServiceTests {

    private static final Integer EXPECTED_ID_1 = 1;
    private static final Integer EXPECTED_ID_2 = 2;
    public static final String EXPECTED_NAME_1 = "Test 1";
    public static final String EXPECTED_NAME_2 = "Test 2";

    @Test
    public void testGetMetricsProfiles() {
        ParsingProfileManagementService service = new ParsingProfileManagementServiceOverride();
        ParsingProfileDo[] metricsProfiles = service.getParsingProfiles();
        assertEquals(EXPECTED_ID_1, metricsProfiles[0].getId());
        assertEquals(EXPECTED_NAME_1, metricsProfiles[0].getName());
        assertEquals(EXPECTED_ID_2, metricsProfiles[1].getId());
        assertEquals(EXPECTED_NAME_2, metricsProfiles[1].getName());
    }

    private class ParsingProfileManagementServiceOverride extends ParsingProfileManagementService {
        public ParsingProfileManagementServiceOverride() {
            reader = new ParsingProfileReaderOverride();
        }
    }

    private class ParsingProfileReaderOverride extends ParsingProfileReader {
        public ParsingProfile[] getParsingProfiles() {
            ParsingProfile p1 = new ParsingProfile(EXPECTED_ID_1, EXPECTED_NAME_1);
            ParsingProfile p2 = new ParsingProfile(EXPECTED_ID_2, EXPECTED_NAME_2);
            return new ParsingProfile[] { p1, p2 };
        }
    }
}
