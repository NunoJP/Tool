package data.dataaccess;

import data.dataaccess.reader.ParsingProfileConsumer;
import data.dataaccess.writer.ParsingProfileFunction;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.domainobjects.ParsingProfile;

import static org.junit.Assert.assertEquals;

public class ParsingProfilesCommon {

    protected static final String VALUE = "Value";
    protected static final String GARBAGE = "assfsadaskh bdakbdkajsbdv";
    protected final String EXPECTED_FIRST_LINE = "Parsing Profile";


    protected ParsingProfileConsumer getConsumer() {
        ParsingProfileConsumer consumer = new ParsingProfileConsumer();
        // baseline
        ParsingProfile[] profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
        return consumer;
    }


    protected void validatePortion(ParsingProfilePortion portion, boolean isSeparator, boolean isIgnore, boolean isSpecificFormat, String name) {
        assertEquals(isSeparator, portion.isSeparator());
        assertEquals(isIgnore, portion.isIgnore());
        assertEquals(isSpecificFormat, portion.isSpecificFormat());
        assertEquals(name, portion.getPortionRepresentation());
    }

    protected void simulateWriteAndRead(ParsingProfileFunction function, ParsingProfileConsumer consumer, ParsingProfile profile) {
        String apply = function.apply(profile);
        String[] split = apply.split(System.lineSeparator());

        for (String s : split) {
            consumer.accept(s);
        }
    }

}
