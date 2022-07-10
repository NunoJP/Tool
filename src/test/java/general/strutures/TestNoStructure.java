package general.strutures;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestNoStructure {

    @Test
    public void testBasicCreation() {
        String t = "CAGTCAGG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        assertEquals(t, nostructure.getSource());
    }


    @Test
    public void testBasicTreeSearchSingleChar() {
        String t = "CAGTCAGG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(2, indexes.get(0).intValue());
        assertEquals(6, indexes.get(1).intValue());
        assertEquals(7, indexes.get(2).intValue());
    }

    @Test
    public void testBasicTreeSearchTwoChars() {
        String t = "CAGTCAGG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("CA").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = nostructure.searchStringIndexes("GG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(6, indexes.get(0).intValue());
    }

    @Test
    public void testBasicTreeSearchThreeChars() {
        String t = "CAGTCAGG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("CAG").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = nostructure.searchStringIndexes("AGG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
    }


    @Test
    public void testLargerStringSearchSingleChar() {
        String t = "GAYGEGAFDGAFG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());
    }

    @Test
    public void testLargerStringSearchTwoChars() {
        String t = "GAYGEGAFDGAFG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("GA").stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(5, indexes.get(1).intValue());
        assertEquals(9, indexes.get(2).intValue());

        indexes = nostructure.searchStringIndexes("FG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(11, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchThreeChars() {
        String t = "GAYGEGAFDGAFG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);

        List<Integer> indexes = nostructure.searchStringIndexes("GAF").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
        assertEquals(9, indexes.get(1).intValue());

        indexes = nostructure.searchStringIndexes("AFG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(10, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchSingleCharCaseSensitive() {
        String t = "GAYGEGAFDGAFG";

        NoStructure nostructure = new NoStructure(t);
        assertNotNull(nostructure);
        List<Integer> indexes = nostructure.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());


        indexes = nostructure.searchStringIndexes("g").stream().sorted().collect(Collectors.toList());

        assertEquals(0, indexes.size());
    }

}
