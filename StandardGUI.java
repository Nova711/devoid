package devoid_boosted;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StandardGUI implements GUI {

	public int width = 100;

	public StandardGUI() {
	}

	@Override
	public void draw(Ship s, Rectangle bounds, Graphics g) {
		g.setColor(Color.gray);
		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();
		int[] xPoints = { width / 4, 3 * width / 4, 2 * width / 3, 5 * width / 8, 3 * width / 8, width / 3 };
		int[] yPoints = { height, height, 7 * height / 8, 3 * height / 4, 3 * height / 4, 7 * height / 8 };
		Polygon centerConsole = new Polygon(xPoints, yPoints, xPoints.length);
		int[] xPoints1 = { 0, width / 8, width / 12, 0 };
		int[] yPoints1 = { height, height, 7 * height / 8, 13 * height / 16 };
		Polygon leftDash = new Polygon(xPoints1, yPoints1, xPoints1.length);
		int[] xPoints2 = { 7 * width / 8, width, width, 11 * width / 12 };
		int[] yPoints2 = { height, height, 13 * height / 16, 7 * height / 8 };
		Polygon rightDash = new Polygon(xPoints2, yPoints2, xPoints2.length);
		g.fillPolygon(centerConsole);
		g.fillPolygon(leftDash);
		g.fillPolygon(rightDash);
		g.setColor(Color.darkGray);
		g.fillRect(3 * width / 8, 3 * height / 4, width / 4, height / 4);
		g.fillRect(width / 3, 7 * height / 8, width / 3, height / 8);
		g.fillRect(0, 7 * height / 8, width / 12, height / 8);
		g.fillRect(11 * width / 12, 7 * height / 8, width / 12, height / 8);
		g.setColor(Color.green.darker());
		g.fillRect(11 * width / 24 + 2, 3 * height / 4 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(3 * width / 8 + 2, 3 * height / 4 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(13 * width / 24 + 2, 3 * height / 4 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(2, 7 * height / 8 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(2, 15 * height / 16 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(7 * width / 12 + 2, 7 * height / 8 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(7 * width / 12 + 2, 15 * height / 16 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(11 * width / 12 + 2, 7 * height / 8 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(11 * width / 12 + 2, 15 * height / 16 + 2, width / 12 - 4, height / 16 - 4);
		g.fillRect(width / 3 + 2, 7 * height / 8 + 2, width / 12 - 4, height / 8 - 4);
		g.fillRect(5 * width / 12 + 2, 13 * height / 16 + 2, width / 6 - 4, 3 * height / 16 - 4);
		g.setColor(Color.green);
		g.fillRect(11 * width / 24 + 4, 3 * height / 4 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(3 * width / 8 + 4, 3 * height / 4 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(13 * width / 24 + 4, 3 * height / 4 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(4, 7 * height / 8 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(4, 15 * height / 16 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(7 * width / 12 + 4, 7 * height / 8 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(7 * width / 12 + 4, 15 * height / 16 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(11 * width / 12 + 4, 7 * height / 8 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(11 * width / 12 + 4, 15 * height / 16 + 4, width / 12 - 8, height / 16 - 8);
		g.fillRect(width / 3 + 4, 7 * height / 8 + 4, width / 12 - 8, height / 8 - 8);
		g.fillRect(5 * width / 12 + 4, 13 * height / 16 + 4, width / 6 - 8, 3 * height / 16 - 8);
		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);
		g.setColor(Color.green.darker());
		Util.drawCenteredText("CBT", width / 2, 25 * height / 32, g);
		Util.drawCenteredText((int) s.getTemperature() + "K", 5 * width / 12, 25 * height / 32, g);
		Util.drawCenteredText((int) Math.toDegrees(s.getAngularVelocity()) + "°/s", 7 * width / 12, 25 * height / 32,
				g);
		Util.drawCenteredText((int) s.getPosition().getMagnitude() + "m", 5 * width / 8, 29 * height / 32, g);
		Util.drawCenteredText((int) s.getVelocity().getMagnitude() + "m/s", 5 * width / 8, 31 * height / 32, g);
		Util.drawCenteredText((int) s.getFuel() + "kg", width / 24, 29 * height / 32, g);
		Util.drawCenteredText((int) s.getMass() + "kg", width / 24, 31 * height / 32, g);
		Util.drawCenteredText((int) s.getThrottle() + "%", 23 * width / 24, 29 * height / 32, g);
		double angle = s.getPosition().rotate(Math.PI / 2 - s.getAngle()).getAngle() % (2 * Math.PI);
		if (angle < 0)
			for (; angle < 0; angle += 2 * Math.PI)
				;
		double[] sector = new double[4];
		sector[0] = Math.atan((double) (height / 16) / (width / 24));
		sector[1] = Math.PI - Math.atan((double) (height / 16) / (width / 24));
		sector[2] = Math.PI - Math.atan(-(double) (height / 16) / (width / 24));
		sector[3] = Math.atan(-(double) (height / 16) / (width / 24));
		for (int i = 0; i < sector.length; i++) {
			if (sector[i] < 0)
				sector[i] += 2 * Math.PI;
		}
		if (angle > sector[0] & angle < sector[1]) {
			g.fillRect(3 * width / 8 + (int) (Math.cos(angle) * ((double) width / 24)) - 2, height - 8, 4, 4);
		} else if (angle > sector[1] & angle < sector[2]) {
			g.fillRect(width / 3 + 4, 15 * height / 16 + (int) (Math.sin(angle) * ((double) height / 16 + 12)) - 2, 4,
					4);
		} else if (angle > sector[2] & angle < sector[3]) {
			g.fillRect(3 * width / 8 + (int) (Math.cos(angle) * ((double) width / 24)) - 2, 7 * height / 8 + 4, 4, 4);
		} else {
			g.fillRect(5 * width / 12 - 8, 15 * height / 16 + (int) (Math.sin(angle) * ((double) height / 16 + 12)) - 2,
					4, 4);
		}
		g.setColor(Color.red.darker());
		angle = s.getVelocity().rotate(Math.PI / 2 - s.getAngle()).rotate(Math.PI).getAngle() % (2 * Math.PI);
		for (int i = 0; i < sector.length; i++) {
			if (sector[i] < 0)
				sector[i] += 2 * Math.PI;
		}
		if (angle > sector[0] & angle < sector[1]) {
			g.fillRect(3 * width / 8 + (int) (Math.cos(angle) * ((double) width / 24)) - 2, height - 8, 4, 4);
		} else if (angle > sector[1] & angle < sector[2]) {
			g.fillRect(width / 3 + 4, 15 * height / 16 + (int) (Math.sin(angle) * ((double) height / 16 + 12)) - 2, 4,
					4);
		} else if (angle > sector[2] & angle < sector[3]) {
			g.fillRect(3 * width / 8 + (int) (Math.cos(angle) * ((double) width / 24)) - 2, 7 * height / 8 + 4, 4, 4);
		} else {
			g.fillRect(5 * width / 12 - 8, 15 * height / 16 + (int) (Math.sin(angle) * ((double) height / 16 + 12)) - 2,
					4, 4);
		}
	}

	public void drawOld(Ship s, Rectangle bounds, Graphics g) {
		g.setColor(new Color(Color.cyan.getRed(), Color.cyan.getGreen(), Color.cyan.getBlue(), 128));
		// g.fillRect(0, 0, this.width, bounds.height);
		// g.fillRect(bounds.width - this.width, this.width, this.width, bounds.height -
		// this.width);
		// g.fillRect(this.width, 0, bounds.width - this.width, this.width);
		// g.fillRect(this.width, bounds.height - this.width, bounds.width - 2 *
		// this.width, this.width);
		g.fillOval(bounds.width - 2 * this.width, bounds.height - 2 * this.width, 4 * this.width, 4 * this.width);
		g.setColor(new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 128));
		g.fillArc(bounds.width - 2 * this.width, bounds.height - 2 * this.width, 4 * this.width, 4 * this.width, 90,
				(int) (90 * (s.getThrottle() / 100)));
		g.fillArc((int) (bounds.width - 1.7 * this.width), (int) (bounds.height - 1.7 * this.width),
				(int) (3.4 * this.width), (int) (3.4 * this.width), 180, -(int) (90 * (s.getFuel() / s.getMaxFuel())));
		g.setColor(Color.black);
		NumberFormat f = new DecimalFormat("#0.0");
		Font font = new Font("Arial", Font.PLAIN, 12);
		g.setFont(font);
		Util.drawText("" + s.getThrottle(), bounds.width - 1 * this.width, bounds.height - 1 * this.width, g);
		Util.drawText("" + f.format(s.getFuel()) + "/" + f.format(s.getMaxFuel()), bounds.width - 1 * this.width,
				bounds.height - 1 * this.width + 10, g);
		// Util.drawText(f.format(s.getVelocity().getMagnitude()), 0, 10, g);
		/// Util.drawText(f.format(s.getAngularVelocity()), 0, 20, g);
		// Util.drawText(f.format(s.getMass()), 0, 30, g);
		// Util.drawText("x" + s.getX() + " y"+s.getY(), 0, 40, g);
		// Util.drawText(f.format(s.getAngle()), 0, 50, g);
	}

}
