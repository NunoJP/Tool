package domain.services;


import data.dataaccess.common.ToolConfigurationReadWriteConstants;
import data.dataaccess.reader.ToolConfigurationReader;
import data.dataaccess.writer.ToolConfigurationWriter;
import domain.entities.common.ToolConfiguration;

import java.io.File;

public class ToolConfigurationService {

    private static ToolConfigurationService instance;
    ToolConfigurationReader reader;
    ToolConfigurationWriter writer;

    public static ToolConfigurationService getInstance(){
        if(instance == null) {
            instance = new ToolConfigurationService();
        }
        return instance;
    }

    private ToolConfigurationService() {
        reader = new ToolConfigurationReader();
        writer = new ToolConfigurationWriter();
    }

    public ToolConfiguration getToolConfiguration() {
        return reader.read();
    }

    public void createConfigFile() {
        File configFile = new File(ToolConfigurationReadWriteConstants.DEFAULT_TOOL_CONFIGURATION_PROFILE_PATH);

        // usually we do not check if the file exists before creating it
        // it is an anti-pattern as createNewFile already performs this operation
        // what we don't want is to write over the file if it has already been created
        if(!configFile.exists()) {
            writer.write(configFile);
        }
    }
}
