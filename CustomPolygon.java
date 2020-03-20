package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

public class CustomPolygon {
	private Polygon bounds;
	private ArrayList<Polygon> holes = new ArrayList<Polygon>();
	private Color color;

	public CustomPolygon(Polygon p, Color color) {
		this.bounds = p;
		this.color = color;
	}

	public CustomPolygon(int[] xPoints, int[] yPoints, Color color) {
		this(new Polygon(xPoints, yPoints, xPoints.length), color);
	}

	public CustomPolygon(int[] xPoints, int[] yPoints) {
		this(xPoints, yPoints, Color.gray);
	}

	public CustomPolygon() {
	}

	public CustomPolygon merge(CustomPolygon p) {
		Area merge = new Area(this.getAsArea());
		merge.add(p.getAsArea());
		return new CustomPolygon(convertFromArea(merge), this.getColor());
	}

	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(this.getColor());
		ng.fill(this.getAsArea());
	}

	public Polygon getBounds() {
		return this.bounds;
	}

	public void setBounds(Polygon bounds) {
		this.bounds = bounds;
	}

	public ArrayList<Polygon> getHoles() {
		return holes;
	}

	public void setHoles(ArrayList<Polygon> holes) {
		this.holes = holes;
	}

	public void addHole(Polygon hole) {
		this.holes.add(hole);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Area getAsArea() {
		Area a = new Area(this.getBounds());
		for (Polygon polygon : this.getHoles())
			a.subtract(new Area(polygon));
		return a;
	}

	public static Polygon convertFromArea(Area a) {
		PathIterator iterator = a.getPathIterator(null);
		float[] floats = new float[6];
		Polygon polygon = new Polygon();
		while (!iterator.isDone()) {
			int type = iterator.currentSegment(floats);
			int x = (int) floats[0];
			int y = (int) floats[1];
			if (type != PathIterator.SEG_CLOSE) {
				polygon.addPoint(x, y);
			}
			iterator.next();
		}
		return polygon;
	}

}
