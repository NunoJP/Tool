package domain.entities.displayobjects;

import java.util.ArrayList;

public class MetricsProfileDo {
    private Integer id;
    private String name;
    private String description;
    private boolean hasMostCommonWords;
    private boolean hasFileSize;
    private boolean hasKeywordHistogram;
    private boolean hasKeywordOverTime;
    private boolean hasKeywordThreshold;
    private ArrayList<KeywordDo> keywords;

    public MetricsProfileDo() {
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

    public ArrayList<KeywordDo> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<KeywordDo> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(KeywordDo keywordDo) {
        this.keywords.add(keywordDo);
    }

    public void removeKeyword(KeywordDo keywordDo) {
        this.keywords.remove(keywordDo);
    }
}
