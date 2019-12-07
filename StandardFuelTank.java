package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardFuelTank implements FuelTank {
	protected Vector position = new Vector(0, 0);
	protected Vector velocity = new Vector(0, 0);

	protected double hp = 5;
	protected double angle = 0;
	protected double tankMass = 5;
	protected double elasticity = 1;
	protected double angularVelocity = 0;
	protected double temperature = 0;
	protected int fuelType = 0;
	protected double capacity = 1000;
	protected double currentVolume = this.capacity;

	public StandardFuelTank(Vector position) {
		this.position = position;
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
		return this.tankMass + this.currentVolume;
	}

	@Override
	public double getI() {
		return this.getMass() * Math.pow(this.position.getMagnitude(), 2);
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
		ng.drawRect(-2, -2, 4, 4);
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public double getFuelCapacity() {
		return this.capacity;
	}

	@Override
	public double getFuelType() {
		return this.fuelType;
	}

	@Override
	public double getFuel() {
		return this.currentVolume;
	}

	@Override
	public void drain(double amount) {
		this.currentVolume -= amount > this.currentVolume ? 0 : amount;
	}

	@Override
	public void fill(double amount) {
		this.currentVolume += amount;
	}

	@Override
	public void transfer(FuelTank fuel) {
		while (this.currentVolume >= this.capacity & fuel.getFuel() <= fuel.getFuelCapacity()) {
			fuel.fill(this.currentVolume / 4);
			this.drain(this.currentVolume / 4);
		}
	}

	@Override
	public void transfer(FuelTank fuel, double amount) {
		// TODO Auto-generated method stub

	}

}
