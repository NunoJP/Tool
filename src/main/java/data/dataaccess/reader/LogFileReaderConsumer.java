package data.dataaccess.reader;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFileReaderConsumer implements Consumer<String> {
    private ParsingProfile parsingProfile;
    private ArrayList<LogLine> logLines;
    private static final Logger LOGGER = Logger.getLogger(LogFileReaderConsumer.class.getName());
    private LogLine latestValidLine;

    public LogFileReaderConsumer(ParsingProfile parsingProfile) {
        this.parsingProfile = parsingProfile;
        logLines = new ArrayList<>();
    }

    @Override
    public void accept(String s) {
        String originalString = s;
        boolean validLine = true;
        String subString;
        LogLine line = new LogLine();
        ParsingProfilePortion lastTextClass = null;
        try {
            for (ParsingProfilePortion portion : parsingProfile.getPortions()) {
                if (portion.isSeparator()) {
                    int endIndex = s.indexOf(portion.getPortionSymbol());
                    // the separator was not found, so the line is not standard
                    if(endIndex < 0) {
                        validLine = false;
                        break;
                    }
                    subString = s.substring(0, endIndex);
                    s = s.substring(endIndex + 1).trim();
                    process(subString.trim(), line, lastTextClass);
                } else if (portion.isLast()) {
                    process(s, line, portion);
                } else {
                    lastTextClass = portion;
                }
            }

        } catch (ParseException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.FINEST, "Processing message as non standard:", e);
            validLine = false;
        }

        if(validLine) {
            latestValidLine = line;
            logLines.add(line);
        } else {
            if(latestValidLine != null) {
                String message = latestValidLine.getMessage();
                message = message + System.lineSeparator() + originalString;
                latestValidLine.setMessage(message);
            }
        }

    }

    private void process(String subString, LogLine line, ParsingProfilePortion portion) throws ParseException {
        if(portion == null) {
            LOGGER.log(Level.WARNING, "Portion was null");
            return;
        }
        if (portion.isIgnore()) {
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
        DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
        DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);

        if(TextClassesEnum.TIMESTAMP.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                timeStampFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            line.setTimestamp(timeStampFormat.parse(subString));
        } else if(TextClassesEnum.DATE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                dateFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            line.setDate(dateFormat.parse(subString));
        } else if(TextClassesEnum.TIME.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                timeFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            line.setTime(timeFormat.parse(subString));
        } else if(TextClassesEnum.LEVEL.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                subString = String.format(portion.getSpecificFormat(), subString);
            }
            line.setLevel(subString);
        } else if(TextClassesEnum.MESSAGE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                subString = String.format(portion.getSpecificFormat(), subString);
            }
            line.setMessage(subString);
        } else if(TextClassesEnum.ID.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                subString = String.format(portion.getSpecificFormat(), subString);
            }
            line.setIdentifier(subString);
        } else if(TextClassesEnum.METHOD.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                subString = String.format(portion.getSpecificFormat(), subString);
            }
            line.setOrigin(subString);
        }
    }

    public LogLine[] getLines() {
        return logLines.toArray(new LogLine[0]);
    }
}
