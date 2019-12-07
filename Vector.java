package devoid_boosted;

import in_out.Out;

public class Vector {
	protected double angle;
	protected double magnitude;

	public Vector(Vector v) {
		this(v.angle, v.magnitude);
	}

	public Vector(double angle, double magnitude) {
		this.angle = angle;
		this.magnitude = magnitude;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public double getX() {
		return Math.cos(this.angle) * this.magnitude;
	}

	public double getY() {
		return Math.sin(this.angle) * this.magnitude;
	}

	public Vector add(Vector vector) {
		double x = this.getX() + vector.getX();
		double y = this.getY() + vector.getY();
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	public static double calculateDirection(double x, double y) {
		return x < 0 ? Math.atan(y / x) + Math.PI : Math.atan(y / x);
	}

	public static double calculateMagnitude(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public static Vector fromXY(double x, double y) {
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	public String toString() {
		return "" + Math.toDegrees(this.angle) + " " + this.magnitude;
	}

}
