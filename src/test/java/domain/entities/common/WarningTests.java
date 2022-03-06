package domain.entities.common;

import org.junit.Test;
import presentation.common.GuiMessages;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class WarningTests {

    private Warning warning;

    @Test
    public void simpleTest() {

        testCombination("Word0", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED);

        testCombination("Word1", ThresholdTypeEnum.BIGGER_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED);

        testCombination("Word2", ThresholdTypeEnum.EQUAL_TO, GuiMessages.WARNING_MESSAGE_PARTICLE_MET);

        testCombination("Word3", ThresholdTypeEnum.SMALLER_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED);

        testCombination("Word4", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED);

        testCombination("Word5", ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, 1);

        testCombination("Word6", ThresholdTypeEnum.BIGGER_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, 1);

        testCombination("Word7", ThresholdTypeEnum.EQUAL_TO, GuiMessages.WARNING_MESSAGE_PARTICLE_MET, 1);

        testCombination("Word8", ThresholdTypeEnum.SMALLER_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_SURPASSED, 1);

        testCombination("Word9", ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN, GuiMessages.WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED, 1);

    }

    private void testCombination(String word5, ThresholdTypeEnum biggerOrEqualThan, String warningMessageParticleSurpassed, int val) {
        Keyword kwd5 = new Keyword(word5, false);
        kwd5.setThresholdTrio(biggerOrEqualThan, ThresholdUnitEnum.OCCURRENCES, new BigDecimal(val));
        warning = new Warning(kwd5);
        assertEquals(makeMessage(kwd5, warningMessageParticleSurpassed), warning.getMessage());
    }

    private void testCombination(String word0, ThresholdTypeEnum biggerOrEqualThan, String warningMessageParticleMetOrSurpassed) {
        Keyword kwd0 = new Keyword(word0, false);
        kwd0.setThresholdTrio(biggerOrEqualThan, ThresholdUnitEnum.PERCENTAGE, new BigDecimal("0.1"));
        warning = new Warning(kwd0);
        assertEquals(makeMessage(kwd0, warningMessageParticleMetOrSurpassed), warning.getMessage());
    }

    private String makeMessage(Keyword keyword, String particle) {
        return String.format(GuiMessages.WARNING_MESSAGE_BASE, keyword.getThresholdValue(), keyword.getKeywordText(), particle);
    }
}