package general.strutures;

import java.util.ArrayList;
import java.util.List;

public class NoStructure implements ISearchStructure{

    private final String source;

    public NoStructure(String source) {
        this.source = source;
    }

    @Override
    public List<Integer> searchStringIndexes(String toFind) {
        List<Integer> ctr = new ArrayList<>();
        int prevIdx = toFind.length() * -1; // to offset the first time
        while (true) {
            int indexOf = source.indexOf(toFind, prevIdx + toFind.length());
            if (indexOf == -1) { break; }
            ctr.add(indexOf);
            prevIdx = indexOf;
        }
        return ctr;
    }

    @Override
    public List<Integer> searchStringIndexes(String toFind, boolean caseSensitive) {
        return searchStringIndexes(toFind);
    }

    public String getSource() {
        return source;
    }
}
