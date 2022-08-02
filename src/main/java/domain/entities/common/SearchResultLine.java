package domain.entities.common;

public class SearchResultLine {
    int logLineIdx;
    Integer [] searchIdx;

    public SearchResultLine(int logLineIdx, Integer[] searchIdx) {
        this.logLineIdx = logLineIdx;
        this.searchIdx = searchIdx;
    }

    public int getLogLineIdx() {
        return logLineIdx;
    }

    public void setLogLineIdx(int logLineIdx) {
        this.logLineIdx = logLineIdx;
    }

    public Integer[] getSearchIdx() {
        return searchIdx;
    }

    public void setSearchIdx(Integer[] searchIdx) {
        this.searchIdx = searchIdx;
    }

    public int numberOfResults() {
        return searchIdx.length;
    }
}
