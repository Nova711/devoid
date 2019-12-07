package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardThruster implements Thruster {
	protected Vector position = new Vector(0, 0);
	protected Vector velocity = new Vector(0, 0);

	protected double hp = 5;
	protected double angle = 0;
	protected double mass = 5;
	protected double elasticity = 1;
	protected double throttle = 100;
	protected double angularVelocity = 0;
	protected double temperature = 0;
	protected double maxThrust = 100;
	protected double specificImpulse = 100;

	public StandardThruster(Vector position, double angle) {
		this.position = position;
		this.angle = angle;
	}

	@Override
	public double getHP() {
		return this.hp;
	}

	@Override
	public Vector getPosition() {
		return this.position;
	}

	@Override
	public double getX() {
		return this.position.getX();
	}

	@Override
	public double getY() {
		return this.position.getY();
	}

	@Override
	public double getAngle() {
		return this.angle;
	}

	@Override
	public double getMass() {
		return this.mass;
	}

	@Override
	public double getI() {
		return this.mass * Math.pow(this.position.getMagnitude(), 2);
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public Vector getVelocity() {
		return this.velocity;
	}

	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	@Override
	public double getAngularVelocity() {
		return this.angularVelocity;
	}

	@Override
	public void setAngularVelocity(double velocity) {
		this.angularVelocity = velocity;
	}

	@Override
	public double getTemperature() {
		return this.temperature;
	}

	@Override
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void impact(DObject obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean hits(DObject[] objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(Color.black);
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		ng.drawRect(-3, -1, 6, 2);
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public Vector thrust(FuelTank fuel) {
		double fuelDrain = this.maxThrust * this.throttle / 100 / this.specificImpulse;
		if (fuelDrain <= fuel.getFuel()) {
			fuel.drain(fuelDrain);
			return new Vector(this.angle, this.maxThrust * this.throttle / 100);
		}
		return new Vector(0, 0);
	}

	@Override
	public double getMaxThrust() {
		return this.maxThrust;
	}

	@Override
	public double getThrottle() {
		return this.throttle;
	}

	@Override
	public void setThrottle(double throttle) {
		this.throttle = throttle >= 0 ? throttle <= 100 ? throttle : 100 : 0;
	}

	@Override
	public double getSpecificImpulse() {
		return this.specificImpulse;
	}

}
