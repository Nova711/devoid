package devoid_boosted;

import java.awt.Color;
import java.io.*;

import rpg.Out;

/**
 * Ex --- Contains random code that is needed globally.
 * 
 * @author Carter Rye
 */
public class Ex {
	@SuppressWarnings("unused")
	/**
	 * 
	 * 
	 * @param
	 */
	public static void pause() {
		System.out.println("Press Enter key to continue...");
		try {
			int read = System.in.read(new byte[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param
	 */
	public static void timeout(double timeS) {
		try {
			Thread.sleep((long) (timeS * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param
	 */
	public static int posNeg(int num) {
		int sign = 1;
		if (num < 0) {
			sign = -1;
		} else if (num == 0) {
			sign = 0;
		}
		return sign;
	}

	/**
	 * 
	 * 
	 * @param
	 */
	public static int getNumAt(int num, int index) {
		return (num % (int) Math.pow(10, index + 1)) / (int) Math.pow(10, index);
	}

	/**
	 * Returns the integer between the indices specified by the start and end
	 * arguments. <br />
	 * The indices go from right to left
	 * 
	 * @param num   The integer that contains the desired number
	 * @param start The starting index of the desired number
	 * @param end   The ending index of the desired number
	 * @return the number specified by the index
	 */
	public static int getNumAt(int num, int start, int end) {
		return (num % (int) Math.pow(10, start + end + 1)) / (int) Math.pow(10, start);
	}

	/**
	 * 
	 * 
	 * @param
	 */
	public static int getNumAt(long num, long index) {
		return (int) ((num % (int) Math.pow(10, index + 1)) / (int) Math.pow(10, index));
	}

	public static int randInt(int start, int end) {
		return (int) (Ex.rand(start, end));
	}

	public static double rand(double start, double end) {
		return Math.random() * (end - start + 1) + start;
	}

	public static boolean randBool() {
		boolean rand = false;
		if (randInt(0, 1) == 1)
			rand = true;
		return rand;
	}

	public static double percentError(double measured, double accepted) {
		return (measured - accepted) / accepted;
	}

	public static String[] reverse(String[] forward) {
		String[] temp = new String[forward.length];
		for (int i = temp.length - 1, j = 0; j < temp.length; i--, j++) {
			temp[j] = forward[i];
		}
		return temp;
	}

	public static int[] reverse(int[] forward) {
		int[] temp = new int[forward.length];
		for (int i = temp.length - 1, j = 0; j < temp.length; i--, j++) {
			temp[j] = forward[i];
		}
		return temp;
	}

	public static int[][] rotateCW(int[][] arr) {
		int[][] rotated = new int[arr[0].length][arr.length];
		for (int i = 0; i < arr[0].length; i++) {
			for (int j = arr.length - 1; j >= 0; j--) {
				rotated[i][arr.length - 1 - j] = arr[j][i];
			}
		}
		return rotated;
	}

	public static int[][] rotateCCW(int[][] arr) {
		int[][] rotated = new int[arr[0].length][arr.length];
		for (int i = 0; i < arr[0].length; i++) {
			for (int j = arr.length - 1; j >= 0; j--) {
				rotated[i][j] = arr[j][i];
			}
		}
		return rotated;
	}

	public static int maxValIndex(int[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > arr[max]) {
				max = i;
			}
		}
		return max;
	}

	public static int maxValIndex(float[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > arr[max]) {
				max = i;
			}
		}
		return max;
	}

	public static int minValIndex(float[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < arr[max]) {
				max = i;
			}
		}
		return max;
	}

	public static double maxVal(double[] arr) {
		double max = 0;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(arr[i], max);
		}
		return max;
	}

	public static int maxVal(int[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(arr[i], max);
		}
		return max;
	}

	public static String safeSubstring(String s, int beginIndex, int endIndex) {
		try {
			return s.substring(beginIndex, endIndex);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Converts an RGB color value to HSL. Conversion formula adapted from
	 * http://en.wikipedia.org/wiki/HSL_color_space. Assumes pR, pG, and bpBare
	 * contained in the set [0, 255] and returns h, s, and l in the set [0, 1].
	 *
	 * @param pR The red color value
	 * @param pG The green color value
	 * @param pB The blue color value
	 * @return float array, the HSL representation
	 */
	public static float[] rgbTohsl(int pR, int pG, int pB) {
		float r = pR / 255f;
		float g = pG / 255f;
		float b = pB / 255f;

		float max = (r > g && r > b) ? r : (g > b) ? g : b;
		float min = (r < g && r < b) ? r : (g < b) ? g : b;

		float h, s, l;
		l = (max + min) / 2.0f;

		if (max == min) {
			h = s = 0.0f;
		} else {
			float d = max - min;
			s = (l > 0.5f) ? d / (2.0f - max - min) : d / (max + min);

			if (r > g && r > b)
				h = (g - b) / d + (g < b ? 6.0f : 0.0f);

			else if (g > b)
				h = (b - r) / d + 2.0f;

			else
				h = (r - g) / d + 4.0f;

			h /= 6.0f;
		}

		float[] hsl = { h, s, l };
		return hsl;
	}

	/**
	 * Converts an HSL color value to RGB. Conversion formula adapted from
	 * http://en.wikipedia.org/wiki/HSL_color_space. Assumes h, s, and l are
	 * contained in the set [0, 1] and returns r, g, and b in the set [0, 255].
	 *
	 * @param h The hue
	 * @param s The saturation
	 * @param l The lightness
	 * @return int array, the RGB representation
	 */
	public static int[] hslTorgb(float h, float s, float l) {
		float r, g, b;

		if (s == 0f) {
			r = g = b = l; // achromatic
		} else {
			float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
			float p = 2 * l - q;
			r = hueToRgb(p, q, h + 1f / 3f);
			g = hueToRgb(p, q, h);
			b = hueToRgb(p, q, h - 1f / 3f);
		}
		int[] rgb = { to255(r), to255(g), to255(b) };
		return rgb;
	}

	public static int to255(float v) {
		return (int) Math.min(255, 256 * v);
	}

	/** Helper method that converts hue to rgb */
	public static float hueToRgb(float p, float q, float t) {
		if (t < 0f)
			t += 1f;
		if (t > 1f)
			t -= 1f;
		if (t < 1f / 6f)
			return p + (q - p) * 6f * t;
		if (t < 1f / 2f)
			return q;
		if (t < 2f / 3f)
			return p + (q - p) * (2f / 3f - t) * 6f;
		return p;
	}

	public static int[] findComplement(int r, int g, int b) {
		float[] hsl = rgbTohsl(r, g, b);
		hsl[0] += 0.5f;
		if (hsl[0] < 1f) {
			hsl[0] -= 1f;
		}
		return hslTorgb(hsl[0], hsl[1], hsl[2]);
	}

	public static Color findComplement(Color c) {
		float[] hsl = rgbTohsl(c.getRed(), c.getGreen(), c.getBlue());
		hsl[0] += 0.5f;
		if (hsl[0] < 1f) {
			hsl[0] -= 1f;
		}
		int[] rgb = hslTorgb(hsl[0], hsl[1], hsl[2]);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public static Color lighter(Color c) {
		float[] hsl = rgbTohsl(c.getRed(), c.getGreen(), c.getBlue());
		hsl[2] *= 240;
		hsl[2] += 10;
		hsl[2] /= 240;
		int[] rgb = hslTorgb(hsl[0], hsl[1], hsl[2]);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public static Color dimmer(Color c) {
		float[] hsl = rgbTohsl(c.getRed(), c.getGreen(), c.getBlue());
		hsl[2] *= 240;
		hsl[2] -= 10;
		hsl[2] /= 240;
		int[] rgb = hslTorgb(hsl[0], hsl[1], hsl[2]);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public static Object[] addToArray(Object[] objArr, Object obj) {
		Object[] objectArr = new Object[objArr.length + 1];
		for (int i = 0; i < objArr.length; i++) {
			objectArr[i] = objArr[i];
		}
		objectArr[objectArr.length - 1] = obj;
		return objectArr;
	}

	public static int[] parseIntArray(String[] strArr) {
		int[] intArr = new int[strArr.length];
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}
		return intArr;
	}

	public static boolean parseBool(String s) {
		boolean answer = false;
		if (s.equalsIgnoreCase("true")) {
			answer = true;
		}
		return answer;
	}

	public static boolean containsSameElement(String[] arr1, String[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (isContainedIn(arr2, arr1[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContainedIn(String[] arr, String str) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i].equals(str))
				return true;
		return false;
	}

	public static int leastCommonDenominator(int num1, int num2) {
		for (int i = num1; i < num1 * num2; i += num1) {
			if (i % num2 == 0) {
				return i;
			}
		}
		return num1 * num2;
	}

	public static int greatestCommonFactor(int num1, int num2) {
		if (num2 < num1) {
			int temp = num1;
			num1 = num2;
			num2 = temp;
		}
		int[] temp = reverse(listFactors(num1));
		for (int i : temp) {
			if (num2 % i == 0) {
				return i;
			}
		}
		return 1;
	}

	public static int[] listFactors(int num) {
		int[] factors = new int[num / 2];
		for (int i = 1; i < num / 2; i++) {
			if (num % i == 0) {
				for (int j = 0; j < factors.length; j++) {
					if (factors[j] != 0) {
						factors[j] = i;
						break;
					}
				}
			}
		}
		return trimZeroes(factors);
	}

	public static int[] trimZeroes(int[] arr) {
		int count = 0;
		for (int i : arr) {
			if (i != 0) {
				count++;
			}
		}
		int[] rArr = new int[count];
		for (int i = 0; i < rArr.length; i++) {
			rArr[i] = arr[i];
		}
		return rArr;
	}

	public static double cartDist(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}