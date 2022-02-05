package domain.entities.displayobjects;

import domain.entities.common.Keyword;

import java.util.ArrayList;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FILE_NAME;

public class MetricsProfileDo {
    private Integer id;
    private String name;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
