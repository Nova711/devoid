package devoid_boosted;

import in_out.Out;

/**
 * Vector --- An object designed to store 2d numbers using the direction and
 * magnitude format
 * 
 * @author Carter Rye
 */
public class Vector {
	protected double angle;
	protected double magnitude;
	public static final Vector zero = new Vector(0, 0);

	/**
	 * Generates a copy of the vector v
	 * 
	 * @param v The vector to be copied
	 */
	public Vector(Vector v) {
		this(v.angle, v.magnitude);
	}

	/**
	 * Generates a a vector with the specified angle and magnitude
	 * 
	 * @param angle     The angle of this vector
	 * @param magnitude The magnitude of this vector
	 */
	public Vector(double angle, double magnitude) {
		this.angle = angle;
		this.magnitude = magnitude;
	}

	/**
	 * Gets the angle of this vector
	 * 
	 * @return The angle of this vector
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Sets the angle of this vector
	 * 
	 * @param angle The new angle of this vector
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Gets the magnitude of this vector
	 * 
	 * @return The magnitude of this vector
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Sets the magnitude of this vector
	 * 
	 * @param magnitude The new magnitude of this vector
	 */
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	/**
	 * Gets the x component of this vector
	 * 
	 * @return The x component of this vector
	 */
	public double getX() {
		return Math.cos(this.angle) * this.magnitude;
	}

	/**
	 * Gets the y component of this vector
	 * 
	 * @return The y component of this vector
	 */
	public double getY() {
		return Math.sin(this.angle) * this.magnitude;
	}

	/**
	 * Returns the vector sum of this vector and the vector passed as a parameter
	 * 
	 * @param vector The {@link Vector} to be added to this one
	 * @return The vector sum
	 */
	public Vector add(Vector vector) {
		double x = this.getX() + vector.getX();
		double y = this.getY() + vector.getY();
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	/**
	 * Returns the vector sum of this vector and the negative of the vector passed
	 * as a parameter
	 * 
	 * @param vector The {@link Vector} to be subtracted from this one
	 * @return The vector subtraction
	 */
	public Vector subtract(Vector vector) {
		double x = this.getX() - vector.getX();
		double y = this.getY() - vector.getY();
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	public Vector scalarMultiply(double k) {
		double x = this.getX() * k;
		double y = this.getY() * k;
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	public Vector rotate(Double angle) {
		return new Vector(this.getAngle() + angle, this.getMagnitude());
	}

	/**
	 * Returns the direction of a vector in standard position with its tip at x,y
	 * 
	 * @param x the x coordinate of the tip
	 * @param y the y coordinate of the tip
	 * @return The direction of the vector
	 */
	public static double calculateDirection(double x, double y) {
		return x < 0 ? Math.atan(y / x) + Math.PI : x == 0 ? y > 0 ? Math.PI / 2 : -Math.PI / 2 : Math.atan(y / x);
	}

	/**
	 * Returns the magnitude of a vector in standard position with its tip at x,y
	 * 
	 * @param x the x coordinate of the tip
	 * @param y the y coordinate of the tip
	 * @return The magnitude of the vector
	 */
	public static double calculateMagnitude(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Returns a vector in standard position with its tip at x, y
	 * 
	 * @param x the x coordinate of the tip
	 * @param y the y coordinate of the tip
	 * @return A vector
	 */
	public static Vector fromXY(double x, double y) {
		return new Vector(Vector.calculateDirection(x, y), Vector.calculateMagnitude(x, y));
	}

	public String toString() {
		return "" + this.angle + " " + this.magnitude;
	}

	public static void main(String[] args) {
		Vector test = Vector.fromXY(0, -5);
		Out.println("x" + test.getX() + " y" + test.getY());
	}

}
