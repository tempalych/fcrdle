package com.tempalych.fcrdle.wikidata;

import java.util.HashSet;
import java.util.Set;

public class StringCompareUtils {
    private final static Double SIMILARITY = 0.6;

    public static SimilarityType compareStrings(String str1, String str2) {
        if (str1.equals(str2)) {
            return SimilarityType.EQUALS;
        }

        double similarity = calculateJaccardSimilarity(str1, str2);
        if (similarity >= SIMILARITY) {
            return SimilarityType.SIMILAR;
        } else {
            return SimilarityType.DIFFERENT;
        }
    }

    public static double calculateJaccardSimilarity(String str1, String str2) {
        Set<Character> set1 = new HashSet<>();
        for (char c : str1.toCharArray()) {
            set1.add(c);
        }

        Set<Character> set2 = new HashSet<>();
        for (char c : str2.toCharArray()) {
            set2.add(c);
        }

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    public static int calculateLevenshteinDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]) + 1,
                            dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1));
                }
            }
        }

        return dp[len1][len2];
    }
}
