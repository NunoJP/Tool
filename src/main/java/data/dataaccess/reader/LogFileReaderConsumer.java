package data.dataaccess.reader;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFileReaderConsumer implements Consumer<String> {
    private ParsingProfile parsingProfile;
    private ArrayList<LogLine> logLines;
    private static final Logger LOGGER = Logger.getLogger(LogFileReaderConsumer.class.getName());

    public LogFileReaderConsumer(ParsingProfile parsingProfile) {
        this.parsingProfile = parsingProfile;
        logLines = new ArrayList<>();
    }

    @Override
    public void accept(String s) {
        int currentIndex = 0;
        String subString;
        LogLine line = new LogLine();
        ParsingProfilePortion lastTextClass = null;
        for (ParsingProfilePortion portion : parsingProfile.getPortions()) {
            if (portion.isSeparator()) {
                subString = s.substring(0, s.indexOf(portion.getPortionSymbol()));
                s = s.substring(s.indexOf(portion.getPortionSymbol()) + 1).trim();
                process(subString.trim(), line, lastTextClass);
            } else if(portion.isLast()) {
                process(s, line, portion);
            } else {
                lastTextClass = portion;
            }
        }
        logLines.add(line);
    }

    private void process(String subString, LogLine line, ParsingProfilePortion portion) {
        if(portion == null) {
            LOGGER.log(Level.WARNING, "Portion was null");
            return;
        }
        if (portion.isIgnore()) {
            return;
        }
        if(TextClassesEnum.TIMESTAMP.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {

            }
        } else if(TextClassesEnum.DATE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {

            }
        } else if(TextClassesEnum.TIME.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {

            }
        } else if(TextClassesEnum.LEVEL.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {
                line.setLevel(subString);
            }
        } else if(TextClassesEnum.MESSAGE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {
                line.setMessage(subString);
            }
        } else if(TextClassesEnum.METHOD.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {

            } else {
                line.setOrigin(subString);
            }
        }
    }

    public LogLine[] getLines() {
        return logLines.toArray(new LogLine[0]);
    }
}
