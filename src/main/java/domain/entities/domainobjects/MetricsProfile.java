package domain.entities.domainobjects;

import domain.entities.common.Keyword;

import java.util.ArrayList;

public class MetricsProfile {
    private Integer id;
    private String name;
    private boolean hasMostCommonWords;
    private boolean hasFileSize;
    private boolean hasKeywordHistogram;
    private boolean hasKeywordOverTime;
    private boolean hasKeywordThreshold;
    private ArrayList<Keyword> keywords = new ArrayList<>();

    public MetricsProfile() {
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

    public void addKeyword(Keyword keywordDo) {
        this.keywords.add(keywordDo);
    }

    public void removeKeyword(Keyword keywordDo) {
        this.keywords.remove(keywordDo);
    }
}
