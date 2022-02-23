package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;

import java.util.Arrays;
import java.util.function.Consumer;

import static data.dataaccess.common.ToolConfigurationReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.PORTION_SEPARATOR;
import static data.dataaccess.common.ToolConfigurationReadWriteConstants.STOP_WORDS_TOKEN;

public class ToolConfigurationConsumer implements Consumer<String> {

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
            }
        }
    }

    public ToolConfiguration getToolConfiguration() {
        return toolConfiguration;
    }
}
