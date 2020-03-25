package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import in_out.Out;

/**
 * StandardDObject--Provides standard behavior for physics objects
 * 
 * @author Carter Rye
 */
public class StandardDObject implements DObject {
	/**
	 * position--The position of this object relative to the origin
	 */
	private Vector position = new Vector(0, 0);
	/**
	 * velocity--The velocity of this object
	 */
	private Vector velocity = new Vector(0, 0);

	/**
	 * hp--The hit points of this object
	 */
	private double hp = 1;
	/**
	 * angle--The angle of this object in radians
	 */
	private double angle;
	/**
	 * mass--The mass of this object
	 */
	private double mass;
	/**
	 * angularVelocity--The angular velocity of this object in radians per interval
	 */
	private double angularVelocity;
	/**
	 * temperature--The temperature of this object
	 */
	private double temperature;
	/**
	 * environment--The environment this object is contained in
	 */
	private PhysicsBox environment;

	private ArrayList<CustomPolygon> bounds = new ArrayList<CustomPolygon>();

	/**
	 * Generates an empty StandardDObject
	 */
	public StandardDObject() {

	}

	/**
	 * Generates a StandardDObject with the properties specified
	 * 
	 * @param position        The position of this object
	 * @param velocity        The velocity of this object
	 * @param hp              The hit points of this object
	 * @param angle           The angle of this object
	 * @param mass            The mass of this object
	 * @param elasticity      The elasticity of this object
	 * @param angularVelocity The angular velocity of this object in radians per
	 *                        interval
	 * @param temperature     The temperature of this object
	 */
	public StandardDObject(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature) {
		this.position = position;
		this.velocity = velocity;
		this.hp = hp;
		this.angle = angle;
		this.mass = mass;
		// this.elasticity = elasticity;
		this.angularVelocity = angularVelocity;
		this.temperature = temperature;
	}

	public double getHP() {
		return this.hp;
	}

	public void setHP(double hp) {
		this.hp = hp;
	}

	public Vector getPosition() {
		return this.position;
	}

	public double getX() {
		return this.position.getX();
	}

	public double getY() {
		return this.position.getY();
	}

	public double getAngle() {
		return this.angle;
	}

	public double getMass() {
		return this.mass;
	}

	public double getI() {
		if (this.getPosition().getMagnitude() != 0)
			return this.getMass() * Math.pow(this.getPosition().getMagnitude(), 2);
		else {
			Rectangle rect = this.getBoundingPolygon().getBounds();
			return (this.getMass() * (Math.pow(rect.width, 2) + Math.pow(rect.getHeight(), 2)) / 12);
		}
	}

	public void setX(double x) {
		// TODO Auto-generated method stub
	}

	public void setY(double y) {
		// TODO Auto-generated method stub
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Vector getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void applyForce(Vector force, Vector position) {
		Vector acceleration = new Vector(force.getAngle() + this.getAngle(), force.getMagnitude() / this.getMass());
		this.setVelocity(this.getVelocity().add(acceleration));
		if (acceleration.getMagnitude() != 0)
			this.setAngularVelocity(this.getAngularVelocity() + this.getAngularAcceleration(force, position));
	}

	public void applyTorque(double torque) {
		double angularAcceleration = torque / this.getI();
		this.setAngularVelocity(this.getAngularVelocity() + angularAcceleration);
	}

	/**
	 * Calculates the angular acceleration experienced by this object
	 * 
	 * @param force    The force applied to this object
	 * @param position The position of the applied force
	 */
	public double getAngularAcceleration(Vector force, Vector position) {
		double angularAcceleration = position.getMagnitude() * force.getMagnitude()
				* Math.sin(force.getAngle() - position.getAngle()) / this.getI();
		return angularAcceleration;
	}

	public double getAngularVelocity() {
		return this.angularVelocity;
	}

	public void setAngularVelocity(double velocity) {
		this.angularVelocity = velocity;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public void update() {
		if (this.getEnvironment() != null) {/*
											 * for (DObject obj : this.getEnvironment().objects) { if
											 * (!obj.equals(this)) { Vector force =
											 * Util.calculateGravity(this.getMass(), obj.getMass(), this.getPosition(),
											 * obj.getPosition()); obj.applyForce(force, Vector.zero); } }
											 */
		}
		this.setPosition(this.getPosition()
				.add(this.getVelocity().scalarMultiply(1 / (double) this.getEnvironment().getTickrate() * 4)));
		this.setAngle(this.getAngle() + this.getAngularVelocity() / (double) this.getEnvironment().getTickrate());
	}

	public void impact(DObject obj, CollisionEvent e) {
		Vector tempVelocity = new Vector(this.getVelocity().getMagnitude(),
				this.getVelocity().getAngle() - e.getAngle());
		Vector objTempVelocity = new Vector(obj.getVelocity().getMagnitude(),
				obj.getVelocity().getAngle() - e.getAngle());
		double y = (this.getMass() - obj.getMass()) / (this.getMass() + obj.getMass()) * tempVelocity.getY()
				+ 2 * obj.getMass() / (this.getMass() + obj.getMass()) * objTempVelocity.getY();
		Vector newVelocity = Vector.fromXY(tempVelocity.getX(), y);
		Vector force = newVelocity.add(new Vector(tempVelocity.getAngle(), -tempVelocity.getMagnitude()));
		this.applyForce(force, e.getPosition());
	}

	public boolean hits(DObject objects) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkForDestruction() {
		return this.hp <= 0;
	}

	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		for (CustomPolygon p : this.getBounds()) {
			p.draw(ng);
		}
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	public PhysicsBox getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(PhysicsBox b) {
		this.environment = b;
	}

	@Override
	public void setPosition(Vector position) {
		this.position = position;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;
	}

	public ArrayList<CustomPolygon> getBounds() {
		return bounds;
	}

	public void setBounds(ArrayList<CustomPolygon> bounds) {
		this.bounds = bounds;
	}

	public void setBounds(CustomPolygon... bounds) {
		this.bounds = new ArrayList<CustomPolygon>();
		for (CustomPolygon p : bounds) {
			this.bounds.add(p);
		}
	}

	public Polygon getBoundingPolygon() {
		Area a = new Area();
		for (CustomPolygon p : this.getBounds())
			a.add(new Area(p.getBounds()));
		return CustomPolygon.convertFromArea(a);
	}

}
