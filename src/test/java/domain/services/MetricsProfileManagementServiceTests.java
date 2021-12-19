package domain.services;

import data.dataaccess.reader.MetricsProfileReader;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.domainobjects.MetricsProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetricsProfileManagementServiceTests {

    private static final Integer EXPECTED_ID_1 = 1;
    private static final Integer EXPECTED_ID_2 = 2;
    public static final String EXPECTED_NAME_1 = "Test 1";
    public static final String EXPECTED_NAME_2 = "Test 2";

    @Test
    public void testGetMetricsProfiles() {
        MetricsProfileManagementService service = new MetricsProfileManagementServiceOverride();
        MetricsProfileDo[] metricsProfiles = service.getMetricsProfiles();
        assertEquals(EXPECTED_ID_1, metricsProfiles[0].getId());
        assertEquals(EXPECTED_NAME_1, metricsProfiles[0].getName());
        assertEquals(EXPECTED_ID_2, metricsProfiles[1].getId());
        assertEquals(EXPECTED_NAME_2, metricsProfiles[1].getName());
    }

    private class MetricsProfileManagementServiceOverride extends MetricsProfileManagementService {
        public MetricsProfileManagementServiceOverride() {
            reader = new MetricsProfileReaderOverride();
        }
    }

    private class MetricsProfileReaderOverride extends MetricsProfileReader {
        public MetricsProfile[] getProfiles() {
            MetricsProfile p1 = new MetricsProfile(EXPECTED_ID_1, EXPECTED_NAME_1);
            MetricsProfile p2 = new MetricsProfile(EXPECTED_ID_2, EXPECTED_NAME_2);
            return new MetricsProfile[] { p1, p2 };
        }
    }
}
