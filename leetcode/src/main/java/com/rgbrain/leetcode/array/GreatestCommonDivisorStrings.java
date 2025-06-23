package com.rgbrain.leetcode.array;

import java.util.ArrayList;

public class GreatestCommonDivisorStrings {

    public static void main(String[] args) {
        execute("ABABAB", "ABAB");
    }

    public static String execute(String str1, String str2) {
        var divisor = new StringBuilder();

        for (int i = 1; i < str1.length(); i++) {
            var chunks = new ArrayList<String>();

            for (int j = 0; j < str1.length(); j+=i) {
                var start = j;
                var end = Math.min(str1.length(), start + i);
                chunks.add(str1.substring(start, end));
            }

            System.out.println(chunks);
            
        }
        return "OI";
    }

}
