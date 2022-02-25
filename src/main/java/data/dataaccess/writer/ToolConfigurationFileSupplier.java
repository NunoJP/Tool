package data.dataaccess.writer;

import data.dataaccess.common.ToolConfigurationReadWriteConstants;

import java.util.function.Supplier;

import static data.dataaccess.common.ToolConfigurationReadWriteConstants.PORTION_SEPARATOR;

public class ToolConfigurationFileSupplier implements Supplier<String> {

    private final String [][] configurations = new String[][] {
            { ToolConfigurationReadWriteConstants.STOP_WORDS_TOKEN, ToolConfigurationReadWriteConstants.STOP_WORDS_DEFAULT_VALUES }
    };

    @Override
    public String get() {
        StringBuilder text = new StringBuilder();
        text.append(ToolConfigurationReadWriteConstants.EXPECTED_FIRST_LINE).append(System.lineSeparator());
        for (String[] config : configurations) {
            text.append(config[0]).append(PORTION_SEPARATOR).append(config[1]).append(System.lineSeparator());
        }
        return text.toString();
    }
}
