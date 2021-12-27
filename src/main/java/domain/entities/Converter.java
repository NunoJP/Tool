package domain.entities;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;

public class Converter {

    public static ParsingProfile toDomainObject(ParsingProfileDo parsingProfileDo){
        return new ParsingProfile(parsingProfileDo.getId(), parsingProfileDo.getName(), parsingProfileDo.getDescription());
    }

    public static ParsingProfileDo toDisplayObject(ParsingProfile parsingProfile) {
        return new ParsingProfileDo(parsingProfile.getId(), parsingProfile.getName(), parsingProfile.getDescription());
    }

    public static MetricsProfile toDomainObject(MetricsProfileDo metricsProfileDo){
        return new MetricsProfile(metricsProfileDo.getId(), metricsProfileDo.getName());
    }

    public static MetricsProfileDo toDisplayObject(MetricsProfile metricsProfile) {
        return new MetricsProfileDo(metricsProfile.getId(), metricsProfile.getName());
    }
}
