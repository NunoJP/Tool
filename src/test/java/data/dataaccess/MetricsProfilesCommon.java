package data.dataaccess;

import data.dataaccess.reader.MetricsProfileConsumer;
import data.dataaccess.writer.MetricsProfileFunction;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.WarningLevel;
import domain.entities.domainobjects.MetricsProfile;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public abstract class MetricsProfilesCommon {
    
    protected static final String VALUE = "Value";
    protected static final String GARBAGE = "assfsadaskh bdakbdkajsbdv";

    protected MetricsProfileConsumer getConsumer() {
        MetricsProfileConsumer consumer = new MetricsProfileConsumer();

        // Test baseline
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
        return consumer;
    }

    protected void validateKeyword(Keyword keyword, String kwd, boolean caseSensitive,
                                   ThresholdTypeEnum typeEnum, BigDecimal value, ThresholdUnitEnum unitEnum, WarningLevel none) {
        assertEquals(kwd, keyword.getKeywordText());
        assertEquals(caseSensitive, keyword.isCaseSensitive());
        assertEquals(typeEnum, keyword.getThresholdType());
        assertEquals(value, keyword.getThresholdValue());
        assertEquals(unitEnum, keyword.getThresholdUnit());
    }

    protected void validateEnabledProfiles(MetricsProfile profile, boolean mCw, boolean fs, boolean kwdH, boolean kwdOt, boolean kwdTh) {
        assertEquals(mCw, profile.isHasMostCommonWords());
        assertEquals(fs, profile.isHasFileSize());
        assertEquals(kwdH, profile.isHasKeywordHistogram());
        assertEquals(kwdOt, profile.isHasKeywordOverTime());
        assertEquals(kwdTh, profile.isHasKeywordThreshold());
    }

    protected void simulateWriteAndRead(MetricsProfileFunction function, MetricsProfileConsumer consumer, MetricsProfile profile) {
        String apply = function.apply(profile);
        String[] split = apply.split(System.lineSeparator());

        for (String s : split) {
            consumer.accept(s);
        }
    }
}
