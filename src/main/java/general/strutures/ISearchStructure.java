package general.strutures;

import java.util.List;

public interface ISearchStructure {

    List<Integer> searchStringIndexes(String toFind);

    List<Integer> searchStringIndexes(String toFind, boolean caseSensitive);
}
