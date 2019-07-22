package com.shortening.utils;

import java.util.Random;

public class Base62 {

	static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	/**
	 * Shortening Key(shorturl) 생성 메소드
	 * Base64기준 '+', '/' 제외 한 62가지로 난수 처리
	 * 1~8자리의 자리 수 별로 확률 처리
	 * @return String
	 */
	public static String encode() {
		final StringBuilder sb = new StringBuilder();

		Random randomLength = new Random();

		int length = 0;
		int resultLength = randomLength.nextInt(10000);
		if (resultLength == 0) {			// 1자리 0.01%	0
			length = 1;
		} else if (resultLength <= 2) {		// 2자리 0.02%	1~2
			length = 2;
		} else if (resultLength <= 5) {		// 3자리 0.03%	3~5
			length = 3;
		} else if (resultLength <= 9) {		// 4자리 0.04%	6~9
			length = 4;
		} else if (resultLength <= 19) {	// 5자리 0.1%		10~19
			length = 5;
		} else if (resultLength <= 49) {	// 6자리 0.3%		20~49
			length = 6;
		} else if (resultLength <= 99) {	// 7자리 0.5%		50~99
			length = 7;
		} else if (resultLength <= 9999) {	// 8자리 99%		100~9999
			length = 8;
		}

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(BASE62[random.nextInt(BASE62.length)]);
		}
		return sb.toString();
	}
}