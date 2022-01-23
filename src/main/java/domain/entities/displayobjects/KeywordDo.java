package domain.entities.displayobjects;

import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;

import java.math.BigDecimal;

public class KeywordDo {

    private String keywordText;
    private boolean isCaseSensitive;
    private ThresholdTypeEnum threshold;
    private ThresholdUnitEnum unit;
    private BigDecimal thresholdValue;
    private ThresholdUnitEnum thresholdUnit;

    public KeywordDo(String keywordText, boolean isCaseSensitive) {
        this.keywordText = keywordText;
        this.isCaseSensitive = isCaseSensitive;
    }

    public KeywordDo setThreshold(ThresholdTypeEnum threshold, ThresholdUnitEnum unit, BigDecimal thresholdValue) {
        this.threshold = threshold;
        this.unit = unit;
        this.thresholdValue = thresholdValue;
        return this;
    }


    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }

    public ThresholdTypeEnum getThreshold() {
        return threshold;
    }

    public void setThreshold(ThresholdTypeEnum threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public ThresholdUnitEnum getThresholdUnit() {
        return thresholdUnit;
    }

    public void setThresholdUnit(ThresholdUnitEnum thresholdUnit) {
        this.thresholdUnit = thresholdUnit;
    }
}
