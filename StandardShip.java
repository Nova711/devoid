package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import rpg.Out;

public class StandardShip extends StandardDObject implements Ship {
	protected Vector thrust = new Vector(0, 0);

	private double airFrameMass = 500;
	private double thrusterThrottle = 100;
	private double prevAngularVelocity = 0;
	private boolean flightMode = true;

	protected HitBox bounds;
	protected double delay = 1;
	public boolean bounced;

	ArrayList<ShipComponent> shipComponents = new ArrayList<ShipComponent>();
	ArrayList<Thruster> thrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> leftStrafeThrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> rightStrafeThrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> accelThrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> deccelThrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> leftTurnThrusters = new ArrayList<Thruster>();
	ArrayList<Thruster> rightTurnThrusters = new ArrayList<Thruster>();
	ArrayList<FuelTank> fuelTanks = new ArrayList<FuelTank>();
	ArrayList<PowerSource> powerSources = new ArrayList<PowerSource>();
	ArrayList<Battery> batteries = new ArrayList<Battery>();
	Cockpit cockPit;

	public StandardShip() {

	}

	public StandardShip(Vector position, Vector velocity, double mass) {
		super(position, velocity, 5, 0, mass, 1, 0, 0);
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-60, 30), 0, 1000));
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-60, -30), 0, 1000));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(60, 30), Math.PI, 1000));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(60, -30), Math.PI, 1000));
		this.leftStrafeThrusters.add(new StandardThruster(Vector.fromXY(20, 20), -Math.PI / 2));
		this.leftStrafeThrusters.add(new StandardThruster(Vector.fromXY(-20, 20), -Math.PI / 2));
		this.rightStrafeThrusters.add(new StandardThruster(Vector.fromXY(20, -20), Math.PI / 2));
		this.rightStrafeThrusters.add(new StandardThruster(Vector.fromXY(-20, -20), Math.PI / 2));
		this.leftTurnThrusters.add(this.accelThrusters.get(0));
		this.leftTurnThrusters.add(this.deccelThrusters.get(1));
		this.leftTurnThrusters.add(this.leftStrafeThrusters.get(0));
		this.leftTurnThrusters.add(this.rightStrafeThrusters.get(1));
		this.rightTurnThrusters.add(this.accelThrusters.get(1));
		this.rightTurnThrusters.add(this.deccelThrusters.get(0));
		this.rightTurnThrusters.add(this.leftStrafeThrusters.get(1));
		this.rightTurnThrusters.add(this.rightStrafeThrusters.get(0));
		this.fuelTanks.add(new StandardFuelTank(new Vector(0, 0)));

		for (Thruster t : this.leftStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.accelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.deccelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.leftTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.thrusters) {
			this.shipComponents.add(t);
		}
		for (FuelTank f : this.fuelTanks) {
			this.shipComponents.add(f);
		}
		this.setMass(this.getMass());
	}

	public StandardShip(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature) {
		super(position, velocity, hp, angle, mass, elasticity, angularVelocity, temperature);
	}

	@Override
	public double getHP() {
		double sum = 0;
		for (ShipComponent s : shipComponents)
			sum += s.getHP();
		return sum;
	}

	@Override
	public double getMass() {
		double sum = this.airFrameMass;
		for (ShipComponent s : shipComponents)
			sum += s.getMass();
		return sum;
	}

	@Override
	public double getI() {
		double sum = 10;
		for (ShipComponent s : shipComponents)
			sum += s.getI();
		return sum;
	}

	@Override
	public void update() {
		this.thrust = new Vector(0, 0);
		if (!this.flightMode) {
			for (Thruster t : this.accelThrusters) {
				t.setThrottle(this.thrusterThrottle);
				t.activate();
			}
		}
		for (Thruster t : this.thrusters) {
			if (t.isThrusting()) {
				Vector temp = t.thrust(this.fuelTanks.get(0));
				this.thrust = this.thrust.add(temp);
				if (temp.getMagnitude() > 0)
					this.setAngularVelocity(this.getAngularVelocity() + this.getAngularAcceleration(t));
				;
				t.deactivate();
			}
		}
		this.setVelocity(this.getVelocity().add(
				new Vector(this.thrust.getAngle() + this.getAngle(), this.thrust.getMagnitude() / this.getMass())));
		this.setPosition(this.getPosition().add(this.getVelocity()));
		this.setAngle(this.getAngle() + this.getAngularVelocity());
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
		// ng.drawRect(-20, -10, 40, 20);
		for (ShipComponent s : shipComponents)
			s.draw(ng);
		ng.rotate(-this.getAngle());
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.red);
		temp = new Vector(this.thrust.getAngle() + this.getAngle(), this.thrust.getMagnitude());
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.blue);
		temp = new Vector(this.getVelocity().getAngle(), this.getVelocity().getMagnitude());
		g.drawLine(0, 0, (int) (temp.getX() * 25), (int) (temp.getY() * 25));
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public void strafeLeft() {
		for (Thruster t : this.leftStrafeThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void strafeRight() {
		for (Thruster t : this.rightStrafeThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void accel() {
		for (Thruster t : this.accelThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void deccel() {
		for (Thruster t : this.deccelThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	public double getAngularAcceleration(Thruster t) {
		t.setThrottle(this.thrusterThrottle);
		Vector tempPos = t.getPosition();
		Vector tempThrust = new Vector(t.getAngle(), t.getMaxThrust() * t.getThrottle() / 100);
		double angularAcceleration = tempPos.getMagnitude() * tempThrust.getMagnitude()
				* Math.sin(tempThrust.getAngle() - tempPos.getAngle()) / this.getI();
		return angularAcceleration;
	}

	@Override
	public void throttleUp() {
		if (this.thrusterThrottle < 100)
			this.thrusterThrottle++;
	}

	@Override
	public void throttleDown() {
		if (this.thrusterThrottle > 1)
			this.thrusterThrottle--;
	}

	@Override
	public double getThrottle() {
		return this.thrusterThrottle;
	}

	public void setThrottle(double throttle) {
		this.thrusterThrottle = throttle;
	}

	@Override
	public void rotateLeft() {
		for (Thruster t : this.leftTurnThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void rotateRight() {
		for (Thruster t : this.rightTurnThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public double getFuel() {
		double sum = 0;
		for (FuelTank f : this.fuelTanks)
			sum += f.getFuel();
		return sum;
	}

	@Override
	public double getMaxFuel() {
		double sum = 0;
		for (FuelTank f : this.fuelTanks)
			sum += f.getFuelCapacity();
		return sum;
	}

	@Override
	public void toggleFlightMode() {
		this.thrusterThrottle = 50;
		this.thrust = new Vector(0, 0);
		this.flightMode = !this.flightMode;
	}

	@Override
	public String toString() {
		return "" + this.getPosition().toString() + " " + this.getVelocity().toString() + " " + this.thrust.toString()
				+ " " + this.getAngle() + " " + this.getMass() + " " + this.airFrameMass + " " + this.getI() + " "
				+ this.thrusterThrottle + " " + this.getAngularVelocity() + " " + this.getTemperature() + " "
				+ this.flightMode;
	}

	public double airFrameMass() {
		return this.airFrameMass;
	}

	public void setAirFrameMass(double mass) {
		this.airFrameMass = mass;
	}

}
