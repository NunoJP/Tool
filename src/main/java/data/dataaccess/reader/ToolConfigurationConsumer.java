package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.common.WarningLevel;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Consumer;

import static data.dataaccess.common.ToolConfigurationReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.PORTION_SEPARATOR;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.STOP_WORDS_TOKEN;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.WARNING_COLORS_TOKEN;

public class ToolConfigurationConsumer implements Consumer<String> {

    private static final int HEXADECIMAL = 16;
    private final ToolConfiguration toolConfiguration;
    boolean firstLine = true;

    public ToolConfigurationConsumer() {
        this.toolConfiguration = new ToolConfiguration();
    }

    @Override
    public void accept(String line) {
        // identify if the file contains parsing profiles
        if(firstLine) {
            if(!EXPECTED_FIRST_LINE.equalsIgnoreCase(line)){
                return;
            }
            firstLine = false;
        } else {
            if(line.startsWith(STOP_WORDS_TOKEN)) {
                String[] split = line.split(PORTION_SEPARATOR);
                toolConfiguration.setStopWords(Arrays.copyOfRange(split, 1, split.length));
            } else if(line.startsWith(WARNING_COLORS_TOKEN)) {
                String[] split = line.split(PORTION_SEPARATOR);
                processColors(split);
            }
        }
    }

    private void processColors(String[] split) {
        for (int i = 1; i < split.length - 1; i+=2) {
            Color color = new Color(Integer.parseInt(split[i + 1], HEXADECIMAL));
            if(WarningLevel.NONE.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.NONE, color);
            } else if(WarningLevel.CRITICAL.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.CRITICAL, color);
            } else if(WarningLevel.HIGH.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.HIGH, color);
            } else if(WarningLevel.MEDIUM.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.MEDIUM, color);
            } else if(WarningLevel.LOW.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.LOW, color);
            } else if(WarningLevel.INFO.getParsingString().equalsIgnoreCase(split[i])){
                toolConfiguration.addWarningColor(WarningLevel.INFO, color);
            }
        }
    }

    public ToolConfiguration getToolConfiguration() {
        return toolConfiguration;
    }
}
