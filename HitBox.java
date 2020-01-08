package devoid_boosted;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.*;

import in_out.Out;
import rift.Ex;

public class HitBox {
	private Polygon bounds;
	protected double x;
	protected double y;

	public HitBox(HitBox h) {
		this(h.x, h.y, new Polygon(h.getBounds().xpoints, h.getBounds().ypoints, h.getBounds().npoints));
	}

	public HitBox(double x, double y, Polygon bounds) {
		this.x = x;
		this.y = y;
		this.bounds = bounds;
	}

	public CollisionEvent hits(HitBox target, AffineTransform tx, AffineTransform ttx) {
		CollisionEvent collision = new CollisionEvent();
		Polygon box = new Polygon(this.bounds.xpoints, this.bounds.ypoints, this.bounds.npoints);
		box = (Polygon) tx.createTransformedShape(box);
		Polygon targetBox = new Polygon(target.getBounds().xpoints, target.getBounds().ypoints,
				target.getBounds().npoints);
		targetBox = (Polygon) ttx.createTransformedShape(targetBox);
		if (box.getBounds().intersects(targetBox.getBounds())) {
			boolean isSurface = false;
			int x = 0;
			int y = 0;
			int index = -1;
			for (int i = 0; i < box.npoints; i++) {
				if (targetBox.contains(box.xpoints[i], box.ypoints[i])) {
					isSurface = true;
					x = box.xpoints[i];
					y = box.ypoints[i];
				}
			}
			if (!isSurface) {
				for (int i = 0; i < targetBox.npoints; i++) {
					if (box.contains(targetBox.xpoints[i], targetBox.ypoints[i])) {
						isSurface = true;
						x = targetBox.xpoints[i];
						y = targetBox.ypoints[i];
					}
				}
				if (isSurface) {
					double slope = Vector.fromXY(box.xpoints[0] - box.xpoints[1], box.ypoints[0] - box.ypoints[1])
							.getAngle() - Vector.fromXY(box.xpoints[0] - x, box.ypoints[0] - y).getAngle();
					for (int i = 0; i < box.npoints; i++) {
						double tempSlope = Vector
								.fromXY(box.xpoints[i] - box.xpoints[(i + 1) % box.npoints],
										box.ypoints[i] - box.ypoints[(i + 1) % box.npoints])
								.getAngle() - Vector.fromXY(box.xpoints[i] - x, box.ypoints[i] - y).getAngle();
						if (Math.abs(tempSlope) < Math.abs(slope)) {
							slope = tempSlope;
							index = i;
						}
					}
					double angle = Vector.fromXY(box.xpoints[index] - box.xpoints[(index + 1) % box.npoints],
							box.ypoints[index] - box.ypoints[(index + 1) % box.npoints]).getAngle();
					collision = new CollisionEvent(Vector.fromXY(x, y), angle);
				}
			} else {
				double slope = Vector
						.fromXY(targetBox.xpoints[0] - targetBox.xpoints[1],
								targetBox.ypoints[0] - targetBox.ypoints[1])
						.getAngle() - Vector.fromXY(targetBox.xpoints[0] - x, targetBox.ypoints[0] - y).getAngle();
				for (int i = 0; i < targetBox.npoints; i++) {
					double tempSlope = Vector
							.fromXY(targetBox.xpoints[i] - targetBox.xpoints[(i + 1) % targetBox.npoints],
									targetBox.ypoints[i] - targetBox.ypoints[(i + 1) % targetBox.npoints])
							.getAngle() - Vector.fromXY(targetBox.xpoints[i] - x, targetBox.ypoints[i] - y).getAngle();
					if (Math.abs(tempSlope) < Math.abs(slope)) {
						slope = tempSlope;
						index = i;
					}
				}
				double angle = Vector
						.fromXY(targetBox.xpoints[index] - targetBox.xpoints[(index + 1) % targetBox.npoints],
								targetBox.ypoints[index] - targetBox.ypoints[(index + 1) % targetBox.npoints])
						.getAngle();
				collision = new CollisionEvent(Vector.fromXY(x, y), angle);

			}
		}
		return collision;
	}

	public void draw(Graphics g) {
		Polygon box = new Polygon(this.bounds.xpoints, this.bounds.ypoints, this.bounds.npoints);
		box.translate((int) this.x, (int) this.y);
		g.fillPolygon(box);
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

	public Polygon getBounds() {
		return this.bounds;
	}

	public void setBounds(Polygon bounds) {
		this.bounds = bounds;
	}
}
