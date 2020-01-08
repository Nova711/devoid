package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import rift.Out;

public class StandardThruster extends StandardDObject implements Thruster {

	protected double hp = 5;
	protected double mass = 50;
	protected double throttle = 100;
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
		super(position, new Vector(0, 0), 5, angle, 50, 1, 0, 0);
		this.maxThrust = maxThrust;
		this.specificImpulse = specificImpulse;
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
		ng.fillRect(-8, -4, 20, 8);
		int[] xPoints = { -8, -8, -12, -12 };
		int[] yPoints = { -4, 4, 6, -6 };
		ng.fillPolygon(new Polygon(xPoints, yPoints, xPoints.length));
		ng.rotate(-this.getAngle());
		ng.setColor(Color.red);
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		if (this.isThrusting()) {
			temp = new Vector(this.getAngle(), this.maxThrust * this.throttle / 100);
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
			return new Vector(this.getAngle(), this.maxThrust * this.throttle / 100);
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
