package general.strutures;

import general.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SuffixArray implements ISearchStructure {


    protected String source;
    protected List<Integer> suffixIndexes;
    protected int [] LCP;

    public SuffixArray createSuffixArray(String source) {
        this.source = source;
        suffixIndexes = new ArrayList<>(source.length());
        LCP = new int[source.length()];
        process();
        return this;
    }

    private void process() {
        List<Pair<Integer, String>> suffixes = new ArrayList<>(source.length());
        int stIdx = 0;
        for (int i = source.length()-1; i >= 0;  --i) {
            suffixes.add(stIdx, Pair.of(i, source.substring(i)));
            stIdx++;
        }

        List<Pair<Integer, String>> collect = suffixes.stream().sorted(Comparator.comparing(Pair::getRight)).collect(Collectors.toList());
        suffixIndexes = collect.stream().map(Pair::getLeft).collect(Collectors.toList());

        // create LCP
        char[] first = null;
        char[] second;
        int lcpIdx = 0;
        for (Pair<Integer, String> pair : collect) {
            String s = pair.getRight();
            if(first == null) {
                LCP[lcpIdx] = 0;
                first = s.toCharArray();
            } else {
                second = s.toCharArray();
                int ctr = 0;
                int length = Math.min(first.length, second.length);
                for (int i = 0; i <= length -1; i++) {
                    if(first[i] == second[i]) {
                        ctr++;
                    } else {
                        break;
                    }
                }
                LCP[lcpIdx] = ctr;
                first = second;
            }
            lcpIdx++;
        }
        suffixes = null;

    }

    public List<Integer> getSuffixIndexes() {
        return suffixIndexes;
    }

    public int[] getLCP() {
        return LCP;
    }

    @Override
    public List<Integer> searchStringIndexes(String toFind) {
        List<Integer> toRet = new ArrayList<>();

        int left = findLeftLimit(0, suffixIndexes.size()-1, toFind);
        int right = findRightLimit(0, suffixIndexes.size()-1, toFind);

        if(left == -1 && right == -1) {
            return toRet;
        }

        for (int i = left; i <= right; i++) {
            toRet.add(suffixIndexes.get(i));
        }

        return toRet;
    }

    @Override
    public List<Integer> searchStringIndexes(String toFind, boolean caseSensitive) {
        return searchStringIndexes(toFind);
    }

    /*
       Negative number means that the toFind is larger than the currString
    */
    static int compareStrings(char[] currStr, char[] toFind, int toFindLength) {

        int length = Math.min(currStr.length, toFind.length);

        for (int i = 0; i < length; i++) {
            if(currStr[i] != toFind[i]) {
                return currStr[i] - toFind[i];
            }
        }
        // if the current string is smaller than the toFind, then there are some shared chars, we consider the
        // current to be before the toFind
        if(currStr.length < toFindLength) {
            return -1;
        }
        return 0;
    }


    private int findLeftLimit(int left, int right, String toFind) {
        if(left > right) {
            return -1;
        }

        int mid = left + (right-left)/2;
        String currStr = source.substring(suffixIndexes.get(mid));
        int res = compareStrings(currStr.toCharArray(), toFind.toCharArray(), toFind.length());

        if(res == 0) {
            // if the LCP is equal or above the size of the string to find, then we have more hits to the left
            while(LCP[mid] >= toFind.length() && mid > 0) {
                mid--;
            }
            return mid;
        }
        // if the value in mid is smaller than the toFind
        if(res < 0) {
            return findLeftLimit(mid + 1, right, toFind);
        } else {
            return findLeftLimit(left, mid - 1, toFind);
        }

    }

    private int findRightLimit(int left, int right, String toFind) {
        if(right < left) {
            return -1;
        }

        int mid = left + (right-left)/2;
        String currStr = source.substring(suffixIndexes.get(mid));
        int res = compareStrings(currStr.toCharArray(), toFind.toCharArray(), toFind.length());

        if(res == 0) {
            // if the LCP is equal or above the size of the string to find, then we have more hits to the left
            while(LCP[mid + 1] >= toFind.length() && mid < suffixIndexes.size() - 2) {
                mid++;
            }
            return mid;
        }
        // if the value in mid is smaller than the toFind
        if(res < 0) {
            return findRightLimit(mid + 1, right, toFind);
        } else {
            return findRightLimit(left, mid - 1, toFind);
        }
    }

}
