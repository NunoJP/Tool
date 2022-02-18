package data.dataaccess.writer;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Function;

public class LogFileFunction implements Function<LogLine, String> {
    private final ParsingProfile parsingProfile;
    private DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);

    public LogFileFunction(ParsingProfile parsingProfile) {
        this.parsingProfile = parsingProfile;
    }

    @Override
    public String apply(LogLine logLine) {
        return generateLine(logLine);
    }

    private String generateLine(LogLine logLine) {
        StringBuilder line = new StringBuilder();
        ArrayList<ParsingProfilePortion> portions = parsingProfile.getPortions();
        for (ParsingProfilePortion portion : portions) {
            if(portion.isSeparator()) {
                line.append(portion.getPortionSymbol());
            } else if(!portion.isIgnore()){
                line.append(calculateValue(portion, logLine));
            }
        }
        return line.append(System.lineSeparator()).toString();
    }

    private String calculateValue(ParsingProfilePortion portion, LogLine logLine) {
        if(TextClassesEnum.TIMESTAMP.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                timeStampFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            return timeStampFormat.format(logLine.getTimestamp());
        } else if(TextClassesEnum.DATE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                dateFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            return dateFormat.format(logLine.getDate());
        } else if(TextClassesEnum.TIME.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                timeFormat = new SimpleDateFormat(portion.getSpecificFormat());
            }
            return timeFormat.format(logLine.getTime());
        } else if(TextClassesEnum.LEVEL.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                return String.format(portion.getSpecificFormat(), logLine.getLevel());
            }
            return logLine.getLevel();
        } else if(TextClassesEnum.MESSAGE.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                return String.format(portion.getSpecificFormat(), logLine.getMessage());
            }
            return logLine.getMessage();
        } else if(TextClassesEnum.ID.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                return String.format(portion.getSpecificFormat(), logLine.getIdentifier());
            }
            return logLine.getIdentifier();
        } else if(TextClassesEnum.METHOD.getName().equals(portion.getPortionName())) {
            if(portion.isSpecificFormat()) {
                return String.format(portion.getSpecificFormat(), logLine.getOrigin());
            }
            return logLine.getOrigin();
        }
        return "";
    }

}
