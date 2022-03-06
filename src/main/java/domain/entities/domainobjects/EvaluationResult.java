package domain.entities.domainobjects;

import domain.entities.common.Keyword;
import domain.entities.common.Warning;

import java.util.Optional;

public class EvaluationResult {


    private Keyword standard;
    private String actualValue;
    private boolean thresholdMet;

    private EvaluationResult() {}

    public EvaluationResult(Keyword standard, String actualValue) {
        this.standard = standard;
        this.actualValue = actualValue;
    }

    public static EvaluationResult emptyResult() {
        return new EvaluationResult();
    }

    public void setThresholdMet() {
        thresholdMet = true;
    }

    public Optional<Warning> getWarning() {
        if(thresholdMet) {
            return Optional.of(new Warning(standard));
        }
        return Optional.empty();
    }

    public Keyword getStandard() {
        return standard;
    }

    public String actualValue() {
        return actualValue;
    }
}
