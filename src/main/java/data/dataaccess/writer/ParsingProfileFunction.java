package data.dataaccess.writer;

import data.dataaccess.common.ParsingProfileReadWriteConstants;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.ParsingProfile;

import java.util.function.Function;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.IGNORE_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_SPECIFIC_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.PORTION_SEPARATOR;

public class ParsingProfileFunction implements Function<ParsingProfile, String> {

    private boolean isNewFile;

    public ParsingProfileFunction(boolean isNewFile) {
        this.isNewFile = isNewFile;
    }

    @Override
    public String apply(ParsingProfile parsingProfile) {
        StringBuilder builder = new StringBuilder(isNewFile ? EXPECTED_FIRST_LINE + System.lineSeparator(): "");
        builder.append(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR).append(parsingProfile.getName())
                .append(System.lineSeparator())
                .append(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN)
                .append(System.lineSeparator());

        for (ParsingProfilePortion portion : parsingProfile.getPortions()) {
            builder.append(generatePortionLine(portion))
                    .append(System.lineSeparator());
        }
        builder.append(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN)
                .append(System.lineSeparator());
        return builder.toString();
    }

    private String generatePortionLine(ParsingProfilePortion portion) {
        if(portion.isIgnore()){
            return createIgnoreString(portion.getPortionName());
        } else if(portion.isSeparator()) {
            return createSeparatorString(portion.getPortionSymbol());
        } else if(portion.isSpecificFormat()) {
            return createKeepSpecificFormatString(portion.getPortionName(), portion.getSpecificFormat());
        } else {
            return createKeepString(portion.getPortionName());
        }
    }

    private String createIgnoreString(String portion) {
        return TextClassesEnum.getParsingStringByName(portion) + PORTION_SEPARATOR + IGNORE_TOKEN;
    }

    private String createSeparatorString(String portion) {
        return SeparatorEnum.getParsingStringBySymbol(portion);
    }

    private String createKeepSpecificFormatString(String portion, String specificFormat) {
        return TextClassesEnum.getParsingStringByName(portion)  + PORTION_SEPARATOR + KEEP_SPECIFIC_TOKEN + PORTION_SEPARATOR + specificFormat;
    }

    private String createKeepString(String portion) {
        return TextClassesEnum.getParsingStringByName(portion) + PORTION_SEPARATOR + KEEP_TOKEN;
    }

    public void setIsNewFile(boolean isNew) {
        isNewFile = isNew;
    }
}
