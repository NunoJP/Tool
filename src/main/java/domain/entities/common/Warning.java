package domain.entities.common;

import presentation.common.GuiMessages;

public class Warning {

    private final String message;
    private WarningLevel warningLevel;

    public Warning(Keyword standard) {
        String particle = "";
        switch (standard.getThresholdType()) {
            case EQUAL_TO:
                particle = GuiMessages.WARNING_MESSAGE_PARTICLE_MET;
                break;
            case BIGGER_THAN:
            case SMALLER_THAN:
                particle = GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED;
                break;
            case BIGGER_OR_EQUAL_THAN:
            case SMALLER_OR_EQUAL_THAN:
                particle = GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED;
                break;
        }

        message = String.format(getMessageBase(standard), standard.getThresholdValue(), standard.getKeywordText(), particle);
        warningLevel = standard.getWarningLevel();
    }

    private String getMessageBase(Keyword standard) {
        if(ThresholdUnitEnum.PERCENTAGE.getName().equals(standard.getThresholdUnit().getName())) {
            return GuiMessages.WARNING_MESSAGE_BASE_PERCENTAGE;
        }
        return GuiMessages.WARNING_MESSAGE_BASE_OCCURRENCES;
    }

    public String getMessage() {
        return message;
    }

    public WarningLevel getWarningLevel() {
        if (warningLevel == null) {
            warningLevel = WarningLevel.NONE;
        }
        return warningLevel;
    }

    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }
}
