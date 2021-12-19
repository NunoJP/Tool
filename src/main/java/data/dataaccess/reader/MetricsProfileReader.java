package data.dataaccess.reader;

import domain.entities.domainobjects.MetricsProfile;

public class MetricsProfileReader {
    public MetricsProfile[] getProfiles() {
        MetricsProfile p1 = new MetricsProfile(1, "Test 1");
        MetricsProfile p2 = new MetricsProfile(2, "Test 2");
        return new MetricsProfile[] { p1, p2};
    }
}
