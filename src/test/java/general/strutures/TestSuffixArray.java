package general.strutures;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestSuffixArray {




    @Test
    public void testBasicTreeCreation() {
        String t = "CAGTCAGG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);
        /*
            AGG	        5
            AGTCAGG	    1
            CAGG	    4
            CAGTCAGG	0
            G	        7
            GG	        6
            GTCAGG	    2
            TCAGG	    3
         */
        String [] sortedSuffixes = new String[] {
                "AGG",
                "AGTCAGG",
                "CAGG",
                "CAGTCAGG",
                "G",
                "GG",
                "GTCAGG",
                "TCAGG"
        };

        List<Integer> suffixIndexes = suffixArray.getSuffixIndexes();
        assertEquals(sortedSuffixes.length, suffixIndexes.size());

        // For each given suffixIndex, obtain the suffix from the original string and compare it to
        // to the expected suffix
        for (int i = 0; i < sortedSuffixes.length; i++) {
            assertEquals(sortedSuffixes[i], t.substring(suffixIndexes.get(i)));
        }
    }


    @Test
    public void testLargerString() {
        String t = "GAYGEGAFDGAFG";
        /*
            AFDGAFG			6
            AFG				10
            AYGEGAFDGAFG	1
            DGAFG			8
            EGAFDGAFG		4
            FDGAFG			7
            FG				11
            G				12
            GAFDGAFG		5
            GAFG			9
            GAYGEGAFDGAFG	0
            GEGAFDGAFG		3
            YGEGAFDGAFG		2
         */
        String [] sortedSuffixes = new String[] {
                "AFDGAFG",
                "AFG",
                "AYGEGAFDGAFG",
                "DGAFG",
                "EGAFDGAFG",
                "FDGAFG",
                "FG",
                "G",
                "GAFDGAFG",
                "GAFG",
                "GAYGEGAFDGAFG",
                "GEGAFDGAFG",
                "YGEGAFDGAFG",
        };

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> suffixIndexes = suffixArray.getSuffixIndexes();
        assertEquals(sortedSuffixes.length, suffixIndexes.size());

        // For each given suffixIndex, obtain the suffix from the original string and compare it to
        // to the expected suffix
        for (int i = 0; i < sortedSuffixes.length; i++) {
            assertEquals(sortedSuffixes[i], t.substring(suffixIndexes.get(i)));
        }
    }

    @Test
    public void testBasicTreeSearchSingleChar() {
        String t = "CAGTCAGG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(2, indexes.get(0).intValue());
        assertEquals(6, indexes.get(1).intValue());
        assertEquals(7, indexes.get(2).intValue());
    }

    @Test
    public void testBasicTreeSearchTwoChars() {
        String t = "CAGTCAGG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("CA").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = suffixArray.searchStringIndexes("GG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(6, indexes.get(0).intValue());
    }

    @Test
    public void testBasicTreeSearchThreeChars() {
        String t = "CAGTCAGG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("CAG").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = suffixArray.searchStringIndexes("AGG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
    }


    @Test
    public void testLargerStringSearchSingleChar() {
        String t = "GAYGEGAFDGAFG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

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

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("GA").stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(5, indexes.get(1).intValue());
        assertEquals(9, indexes.get(2).intValue());

        indexes = suffixArray.searchStringIndexes("FG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(11, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchThreeChars() {
        String t = "GAYGEGAFDGAFG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);

        List<Integer> indexes = suffixArray.searchStringIndexes("GAF").stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
        assertEquals(9, indexes.get(1).intValue());

        indexes = suffixArray.searchStringIndexes("AFG").stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(10, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchSingleCharCaseSensitive() {
        String t = "GAYGEGAFDGAFG";

        SuffixArray suffixArray = new SuffixArray().createSuffixArray(t);
        assertNotNull(suffixArray);
        List<Integer> indexes = suffixArray.searchStringIndexes("G").stream().sorted().collect(Collectors.toList());

        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());


        indexes = suffixArray.searchStringIndexes("g").stream().sorted().collect(Collectors.toList());

        assertEquals(0, indexes.size());
    }

    @Test
    public void testStringComparator() {
        String low = "AAA";
        String high = "ZZZ";

        // equal
        int comparison = SuffixArray.compareStrings(low.toCharArray(), "AAA".toCharArray(), 3);
        assertEquals(comparison, 0);

        comparison = SuffixArray.compareStrings(high.toCharArray(), "ZZZ".toCharArray(), 3);
        assertEquals(comparison, 0);

        // one equal and rest higher
        comparison = SuffixArray.compareStrings(low.toCharArray(), "ABB".toCharArray(), 3);
        assertEquals(comparison, -1);

        // one equal and rest lower
        comparison = SuffixArray.compareStrings(high.toCharArray(), "ZYY".toCharArray(), 3);
        assertEquals(comparison, 1);

        // two equal and rest higher
        comparison = SuffixArray.compareStrings(low.toCharArray(), "AAB".toCharArray(), 3);
        assertEquals(comparison, -1);

        // two equal and rest lower
        comparison = SuffixArray.compareStrings(high.toCharArray(), "ZZY".toCharArray(), 3);
        assertEquals(comparison, 1);

        // all higher
        comparison = SuffixArray.compareStrings(low.toCharArray(), "BBB".toCharArray(), 3);
        assertEquals(comparison, -1);

        // all lower
        comparison = SuffixArray.compareStrings(high.toCharArray(), "YYY".toCharArray(), 3);
        assertEquals(comparison, 1);

    }

//    @Test
//    public void testStringComparator() {
//        String low = "AAA";
//        String high = "ZZZ";
//
//        // equal
//        SuffixArray.Comparison comparison = SuffixArray.compareStrings(low, "AAA");
//        assertEquals(comparison.numberEqualChars, 3);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.EQUAL);
//
//        comparison = SuffixArray.compareStrings(high, "ZZZ");
//        assertEquals(comparison.numberEqualChars, 3);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.EQUAL);
//
//        // one equal and rest higher
//        comparison = SuffixArray.compareStrings(low, "ABB");
//        assertEquals(comparison.numberEqualChars, 1);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.BIGGER);
//
//        // one equal and rest lower
//        comparison = SuffixArray.compareStrings(high, "ZYY");
//        assertEquals(comparison.numberEqualChars, 1);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.SMALLER);
//
//        // two equal and rest higher
//        comparison = SuffixArray.compareStrings(low, "AAB");
//        assertEquals(comparison.numberEqualChars, 2);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.BIGGER);
//
//        // two equal and rest lower
//        comparison = SuffixArray.compareStrings(high, "ZZY");
//        assertEquals(comparison.numberEqualChars, 2);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.SMALLER);
//
//        // all higher
//        comparison = SuffixArray.compareStrings(low, "BBB");
//        assertEquals(comparison.numberEqualChars, 0);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.BIGGER);
//
//        // all lower
//        comparison = SuffixArray.compareStrings(high, "YYY");
//        assertEquals(comparison.numberEqualChars, 0);
//        assertEquals(comparison.comparisonResult, SuffixArray.ComparisonResult.SMALLER);
//
//    }

//    @Test
//    public void testAnalyseSurroundings() {
//        String [] source = new String[] {
//          "AAA", "AB", "ABB", "ABC", "ADD"
//        };
//
//        SuffixArrayWrapper suffixArrayWrapper = new SuffixArrayWrapper();
//        suffixArrayWrapper.setSuffixIndexes(List.of(new Integer[]{ 0, 1, 2, 3, 4}));
//        suffixArrayWrapper.setLCP(new int[] { 0, 1, 2, 2, 1 });
//        List<Integer> integerList = suffixArrayWrapper.analyseSurroundings(2, new ArrayList<>(), "AB");
//        assertEquals(3, integerList.size());
//
//
//    }

//    public class SuffixArrayWrapper extends SuffixArray {
////        @Override
////        public List<Integer> analyseSurroundings(int mid, ArrayList<Integer> res, String g) {
////            return super.analyseSurroundings(mid, res, g);
////        }
//
//        public void setLCP(int [] lcp) {
//            this.LCP = lcp;
//        }
//
//        public void setSuffixIndexes(List<Integer> suffixIndexes) {
//            this.suffixIndexes = suffixIndexes;
//        }
//    }

}
