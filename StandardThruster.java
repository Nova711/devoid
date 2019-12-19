package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class StandardThruster implements Thruster {
	protected Vector position = new Vector(0, 0);
	protected Vector velocity = new Vector(0, 0);

	protected double hp = 5;
	protected double angle = 0;
	protected double mass = 50;
	protected double elasticity = 1;
	protected double throttle = 100;
	protected double angularVelocity = 0;
	protected double temperature = 0;
	protected double maxThrust = 0;
	protected double specificImpulse = 0;

	protected boolean isThrusting;

	public StandardThruster(Vector position, double angle) {
		this(position, angle, 500);
	}

	public StandardThruster(Vector position, double angle, double maxThrust) {
		this(position, angle, maxThrust, 100);
	}

	public StandardThruster(Vector position, double angle, double maxThrust, double specificImpulse) {
		this.position = position;
		this.angle = angle;
		this.maxThrust = maxThrust;
		this.specificImpulse = specificImpulse;
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
		g.drawLine(0, 0, (int) (this.getX()), (int) (this.getY()));
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		ng.drawRect(-8, -4, 20, 8);
		int[] xPoints = { -8, -8, -12, -12 };
		int[] yPoints = { -4, 4, 6, -6 };
		ng.drawPolygon(new Polygon(xPoints, yPoints, xPoints.length));
		ng.rotate(-this.getAngle());
		ng.setColor(Color.red);
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		if (this.isThrusting()) {
			temp = new Vector(this.angle, this.maxThrust * this.throttle / 100);
			g.drawLine(0, 0, (int) (temp.getX()), (int) (temp.getY()));
		}
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public void activate() {
		this.isThrusting = true;
	}

	@Override
	public void deactivate() {
		this.isThrusting = false;
	}

	@Override
	public boolean isThrusting() {
		return this.isThrusting;
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
