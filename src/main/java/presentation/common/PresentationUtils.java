package presentation.common;

import domain.entities.common.Warning;
import domain.entities.common.WarningLevel;
import domain.services.ToolConfigurationService;
import general.util.Pair;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class PresentationUtils {


    public static String[][] getMessages(ArrayList<Warning> warningsData) {
        String[][] res = new String[warningsData.size()][];
        int idx = 0;
        for (Warning warning : warningsData) {
            res[idx] = new String[] { warning.getWarningLevel().getSymbol(), warning.getMessage()};
            idx++;
        }
        return res;
    }

    public static HashMap<String, Pair<Color, Color>> generateDefaultColorMap() {
        HashMap<WarningLevel, Color> warningColorMap = ToolConfigurationService.getInstance().getToolConfiguration().getWarningColorMap();
        HashMap<String, Pair<Color, Color>> colorMap = new HashMap<>();
        for (WarningLevel s : warningColorMap.keySet()) {
            colorMap.put(s.getSymbol(), Pair.of(warningColorMap.get(s), Color.BLACK));
        }
        return colorMap;
    }

}
