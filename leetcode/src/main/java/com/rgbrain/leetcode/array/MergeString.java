package com.rgbrain.leetcode.array;

public class MergeString {	

	public String merge(String word1, String word2) {
		var sb = new StringBuilder();
		var lengthWord1 = word1.length();
		var lengthWord2 = word2.length();

		var index = lengthWord1 > lengthWord2 ? lengthWord1 : lengthWord2;

		for (int i = 0; i < index; i++) {
			
			if (word1.length() > i) {
				sb.append(word1.charAt(i));
			}

			if (word2.length() > i) {
				sb.append(word2.charAt(i));
			}
		}

		return sb.toString();
	}
}
