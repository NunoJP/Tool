package domain.services;

import data.dataaccess.reader.MetricsProfileReader;
import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.domainobjects.MetricsProfile;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MetricsProfileManagementService {

    protected MetricsProfileReader reader;

    public MetricsProfileManagementService() {
        reader = new MetricsProfileReader();
    }

    public MetricsProfileDo[] getMetricsProfiles() {
        MetricsProfile[] profiles = reader.getProfiles();
        return Arrays.stream(profiles).map(Converter::toDisplayObject).collect(Collectors.toList()).toArray(MetricsProfileDo[]::new);
    }

    public boolean deleteProfile(MetricsProfileDo metricsProfile) {
        return false;
    }
}
