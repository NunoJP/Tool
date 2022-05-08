package general.strutures;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestSuffixTree {


    @Test
    public void testBasicTreeCreation() {
        String t = "CAGTCAGG";

        SuffixTree tree = new SuffixTree(t);

        SfNode head = tree.getTree(true);

        assertEquals("\0", head.getString());
        assertEquals(-1, head.getPosition());
        ArrayList<SfNode> headNodes = head.getChildNodes();
        assertEquals(4, headNodes.size());

        SfNode gNode = headNodes.get(0);
        assertEquals("G", gNode.getString());
        assertEquals(-1, gNode.getPosition());

        SfNode agNode = headNodes.get(1);
        assertEquals("AG", agNode.getString());
        assertEquals(-1, agNode.getPosition());

        SfNode cagNode = headNodes.get(2);
        assertEquals("CAG", cagNode.getString());
        assertEquals(-1, cagNode.getPosition());

        SfNode tcagNode = headNodes.get(3);
        assertEquals("TCAGG", tcagNode.getString());
        assertEquals(3, tcagNode.getPosition());

        ArrayList<SfNode> gNodeNodes = gNode.getChildNodes();
        assertEquals(3, gNodeNodes.size());


        SfNode ggNode = gNodeNodes.get(0);
        assertEquals("G", ggNode.getString());
        assertEquals(6, ggNode.getPosition());

        SfNode $Node = gNodeNodes.get(1);
        assertEquals("$", $Node.getString());
        assertEquals(7, $Node.getPosition());

        SfNode gtcaggNode = gNodeNodes.get(2);
        assertEquals("TCAGG", gtcaggNode.getString());
        assertEquals(2, gtcaggNode.getPosition());

        ArrayList<SfNode> agNodeNodes = agNode.getChildNodes();
        SfNode aggNode = agNodeNodes.get(0);
        assertEquals("G", aggNode.getString());
        assertEquals(5, aggNode.getPosition());

        SfNode agtcaggNode = agNodeNodes.get(1);
        assertEquals("TCAGG", agtcaggNode.getString());
        assertEquals(1, agtcaggNode.getPosition());

        ArrayList<SfNode> cagNodeNodes = cagNode.getChildNodes();
        SfNode caggNode = cagNodeNodes.get(0);
        assertEquals("G", caggNode.getString());
        assertEquals(4, caggNode.getPosition());

        SfNode tcaggNode = cagNodeNodes.get(1);
        assertEquals("TCAGG", tcaggNode.getString());
        assertEquals(0, tcaggNode.getPosition());
    }

    @Test
    public void testLargerString() {
        String t = "GAYGEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        SfNode head = tree.getTree(true);

        assertEquals("\0", head.getString());
        assertEquals(-1, head.getPosition());
        ArrayList<SfNode> headNodes = head.getChildNodes();
        assertEquals(6, headNodes.size());

        // G - $
        // G - EGAFDGAFG
        // G - A - YGEGAFDGAFG
        // G - A - F - G
        // G - A - F - DGAFG
        // EGAFDGAFG
        // YGEGAFDGAFG
        // DGAFG
        // F - G
        // F - DGAFG
        // A - YGEGAFDGAFG
        // A - F - G
        // A - F - DGAFG


        int topLevelCtr = 0;

        int gFirstLevelCtr = 0;
        int gSecondLevelCtr = 0;
        int gThirdLevelCtr = 0;

        int fFirstLevelCtr = 0;
        int aFirstLevelCtr = 0;

        int aSecondLevelCtr = 0;

        for (SfNode node : headNodes) {
            switch (node.getString()) {
                case "G":
                    topLevelCtr++;
                    for (SfNode gNode : node.getChildNodes()) {
                        switch (gNode.getString()) {
                            case "$":
                            case "EGAFDGAFG":
                                gFirstLevelCtr++;
                                break;
                            case "A":
                                gFirstLevelCtr++;
                                for (SfNode g2ndLevelNode : gNode.getChildNodes()) {
                                    switch (g2ndLevelNode.getString()) {
                                        case "YGEGAFDGAFG":
                                            gSecondLevelCtr++;
                                            break;
                                        case "F":
                                            for (SfNode g3rdLevelNode : g2ndLevelNode.getChildNodes()) {
                                                switch (g3rdLevelNode.getString()) {
                                                    case "G":
                                                    case "DGAFG":
                                                        gThirdLevelCtr++;
                                                        break;
                                                }
                                            }
                                            gSecondLevelCtr++;
                                            break;
                                    }
                                }
                                break;
                        }
                    }
                    break;
                case "EGAFDGAFG":
                case "YGEGAFDGAFG":
                case "DGAFG":
                    topLevelCtr++;
                    break;
                case "F":
                    for (SfNode childNode : node.getChildNodes()) {
                        switch (childNode.getString()) {
                            case "DGAFG":
                            case "G":
                                fFirstLevelCtr++;
                                break;
                        }
                    }
                    topLevelCtr++;
                    break;
                case "A":
                    for (SfNode childNode : node.getChildNodes()) {
                        switch (childNode.getString()) {
                            case "YGEGAFDGAFG":
                                aFirstLevelCtr++;
                                break;
                            case "F":
                                for (SfNode childNode1 : childNode.getChildNodes()) {
                                    switch (childNode1.getString()) {
                                        case "DGAFG":
                                        case "G":
                                            aSecondLevelCtr++;
                                            break;
                                    }
                                }
                                aFirstLevelCtr++;
                                break;
                        }
                    }
                    topLevelCtr++;
                    break;
            }
        }


        // G - $
        // G - EGAFDGAFG
        // G - A - YGEGAFDGAFG
        // G - A - F - G
        // G - A - F - DGAFG
        // EGAFDGAFG
        // YGEGAFDGAFG
        // DGAFG
        // F - G
        // F - DGAFG
        // A - YGEGAFDGAFG
        // A - F - G
        // A - F - DGAFG

        assertEquals(6, topLevelCtr);

        assertEquals(3, gFirstLevelCtr);
        assertEquals(2, gSecondLevelCtr);
        assertEquals(2, gThirdLevelCtr);

        assertEquals(2, fFirstLevelCtr);

        assertEquals(2, aFirstLevelCtr);
        assertEquals(2, aSecondLevelCtr);

    }


    @Test
    public void testBasicTreeSearchSingleChar() {
        String t = "CAGTCAGG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("G", false).stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(2, indexes.get(0).intValue());
        assertEquals(6, indexes.get(1).intValue());
        assertEquals(7, indexes.get(2).intValue());

    }

    @Test
    public void testBasicTreeSearchTwoChars() {
        String t = "CAGTCAGG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("CA", false).stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = tree.getIndexes("GG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(6, indexes.get(0).intValue());
    }

    @Test
    public void testBasicTreeSearchThreeChars() {
        String t = "CAGTCAGG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("CAG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(4, indexes.get(1).intValue());

        indexes = tree.getIndexes("AGG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
    }


    @Test
    public void testLargerStringSearchSingleChar() {
        String t = "GAYGEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("G", false).stream().sorted().collect(Collectors.toList());

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

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("GA", false).stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(5, indexes.get(1).intValue());
        assertEquals(9, indexes.get(2).intValue());

        indexes = tree.getIndexes("FG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(11, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchThreeChars() {
        String t = "GAYGEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("GAF", false).stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
        assertEquals(9, indexes.get(1).intValue());

        indexes = tree.getIndexes("AFG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(10, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchSingleCharCaseSensitive() {
        String t = "GAYGEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("G", true).stream().sorted().collect(Collectors.toList());

        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());


        indexes = tree.getIndexes("g", true).stream().sorted().collect(Collectors.toList());

        assertEquals(0, indexes.size());
    }

    @Test
    public void testLargerStringSearchTwoCharsCaseSensitive() {
        String t = "gAYgEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("gA", true).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(0, indexes.get(0).intValue());

        indexes = tree.getIndexes("fg", true).stream().sorted().collect(Collectors.toList());

        assertEquals(0, indexes.size());
    }

    @Test
    public void testLargerStringSearchThreeCharsCaseSensitive() {
        String t = "GAYGEGAFDGafG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("Gaf", true).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(9, indexes.get(0).intValue());

        indexes = tree.getIndexes("AFG", true).stream().sorted().collect(Collectors.toList());

        assertEquals(0, indexes.size());
    }

    @Test
    public void testLargerStringSearchSingleCharNonCaseSensitive() {
        String t = "GAYGEGAFDGAFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("G", false).stream().sorted().collect(Collectors.toList());

        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());


        indexes = tree.getIndexes("g", false).stream().sorted().collect(Collectors.toList());


        assertEquals(5, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(3, indexes.get(1).intValue());
        assertEquals(5, indexes.get(2).intValue());
        assertEquals(9, indexes.get(3).intValue());
        assertEquals(12, indexes.get(4).intValue());
    }

    @Test
    public void testLargerStringSearchTwoCharsNonCaseSensitive() {
        String t = "GAYGEGAFDgaFG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("gA", false).stream().sorted().collect(Collectors.toList());

        assertEquals(3, indexes.size());
        assertEquals(0, indexes.get(0).intValue());
        assertEquals(5, indexes.get(1).intValue());
        assertEquals(9, indexes.get(2).intValue());

        indexes = tree.getIndexes("fG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(11, indexes.get(0).intValue());
    }

    @Test
    public void testLargerStringSearchThreeCharsNonCaseSensitive() {
        String t = "GayGEGafDGAfG";

        SuffixTree tree = new SuffixTree(t);

        List<Integer> indexes = tree.getIndexes("Gaf", false).stream().sorted().collect(Collectors.toList());

        assertEquals(2, indexes.size());
        assertEquals(5, indexes.get(0).intValue());
        assertEquals(9, indexes.get(1).intValue());

        indexes = tree.getIndexes("AFG", false).stream().sorted().collect(Collectors.toList());

        assertEquals(1, indexes.size());
        assertEquals(10, indexes.get(0).intValue());
    }

}
