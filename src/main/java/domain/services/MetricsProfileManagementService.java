package domain.services;

import data.dataaccess.reader.MetricsProfileReader;
import data.dataaccess.writer.MetricsProfileWriter;
import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.domainobjects.MetricsProfile;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MetricsProfileManagementService {

    protected MetricsProfileReader reader;
    protected MetricsProfileWriter writer;

    public MetricsProfileManagementService() {
        reader = new MetricsProfileReader();
        writer = new MetricsProfileWriter();
    }

    public MetricsProfileDo[] getMetricsProfiles() {
        MetricsProfile[] profiles = reader.getProfiles();
        return Arrays.stream(profiles).map(Converter::toDisplayObject).collect(Collectors.toList()).toArray(MetricsProfileDo[]::new);
    }

    public boolean updateProfile(MetricsProfileDo metricsProfile) {
        return writer.updateProfile(Converter.toDomainObject(metricsProfile));
    }

    public boolean deleteProfile(MetricsProfileDo metricsProfile) {
        return writer.deleteProfile(Converter.toDomainObject(metricsProfile));
    }

    public boolean createProfile(MetricsProfileDo newObject) {
        return writer.createProfile(Converter.toDomainObject(newObject));
    }
}
