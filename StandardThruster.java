package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import rift.Out;

public class StandardThruster extends StandardShipComponent implements Thruster {

	protected double hp = 5;
	protected double mass = 50;
	protected double throttle = 100;
	protected double maxThrust = 0;
	protected double specificImpulse = 0;
	private double thrustOffset = 0;

	protected boolean isThrusting;

	public StandardThruster() {

	}

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
		int[] x = { 20, -4, -4, 20 };
		int[] y = { -4, -4, 4, 4 };
		this.setBounds(new HitBox(0, 0, new Polygon(x, y, x.length)));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void impact(DObject obj, CollisionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean hits(DObject objects) {
		// TODO Auto-generated method stub
		return false;
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
			return new Vector(this.getAngle() - this.thrustOffset, this.maxThrust * this.throttle / 100);
		}
		return new Vector(0, 0);
	}

	@Override
	public double getMaxThrust() {
		return this.maxThrust;
	}

	public void setMaxThrust(double maxThrust) {
		this.maxThrust = maxThrust;
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

	public void setSpecificImpulse(double specificImpulse) {
		this.specificImpulse = specificImpulse;
	}

	public double getThrustOffset() {
		return thrustOffset;
	}

	public void setThrustOffset(double thrustOffset) {
		this.thrustOffset = thrustOffset;
	}

}
