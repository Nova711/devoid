package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class StandardFuelTank extends StandardShipComponent implements FuelTank {

	protected double hp = 5;
	protected double tankMass = 5;
	protected int fuelType = 0;
	private double capacity = 10000;

	public double getTankMass() {
		return tankMass;
	}

	public void setTankMass(double tankMass) {
		this.tankMass = tankMass;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getCurrentVolume() {
		return currentVolume;
	}

	public void setCurrentVolume(double currentVolume) {
		this.currentVolume = currentVolume;
	}

	protected double currentVolume = this.capacity;

	public StandardFuelTank() {

	}

	public StandardFuelTank(Vector position) {
		super(position, new Vector(0, 0), 5, 0, 5, 1, 0, 0);
		this.setMass(this.getMass());
		int[] x = { 2, -2, -2, 2 };
		int[] y = { -2, -2, 2, 2 };
		//this.setBounds(new HitBox(0, 0, new Polygon(x, y, x.length)));
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
		if (fuel.getFuel() + amount > fuel.getFuelCapacity()) {
			amount = fuel.getFuelCapacity() - fuel.getFuel();
		}
		if (amount > this.getFuel()) {
			amount = this.getFuel();
		}
		fuel.fill(amount);
		this.drain(amount);
	}

}
