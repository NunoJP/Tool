package domain.entities.domainobjects;

import domain.entities.common.ParsingProfilePortion;

import java.util.ArrayList;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FILE_NAME;

public class ParsingProfile {
    private String originFile;
    private String name;
    private Integer id;
    private final ArrayList<ParsingProfilePortion> portions;


    public ParsingProfile(int id, String name, ArrayList<ParsingProfilePortion> portions, String originFile) {
        this.id = id;
        this.name = name;
        this.portions = portions;
        this.originFile = originFile;
    }

    public ParsingProfile(int id, String name, ArrayList<ParsingProfilePortion> portions) {
        this(id, name, portions, DEFAULT_PARSING_PROFILE_FILE_NAME);
    }

    public ParsingProfile(int id, String name) {
        this(id, name, new ArrayList<>());
    }

    public ParsingProfile(String name, ArrayList<ParsingProfilePortion> portions) {
        this(-1, name, portions);
    }

    public ParsingProfile(String name) {
        this(-1, name, new ArrayList<>());
    }

    /*
    Constructor for a new completely empty profile
     */
    public ParsingProfile() {
        this(-1, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<ParsingProfilePortion> getPortions() {
        return portions;
    }

    public void addPortion(ParsingProfilePortion portion) {
        this.portions.add(portion);
    }

    public String getOriginFile() {
        return originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }
}
