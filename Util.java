package devoid_boosted;

import java.awt.Graphics;
import java.io.File;

import in_out.Out;

public class Util {
	public static File src = new File("devoid_boosted//res//ships").getAbsoluteFile();

	public static double parseNextDouble(String s) {
		return Double.parseDouble(s.substring(s.indexOf(" ")));
	}

	public static int[] parseIntArray(String values) {
		String[] arr = values.split(",");
		int[] retValues = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			retValues[i] = Integer.parseInt(arr[i].trim());
		}
		return retValues;
	}

	public static int[] mirror(int[] arr) {
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = arr[i] * -1;
		}
		return ret;
	}

	public static void drawText(String text, int x, int y, Graphics g) {
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
	}
}
