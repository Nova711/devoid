package devoid_boosted;

import java.awt.*;

public class StandardGUI implements GUI {

	public int width = 100;

	public StandardGUI() {
	}

	@Override
	public void draw(Ship s, Rectangle bounds, Graphics g) {
		g.setColor(new Color(Color.cyan.getRed(), Color.cyan.getGreen(), Color.cyan.getBlue(), 128));
		g.fillRect(0, 0, this.width, bounds.height);
		g.fillRect(bounds.width - this.width, this.width, this.width, bounds.height - this.width);
		g.fillRect(this.width, 0, bounds.width - this.width, this.width);
		g.fillRect(this.width, bounds.height - this.width, bounds.width - this.width, this.width);
	}

}
