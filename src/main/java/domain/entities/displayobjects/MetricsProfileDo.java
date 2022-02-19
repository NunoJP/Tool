package domain.entities.displayobjects;

import domain.entities.common.Keyword;

import java.util.ArrayList;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FILE_NAME;

public class MetricsProfileDo {
    private Integer id;
    private String name;
    private boolean hasMostCommonWords;
    private boolean hasFileSize;
    private boolean hasKeywordHistogram;
    private boolean hasKeywordOverTime;
    private boolean hasKeywordThreshold;
    private String originFile;
    private ArrayList<Keyword> keywords;

    public MetricsProfileDo() {
        this(-1, DEFAULT_METRICS_PROFILE_FILE_NAME);
    }

    public MetricsProfileDo(int id, String name) {
        this.id = id;
        this.name = name;
        this.keywords = new ArrayList<>();
    }

    public MetricsProfileDo(MetricsProfileDo existingProfile) {
        this.id = existingProfile.getId();
        this.name = existingProfile.getName();
        this.keywords = new ArrayList<>(existingProfile.getKeywords());
        this.hasMostCommonWords = existingProfile.isHasMostCommonWords();
        this.hasFileSize = existingProfile.isHasFileSize();
        this.hasKeywordHistogram = existingProfile.isHasKeywordHistogram();
        this.hasKeywordOverTime = existingProfile.isHasKeywordOverTime();
        this.hasKeywordThreshold = existingProfile.isHasKeywordThreshold();
        this.originFile = existingProfile.getOriginFile();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasMostCommonWords() {
        return hasMostCommonWords;
    }

    public void setHasMostCommonWords(boolean hasMostCommonWords) {
        this.hasMostCommonWords = hasMostCommonWords;
    }

    public boolean isHasFileSize() {
        return hasFileSize;
    }

    public void setHasFileSize(boolean hasFileSize) {
        this.hasFileSize = hasFileSize;
    }

    public boolean isHasKeywordHistogram() {
        return hasKeywordHistogram;
    }

    public void setHasKeywordHistogram(boolean hasKeywordHistogram) {
        this.hasKeywordHistogram = hasKeywordHistogram;
    }

    public boolean isHasKeywordOverTime() {
        return hasKeywordOverTime;
    }

    public void setHasKeywordOverTime(boolean hasKeywordOverTime) {
        this.hasKeywordOverTime = hasKeywordOverTime;
    }

    public boolean isHasKeywordThreshold() {
        return hasKeywordThreshold;
    }

    public void setHasKeywordThreshold(boolean hasKeywordThreshold) {
        this.hasKeywordThreshold = hasKeywordThreshold;
    }

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
    }

    public void removeKeyword(Keyword keyword) {
        this.keywords.remove(keyword);
    }

    public String getOriginFile() {
        return this.originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }

    public String getGuiRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append("MCW < ").append(getBooleanRepresentation(hasMostCommonWords)).append(" >; ");
        builder.append("FS < ").append(getBooleanRepresentation(hasFileSize)).append(" >; ");
        builder.append("KwdH < ").append(getBooleanRepresentation(hasKeywordHistogram)).append(" >; ");
        builder.append("KwdOt < ").append(getBooleanRepresentation(hasKeywordOverTime)).append(" >; ");
        builder.append("KwdTh < ").append(getBooleanRepresentation(hasKeywordThreshold)).append(" >; ");

        int maxNumbKwdToShow = 3;
        int i = 0;
        for (Keyword keyword : keywords) {
            builder.append(keyword.getKeywordText()).append("; ");
            if (i >= maxNumbKwdToShow) {
                return builder.toString();
            }
            i++;
        }

        return builder.toString();
    }

    private char getBooleanRepresentation(boolean value) {
        return value ? 'Y' : 'N';
    }
}
