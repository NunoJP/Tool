package domain.entities.common;

import java.math.BigDecimal;

public class Keyword {

    private String keywordText;
    private boolean isCaseSensitive;
    private ThresholdTypeEnum threshold;
    private BigDecimal thresholdValue;
    private ThresholdUnitEnum thresholdUnit;

    public Keyword(String keywordText, boolean isCaseSensitive) {
        this.keywordText = keywordText;
        this.isCaseSensitive = isCaseSensitive;
        this.threshold = ThresholdTypeEnum.NOT_APPLICABLE;
        this.thresholdValue = new BigDecimal(0);
        this.thresholdUnit = ThresholdUnitEnum.NONE;
    }

    public Keyword setThresholdTrio(ThresholdTypeEnum threshold, ThresholdUnitEnum unit, BigDecimal thresholdValue) {
        this.threshold = threshold;
        this.thresholdUnit = unit;
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

    public void setThresholdType(ThresholdTypeEnum threshold) {
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
