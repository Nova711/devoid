package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardFuelTank extends StandardDObject implements FuelTank {

	protected double hp = 5;
	protected double tankMass = 5;
	protected int fuelType = 0;
	protected double capacity = 10000;
	protected double currentVolume = this.capacity;

	public StandardFuelTank(Vector position) {
		super(position, new Vector(0, 0), 5, 0, 5, 1, 0, 0);
		this.setMass(this.getMass());
	}

	@Override
	public double getMass() {
		return this.tankMass + this.currentVolume;
	}

	@Override
	public double getI() {
		return this.getMass() * Math.pow(this.getPosition().getMagnitude(), 2);
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
