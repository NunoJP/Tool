package domain.entities.common;

import java.awt.Color;
import java.util.HashMap;

public class ToolConfiguration {

    private String [] stopWords;
    private HashMap<WarningLevel, Color> warningColorMap;

    public String[] getStopWords() {
        if(stopWords == null) {
            stopWords = new String[0];
        }
        return stopWords;
    }

    public void setStopWords(String[] stopWords) {
        this.stopWords = stopWords;
    }

    public void addWarningColor(WarningLevel warningLevel, Color color) {
        if(this.warningColorMap == null) {
            warningColorMap = new HashMap<>();
        }
        warningColorMap.put(warningLevel, color);
    }

    public HashMap<WarningLevel, Color> getWarningColorMap() {
        return warningColorMap;
    }
}
