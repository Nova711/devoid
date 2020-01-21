package devoid_boosted;

/**
 * Vector --- An object designed to store 2d numbers using the direction and
 * magnitude format
 * 
 * @author Carter Rye
 */
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
	 * Returns the direction of a vector in standard position with its tip at x,y
	 * 
	 * @param x the x coordinate of the tip
	 * @param y the y coordinate of the tip
	 * @return The direction of the vector
	 */
	public static double calculateDirection(double x, double y) {
		return x < 0 ? Math.atan(y / x) + Math.PI : x == 0 ? 0 : Math.atan(y / x);
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

}
