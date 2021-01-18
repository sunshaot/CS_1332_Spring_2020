import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Shaotong Sun
 * @version 1.0
 * @userid ssun319
 * @GTID 903453285
 *
 * Collaborators: No
 *
 * Resources: Textbook
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Error, pattern cannot be null or 0 length!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Error, text cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        int m = pattern.length();
        int n = text.length();
        ArrayList<Integer> result = new ArrayList<>();
        for (int t = 0; t <= n - m; t++) {
            int i = 0;
            while (i <= m - 1) {
                if (comparator.compare(pattern.charAt(i), text.charAt(i + t)) == 0) {
                    if (i < m - 1) {
                        i++;
                    } else {
                        result.add(t);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Error, pattern cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        int[] table = new int[pattern.length()];
        if (pattern.length() == 0) {
            return table;
        }
        table[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                table[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                table[j] = 0;
                j++;
            } else {
                i = table[i - 1];
            }
        }
        return table;

    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Error, pattern cannot be null or 0 length!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Error, text cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        ArrayList<Integer> result = new ArrayList<>();
        int j = 0;
        int k = 0;
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            return result;
        }
        int[] table = buildFailureTable(pattern, comparator);
        while (k < text.length()) {
            if (comparator.compare(pattern.charAt(j), text.charAt(k)) == 0) {
                if (j == m - 1) {
                    result.add(k - m + 1);
                    j = table[j];
                    k++;
                } else {
                    j++;
                    k++;
                }
            } else if (j == 0) {
                k++;
                if (k + m - j > n) {
                    break;
                }
            } else if (k + m - j > n) {
                break;
            } else {
                j = table[j - 1];
                if (k + m - j > n) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Error, pattern cannot be null!");
        }
        Map<Character, Integer> table = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Error, pattern cannot be null or 0 length!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Error, text cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, comparator cannot be null!");
        }
        Map<Character, Integer> table = buildLastTable(pattern);
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        int j;
        int shift;
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                result.add(i);
                i++;
            } else {
                if (table.containsKey(text.charAt(i + j))) {
                    shift = table.get(text.charAt(i + j));
                } else {
                    shift = -1;
                }
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }

        }
        return result;
    }
}
