package domain.entities.common;

public class ToolConfiguration {

    private String [] stopWords;

    public String[] getStopWords() {
        if(stopWords == null) {
            stopWords = new String[0];
        }
        return stopWords;
    }

    public void setStopWords(String[] stopWords) {
        this.stopWords = stopWords;
    }
}
