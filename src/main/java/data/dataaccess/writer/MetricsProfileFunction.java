package data.dataaccess.writer;

import data.dataaccess.common.MetricsProfileReadWriteConstants;
import domain.entities.common.Keyword;
import domain.entities.domainobjects.MetricsProfile;

import java.util.ArrayList;
import java.util.function.Function;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.SEPARATOR;

public class MetricsProfileFunction implements Function<MetricsProfile, String> {

    private boolean isNewFile;

    public MetricsProfileFunction(boolean isNewFile) {
        this.isNewFile = isNewFile;
    }

    public String apply(MetricsProfile metricsProfile) {
        StringBuilder builder = new StringBuilder(isNewFile ? EXPECTED_FIRST_LINE + System.lineSeparator(): "");
        builder.append(MetricsProfileReadWriteConstants.NAME_TOKEN + SEPARATOR).append(metricsProfile.getName())
                .append(System.lineSeparator())
                .append(MetricsProfileReadWriteConstants.START_PROFILE_TOKEN)
                .append(System.lineSeparator());

        builder.append(MetricsProfileReadWriteConstants.MOST_COMMON_WORDS_TOKEN).append(SEPARATOR)
                .append(metricsProfile.hasMostCommonWords())
                .append(System.lineSeparator());
        builder.append(MetricsProfileReadWriteConstants.FILE_SIZE_TOKEN).append(SEPARATOR)
                .append(metricsProfile.hasFileSize())
                .append(System.lineSeparator());
        builder.append(MetricsProfileReadWriteConstants.KEYWORD_HISTOGRAM_TOKEN).append(SEPARATOR)
                .append(metricsProfile.hasKeywordHistogram())
                .append(System.lineSeparator());
        builder.append(MetricsProfileReadWriteConstants.KEYWORD_OVER_TIME_TOKEN).append(SEPARATOR)
                .append(metricsProfile.hasKeywordOverTime())
                .append(System.lineSeparator());
        builder.append(MetricsProfileReadWriteConstants.KEYWORD_THRESHOLD_TOKEN).append(SEPARATOR)
                .append(metricsProfile.hasKeywordThreshold())
                .append(System.lineSeparator());

        builder.append(MetricsProfileReadWriteConstants.START_KEYWORDS_TOKEN)
                .append(System.lineSeparator());

        ArrayList<Keyword> keywords = metricsProfile.getKeywords();
        for (Keyword keyword : keywords) {
            builder.append(generateKeywordLine(keyword))
                    .append(System.lineSeparator());
        }

        builder.append(MetricsProfileReadWriteConstants.END_KEYWORDS_TOKEN)
                .append(System.lineSeparator());

        builder.append(MetricsProfileReadWriteConstants.END_PROFILE_TOKEN)
                .append(System.lineSeparator());
        return builder.toString();
    }

    private String generateKeywordLine(Keyword portion) {
        return portion.getKeywordText() + SEPARATOR
                + portion.isCaseSensitive() + SEPARATOR
                + portion.getThresholdType().getParsingString() + SEPARATOR
                + portion.getThresholdValue() + SEPARATOR
                + portion.getThresholdUnit().getParsingString() + SEPARATOR
                + portion.getWarningLevel().getParsingString();
    }

    public void setIsNewFile(boolean isNew) {
        isNewFile = isNew;
    }
}
