package domain.entities.domainobjects;

import domain.entities.common.Keyword;

import java.util.ArrayList;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FILE_NAME;

public class MetricsProfile {
    private Integer id;
    private String name;
    private boolean hasMostCommonWords;
    private boolean hasFileSize;
    private boolean hasKeywordHistogram;
    private boolean hasKeywordOverTime;
    private boolean hasKeywordThreshold;
    private ArrayList<Keyword> keywords = new ArrayList<>();
    private String originFile;

    public MetricsProfile() {
        this.id = -1;
        this.name = DEFAULT_METRICS_PROFILE_FILE_NAME;
    }

    public MetricsProfile(String name) {
        this.id = -1;
        this.name = name;
    }

    public MetricsProfile(int id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean hasMostCommonWords() {
        return hasMostCommonWords;
    }

    public void setHasMostCommonWords(boolean hasMostCommonWords) {
        this.hasMostCommonWords = hasMostCommonWords;
    }

    public boolean hasFileSize() {
        return hasFileSize;
    }

    public void setHasFileSize(boolean hasFileSize) {
        this.hasFileSize = hasFileSize;
    }

    public boolean hasKeywordHistogram() {
        return hasKeywordHistogram;
    }

    public void setHasKeywordHistogram(boolean hasKeywordHistogram) {
        this.hasKeywordHistogram = hasKeywordHistogram;
    }

    public boolean hasKeywordOverTime() {
        return hasKeywordOverTime;
    }

    public void setHasKeywordOverTime(boolean hasKeywordOverTime) {
        this.hasKeywordOverTime = hasKeywordOverTime;
    }

    public boolean hasKeywordThreshold() {
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

    public void addKeyword(Keyword keywordDo) {
        this.keywords.add(keywordDo);
    }

    public void removeKeyword(Keyword keywordDo) {
        this.keywords.remove(keywordDo);
    }

    public String getOriginFile() {
        return this.originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }
}
