package general.strutures;

import general.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SuffixArray {


    protected String source;
    protected List<Integer> suffixIndexes;
    protected int [] LCP;
    private char firstCharFirstElement;
    private char firstCharLastElement;

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
        firstCharFirstElement = collect.get(0).getRight().charAt(0);
        firstCharLastElement = collect.get(collect.size()-1).getRight().charAt(0);
        suffixIndexes = collect.stream().map(Pair::getLeft).collect(Collectors.toList());

        // create LCP
        char[] first = null;
        char[] second;
        int lcpIdx = 0;
        for (Pair<Integer, String> pair : collect) {
            String s = pair.getRight();
            if(first == null) {
                LCP[0] = 0;
                first = s.toCharArray();
            } else {
                second = s.toCharArray();
                int ctr = 0;
                int length = Math.min(first.length, second.length);
                for (int i = 0; i < length -1; i++) {
                    if(first[i] == second[i]) {
                        ctr++;
                    } else {
                        LCP[lcpIdx] = ctr;
                        lcpIdx++;
                        break;
                    }
                }
            }
        }
        suffixes = null;

    }

    public List<Integer> getSuffixIndexes() {
        return suffixIndexes;
    }

    public int[] getLCP() {
        return LCP;
    }

    public List<Integer> getIndexes(String toFind) {
        List<Integer> toRet = new ArrayList<>();

        boolean foundAlready = false;

        if(Math.abs(source.substring(suffixIndexes.get(0)).charAt(0) - firstCharFirstElement) <
                Math.abs(source.substring(suffixIndexes.get(0)).charAt(0) - firstCharLastElement)) {
            // the string to find is closer to the beginning
            for (int i = 0; i < suffixIndexes.size(); ++i) {
                String currStr = source.substring(suffixIndexes.get(i));
                int res = compareStrings(currStr.toCharArray(), toFind.toCharArray(), toFind.length());
                if(res == 0) {
                    toRet.add(suffixIndexes.get(i));
                    foundAlready = true;
                } else if (foundAlready){
                    return toRet;
                }
            }
        } else { // the string to find is closer to the end
            for (int i = suffixIndexes.size()-1; i >= 0; --i) {
                String currStr = source.substring(suffixIndexes.get(i));
                int res = compareStrings(currStr.toCharArray(), toFind.toCharArray(), toFind.length());
                if(res == 0) {
                    toRet.add(suffixIndexes.get(i));
                    foundAlready = true;
                } else if (foundAlready){
                    return toRet;
                }
            }
        }
        return toRet;
    }

    static int compareStrings(char[] currStr, char[] toFind, int toFindLength) {
        if(currStr.length < toFind.length) {
            return -1;
        }

        for (int i = 0; i < toFindLength; i++) {
            if(currStr[i] != toFind[i]) {
                return currStr[i] - toFind[i];
            }
        }
        return 0;
    }

}
