package general.strutures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SuffixTree {

    private final SfNode head;
    private final SfNode headLowerCase;

    public SuffixTree(String t) {
        head = new SfNode(-1, "\0");
        headLowerCase = new SfNode(-1, "\0");
        String originalStringLowerCase = t.toLowerCase();
        createTree(t, head);
        createTree(originalStringLowerCase, headLowerCase);
    }

    private void createTree(String text, SfNode currHead) {
        String currStr = "";
        for (int i = text.length()-1; i >= 0; i--) {
                currStr = text.substring(i);
                char[] chars = currStr.toCharArray();
                boolean foundMatch = false;
                for (SfNode node : currHead.getChildNodes()) {
                    if(node.getString().startsWith(String.valueOf(chars[0]))) {
                        foundMatch = true;
                        // dive deeper
                        drillDown(node, chars, i);
                        break;
                    }
                }
            // The char does not exist in the headNode
            // create the new node
            if(!foundMatch) {
                currHead.addChildNode(new SfNode(i, currStr));
            }

        }
    }

    private void drillDown(SfNode parentNode, char[] chars, int totalPosition) {
        String nodeString = parentNode.getString();
        char[] nodeChars = nodeString.toCharArray();

        if(nodeChars.length > chars.length) {
            return; // this should not happen
        }

        // Find where the strings start to become different
        int lastCommonIdx = nodeChars.length;
        for (int i = 0; i < nodeChars.length; i++) {
            if(nodeChars[i] != chars[i]) {
                lastCommonIdx = i - 1;
                break;
            }
        }

        if(lastCommonIdx < 0) { // the node above matched but this one has no match
            // create a new child node with the split text
            parentNode.addChildNode(new SfNode(totalPosition, new String(chars)));
        } else if(lastCommonIdx < nodeChars.length) {

            // create the new node
            SfNode newNode = new SfNode(totalPosition, new String(Arrays.copyOfRange(chars, lastCommonIdx + 1, chars.length)));

            // create the split node
            SfNode splitNode = new SfNode(parentNode.getPosition(), nodeString.substring(lastCommonIdx + 1));
            splitNode.setChildren(parentNode.getChildNodes());

            // update the node with the new shorter string and remove the position
            parentNode.setString(nodeString.substring(0, lastCommonIdx + 1));
            parentNode.setPosition(-1);
            parentNode.setChildren(new ArrayList<>());
            parentNode.addChildNode(splitNode);
            parentNode.addChildNode(newNode);

        } else if(lastCommonIdx == nodeChars.length) { // the exact length of the node string and the received string matched
            boolean foundMatch = false;
            for (SfNode childNode : parentNode.getChildNodes()) {
                if (childNode.getString().startsWith(getPrefix(lastCommonIdx, chars))) {
                    drillDown(childNode, Arrays.copyOfRange(chars, lastCommonIdx, chars.length), totalPosition);
                    foundMatch = true;
                    break;
                }
            }
            if(!foundMatch) {
                // create a new child node with the split text
                parentNode.addChildNode(new SfNode(totalPosition,
                        new String(Arrays.copyOfRange(chars, lastCommonIdx, chars.length))));
                parentNode.addChildNodeIfNotExists(new SfNode(parentNode.getPosition(), "$"));
                parentNode.setPosition(-1);
            }
        }
    }

    public SfNode getTree(boolean isCaseSensitive) {
        return isCaseSensitive ? head : headLowerCase;
    }

    public List<Integer> getIndexes(String toFind, boolean isCaseSensitive) {
        SfNode currHead = getTree(isCaseSensitive);

        ArrayList<Integer> toRet = new ArrayList<>();

        char[] chars = toFind.toCharArray();
        for (SfNode node : currHead.getChildNodes()) {
            if(node.getString().startsWith(getPrefix(0, chars, isCaseSensitive))) {
                // dive deeper
                toRet.addAll(drillDownSearch(node, chars, isCaseSensitive));
                break;
            }
        }


        return toRet.stream().distinct().collect(Collectors.toList());
    }

    private List<Integer> drillDownSearch(SfNode parentNode, char[] receivedChars, boolean isCaseSensitive) {
        String nodeString = parentNode.getString();
        char[] nodeChars = nodeString.toCharArray();
        char[] charsToFind;
        if(isCaseSensitive) {
            charsToFind = receivedChars;
        } else {
            charsToFind = new String(receivedChars).toLowerCase().toCharArray();
        }

        int smallestSize = Math.min(nodeChars.length, charsToFind.length);

        // Find where the strings start to become different
        int lastCommonIdx = smallestSize;
        for (int i = 0; i < smallestSize; i++) {
            if(nodeChars[i] != charsToFind[i]) {
                lastCommonIdx = i - 1;
                break;
            }
        }

        if(lastCommonIdx < 0) { // the node above matched but this one has no match
            return findAllPositions(parentNode);
        } else if(lastCommonIdx < nodeChars.length && lastCommonIdx >= charsToFind.length) {
            // the node above matched, but this one matched only part
            // the index found must be the same size of the chars to find, else we would have false hits
            // like in AFD would be found in AFGE as the index is the second position
            return findAllPositions(parentNode);
        } else if(lastCommonIdx == nodeChars.length) { // the node above matched, and this one matched fully

            // if the chars matched exactly, but the lasCommonIdx would go over the size of the string to find
            // then there was an exact match with this node and no nodes below
            if(lastCommonIdx >= charsToFind.length) {
                return findAllPositions(parentNode);
            }

            // if not, then we have to drill down on the child nodes
            boolean foundMatch = false;
            List<Integer> drillDownSearch = new ArrayList<>();
            for (SfNode childNode : parentNode.getChildNodes()) {
                if (childNode.getString().startsWith(getPrefix(lastCommonIdx, charsToFind, isCaseSensitive))) {
                    drillDownSearch = drillDownSearch(childNode, Arrays.copyOfRange(charsToFind, lastCommonIdx, charsToFind.length), isCaseSensitive);
                    foundMatch = true;
                    break;
                }
            }
            if(!foundMatch) {
                return findAllPositions(parentNode);
            } else {
                return drillDownSearch;
            }
        }
        return new ArrayList<>(0);
    }

    private List<Integer> findAllPositions(SfNode parentNode) {
        List<Integer> indexes = new ArrayList<>();

        if(parentNode.getPosition() >= 0) {
            indexes.add(parentNode.getPosition());
        }
        for (SfNode childNode : parentNode.getChildNodes()) {
            indexes.addAll(findAllPositions(childNode));
        }
        return indexes;
    }


    private String getPrefix(int i, char[] chars, boolean isCaseSensitive) {
        String valueOf = String.valueOf(chars[i]);
        return isCaseSensitive ? valueOf : valueOf.toLowerCase();
    }

    private String getPrefix(int i, char[] chars) {
        return getPrefix(i, chars, true);
    }


}
