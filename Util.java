package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import in_out.Out;

public class Util {
	public static File src = new File("devoid_boosted//res").getAbsoluteFile();
	public static double G = 6.67 * Math.pow(10, -11);

	public static double parseNextDouble(String s) {
		return Util.parseNextDouble(s, " ");
	}

	public static double parseNextDouble(String s, String delimiter) {
		return Double.parseDouble(s.substring(s.indexOf(delimiter)));
	}

	public static int[] parseIntArray(String values) {
		String[] arr = values.split(",");
		int[] retValues = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			retValues[i] = Integer.parseInt(arr[i].trim());
		}
		return retValues;
	}

	public static double[] parseDoubleArray(String values) {
		String[] arr = values.split(",");
		double[] retValues = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			retValues[i] = Double.parseDouble(arr[i].trim());
		}
		return retValues;
	}

	public static Color parseColor(String color) {
		return Util.parseColor(Util.parseIntArray(color));
	}

	public static Color parseColor(int[] rgb) {
		if (rgb.length == 3)
			return new Color(rgb[0], rgb[1], rgb[2]);
		else
			return new Color(rgb[0], rgb[1], rgb[2], rgb[3]);
	}

	public static int[] mirror(int[] arr) {
		if (arr == null)
			return arr;
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = arr[i] * -1;
		}
		return ret;
	}

	public static void drawText(String text, int x, int y, Graphics g) {
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
	}

	public static void drawCenteredText(String text, int x, int y, Graphics g) {
		Util.drawText(text, x - g.getFontMetrics().stringWidth(text) / 2, (int) (y + g.getFont()
				.createGlyphVector(g.getFontMetrics().getFontRenderContext(), text).getVisualBounds().getHeight()/2), g);
	}

	public static void drawLine(int x, int y, Vector v, double scale, Graphics g) {
		g.drawLine(x, y, (int) (v.getX() * scale) + x, (int) (v.getY() * scale) + y);
	}

	/**
	 * pull will be toward mass 1
	 * 
	 * @param mass1
	 * @param mass2
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	public static Vector calculateGravity(double mass1, double mass2, Vector pos1, Vector pos2) {
		Vector displacement = pos1.subtract(pos2);
		if (displacement.getMagnitude() == 0.0) {
			return Vector.zero;
		}
		Vector pull = new Vector(displacement.getAngle(),
				Util.G * mass1 * mass2 / Math.pow(displacement.getMagnitude(), 2));
		return pull;
	}

	public static Vector calculateAirResistance(double airDensity, double coefficientOfDrag, double area,
			Vector velocity) {
		return new Vector(velocity.getAngle() + Math.PI,
				airDensity * coefficientOfDrag * area / 2 * Math.pow(velocity.getMagnitude(), 2));
	}

	public static void timeout(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int round(double d) {
		return (int) (d > 0 ? d + 0.5 : d - 0.5);
	}
}
