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
        metricsProfile.setHasMostCommonWords(metricsProfileDo.isHasMostCommonWords());
        metricsProfile.setHasFileSize(metricsProfileDo.isHasFileSize());
        metricsProfile.setHasKeywordHistogram(metricsProfileDo.isHasKeywordHistogram());
        metricsProfile.setHasKeywordOverTime(metricsProfileDo.isHasKeywordOverTime());
        metricsProfile.setHasKeywordThreshold(metricsProfileDo.isHasKeywordThreshold());
        metricsProfile.setKeywords(metricsProfileDo.getKeywords());
        return metricsProfile;
    }

    public static MetricsProfileDo toDisplayObject(MetricsProfile metricsProfile) {
        MetricsProfileDo profileDo = new MetricsProfileDo(metricsProfile.getId(), metricsProfile.getName());
        profileDo.setHasMostCommonWords(metricsProfile.isHasMostCommonWords());
        profileDo.setHasFileSize(metricsProfile.isHasFileSize());
        profileDo.setHasKeywordHistogram(metricsProfile.isHasKeywordHistogram());
        profileDo.setHasKeywordOverTime(metricsProfile.isHasKeywordOverTime());
        profileDo.setHasKeywordThreshold(metricsProfile.isHasKeywordThreshold());
        profileDo.setKeywords(metricsProfile.getKeywords());
        return profileDo;
    }
}
