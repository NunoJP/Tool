package domain.entities.domainobjects;

import java.util.Date;

public class MetricsReport {
    public String[][] getKwdThresholdData() {
        return new String [][] {
                {"Error", "1"},
                {"Warning", "2"},
                {"Info", "3"},
                {"Debug", "12"},
        };
    }

    public String[][] getLogLevelData() {
        return new String [][] {
                {"Error", "10%"},
                {"Warning", "20%"},
                {"Info", "30%"},
                {"Debug", "10%"},
        };
    }

    public String[][] getMostCommonWordsData() {
        return new String [][] {
                {"Error", "1"},
                {"Warning", "2"},
                {"Info", "3"},
                {"Debug", "12"},
        };
    }

    public String[][] getWarningsData() {
        return new String [][] {
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
        };
    }

    public Date getStartDate(){
        return new Date();
    }

    public Date getEndDate(){
        return new Date();
    }

    public String getFileName(){
        return "LogFile";
    }

}
