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
		Util.drawText("" + s.getThrottle(), bounds.width - 1 * this.width, bounds.height - 1 * this.width, g);
		Util.drawText("" + f.format(s.getFuel()) + "/" + f.format(s.getMaxFuel()), bounds.width - 1 * this.width,
				bounds.height - 1 * this.width + 10, g);
		Util.drawText(s.getVelocity().toString(), 0, 10, g);
		Util.drawText("" + s.getAngularVelocity(), 0, 20, g);
		Util.drawText("" + s.getMass(), 0, 30, g);
		Util.drawText("" + s.getPosition(), 0, 40, g);
		Util.drawText("" + s.getAngle(), 0, 50, g);
	}

}
