package domain.entities.displayobjects;

import domain.entities.common.ParsingProfilePortion;

import java.util.ArrayList;

public class ParsingProfileDo {
    private String name;
    private Integer id;
    private ArrayList<ParsingProfilePortion> portions;
    private String originFile;

    public ParsingProfileDo(int id, String name, ArrayList<ParsingProfilePortion> portions) {
        this.id = id;
        this.name = name;
        this.portions = portions;
    }

    public ParsingProfileDo(int id, String name, ArrayList<ParsingProfilePortion> portions, String originFile) {
        this.id = id;
        this.name = name;
        this.portions = portions;
        this.originFile = originFile;
    }

    public ParsingProfileDo(int id, String name) {
        this(id, name, new ArrayList<>());
    }

    /*
    Constructor for a new completely empty profile
     */
    public ParsingProfileDo() {
        this(-1, null);
    }

    public ParsingProfileDo(ParsingProfileDo existingProfile) {
        this.id = existingProfile.getId();
        this.name = existingProfile.getName();
        this.portions = new ArrayList<>(existingProfile.getPortions());
        this.originFile = existingProfile.getOriginFile();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginFile() {
        return originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }

    public String getDescription() {
        return getGuiRepresentation();
    }

    public String addPortionAndGetProfile(String portionName, String portionSymbol, boolean isIgnore, boolean isSeparator, boolean isSpecificFormat){
       return addPortionAndGetProfile(portionName, portionSymbol, isIgnore, isSeparator, isSpecificFormat, "");
    }

    public String addPortionAndGetProfile(String portionName, String portionSymbol, boolean isIgnore, boolean isSeparator, boolean isSpecificFormat, String specificFormat) {
        portions.add(new ParsingProfilePortion(portionName, portionSymbol, isIgnore, isSeparator, isSpecificFormat, specificFormat));
        return getGuiRepresentation();
    }

    public void finishProfile() {
        if(portions.size() != 0) {
            for (ParsingProfilePortion portion : portions) {
                if(portion.isLast()) {
                    portion.setLast(false);
                }
            }
            portions.get(portions.size() - 1).setLast(true);
        }
    }

    public String removeLastPortionAndGetProfile(){
        if(portions.isEmpty()) {
            return getGuiRepresentation();
        }
        portions.remove(portions.size()-1);
        return getGuiRepresentation();
    }

    public String clearPortions() {
        portions = new ArrayList<>();
        return getGuiRepresentation();
    }

    public ArrayList<ParsingProfilePortion> getPortions() {
        return portions;
    }

    public String getGuiRepresentation() {
        StringBuilder builder = new StringBuilder();
        for (ParsingProfilePortion portion : portions) {
            if(portion.isIgnore()){
                builder.append(createIgnoreString(portion.getPortionRepresentation()));
            } else if(portion.isSeparator()) {
                builder.append(createSeparatorString(portion.getPortionRepresentation()));
            } else if(portion.isSpecificFormat()) {
                builder.append(createKeepSpecificFormatString(portion.getPortionRepresentation()));
            } else {
                builder.append(createKeepString(portion.getPortionRepresentation()));
            }
        }
        return builder.toString();
    }

    private String createIgnoreString(String portion) {
        return " Ignore<" + portion + "> ";
    }

    private String createSeparatorString(String portion) {
        return " " + portion + " ";
    }

    private String createKeepString(String portion) {
        return " Keep<" + portion + "> ";
    }

    private String createKeepSpecificFormatString(String portion) {
        return " KeepSpecific<" + portion + "> ";
    }


}
