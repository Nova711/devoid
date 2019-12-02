package devoid_boosted;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.*;

import in_out.Out;
import rift.Ex;

public class HitBox {
	private ArrayList<Rectangle> bounds = new ArrayList<>();
	protected double x;
	protected double y;
	protected double angle = 0;

	@SuppressWarnings("unchecked")
	public HitBox(HitBox h) {
		this(h.x, h.y, (ArrayList<Rectangle>) h.bounds.clone());
	}

	public HitBox(double x, double y, Rectangle... bounds) {
		this.x = x;
		this.y = y;
		for (Rectangle box : bounds) {
			this.getBounds().add(box);
		}
	}

	public HitBox(double x, double y, ArrayList<Rectangle> bounds) {
		this.x = x;
		this.y = y;
		this.bounds = bounds;
	}

	public boolean hits(HitBox target) {
		Area area = new Area();
		Area targetArea = new Area();
		for (Rectangle box : this.getBounds()) {
			box = new Rectangle(box);
			box.translate((int) this.x, (int) this.y);
			area.add(new Area(box));
		}
		for (Rectangle targetBox : target.getBounds()) {
			targetBox = new Rectangle(targetBox);
			targetBox.translate((int) target.getX(), (int) target.getY());
			targetArea.add(new Area(targetBox));
		}
		if (area.isRectangular() & targetArea.isRectangular()) {
			return area.getBounds2D().intersects(targetArea.getBounds2D());
		}
		area.intersect(targetArea);
		return !area.isEmpty();
	}

	public void draw(Graphics g) {
		for (Rectangle box : this.getBounds()) {
			box = new Rectangle(box);
			box.translate((int) this.x, (int) this.y);
			g.fillRect(box.x, box.y, box.width, box.height);
		}
	}
	
	public void add(Rectangle r) {
		this.bounds.add(r);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return this.angle;
	}

	public double getCenterX() {
		double center = 0;
		for (Rectangle box : this.getBounds()) {
			center += box.getCenterX();
		}
		return center / this.bounds.size();
	}

	public double getCenterY() {
		double center = 0;
		for (Rectangle box : this.getBounds()) {
			center += box.getCenterY();
		}
		return center / this.bounds.size();
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public ArrayList<Rectangle> getBounds() {
		return bounds;
	}

	public void setBounds(ArrayList<Rectangle> bounds) {
		this.bounds = bounds;
	}
}
