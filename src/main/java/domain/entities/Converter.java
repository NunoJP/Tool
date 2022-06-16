package domain.entities;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;

public class Converter {

    public static ParsingProfile toDomainObject(ParsingProfileDo parsingProfileDo){
        return new ParsingProfile(parsingProfileDo.getId(), parsingProfileDo.getName(), parsingProfileDo.getPortions(), parsingProfileDo.getOriginFile());
    }

    public static ParsingProfileDo toDisplayObject(ParsingProfile parsingProfile) {
        return new ParsingProfileDo(parsingProfile.getId(), parsingProfile.getName(), parsingProfile.getPortions(), parsingProfile.getOriginFile());
    }

    public static MetricsProfile toDomainObject(MetricsProfileDo metricsProfileDo){
        MetricsProfile metricsProfile = new MetricsProfile(metricsProfileDo.getId(), metricsProfileDo.getName());
        metricsProfile.setHasMostCommonWords(metricsProfileDo.hasMostCommonWords());
        metricsProfile.setHasFileSize(metricsProfileDo.hasFileSize());
        metricsProfile.setHasKeywordHistogram(metricsProfileDo.hasKeywordHistogram());
        metricsProfile.setHasKeywordOverTime(metricsProfileDo.hasKeywordOverTime());
        metricsProfile.setHasKeywordThreshold(metricsProfileDo.hasKeywordThreshold());
        metricsProfile.setKeywords(metricsProfileDo.getKeywords());
        metricsProfile.setOriginFile(metricsProfileDo.getOriginFile());
        return metricsProfile;
    }

    public static MetricsProfileDo toDisplayObject(MetricsProfile metricsProfile) {
        MetricsProfileDo profileDo = new MetricsProfileDo(metricsProfile.getId(), metricsProfile.getName());
        profileDo.setHasMostCommonWords(metricsProfile.hasMostCommonWords());
        profileDo.setHasFileSize(metricsProfile.hasFileSize());
        profileDo.setHasKeywordHistogram(metricsProfile.hasKeywordHistogram());
        profileDo.setHasKeywordOverTime(metricsProfile.hasKeywordOverTime());
        profileDo.setHasKeywordThreshold(metricsProfile.hasKeywordThreshold());
        profileDo.setKeywords(metricsProfile.getKeywords());
        profileDo.setOriginFile(metricsProfile.getOriginFile());
        return profileDo;
    }
}
