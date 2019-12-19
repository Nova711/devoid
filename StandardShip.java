package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import rpg.Out;

public class StandardShip implements Ship {
	protected Vector position = new Vector(0, 0);
	protected Vector velocity = new Vector(0, 0);
	protected Vector thrust = new Vector(0, 0);

	protected double angle = 0;
	protected double mass;
	protected double airFrameMass = 500;
	protected double elasticity = 1;
	protected double thrusterThrottle = 100;
	protected double angularVelocity = 0;
	protected double prevAngularVelocity = 0;
	protected double temperature = 0;
	protected boolean flightMode = true;

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
		this.position = position;
		this.velocity = velocity;
		this.airFrameMass = mass;
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
	}

	@Override
	public double getHP() {
		double sum = 0;
		for (ShipComponent s : shipComponents)
			sum += s.getHP();
		return sum;
	}

	@Override
	public Vector getPosition() {
		return this.position;
	}

	@Override
	public double getX() {
		return position.getX();
	}

	@Override
	public double getY() {
		return position.getY();
	}

	@Override
	public double getAngle() {
		return this.angle;
	}

	@Override
	public double getMass() {
		double sum = this.airFrameMass;
		for (ShipComponent s : shipComponents)
			sum += s.getMass();
		return (this.mass = sum);
	}

	@Override
	public double getI() {
		double sum = 10;
		for (ShipComponent s : shipComponents)
			sum += s.getI();
		return sum;
	}

	@Override
	public void setX(double x) {
		this.position = Vector.fromXY(x, this.position.getY());
	}

	@Override
	public void setY(double y) {
		this.position = Vector.fromXY(this.velocity.getX(), y);
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
					this.angularVelocity += this.getAngularAcceleration(t);
				t.deactivate();
			}
		}
		this.velocity = this.velocity
				.add(new Vector(this.thrust.getAngle() + this.angle, this.thrust.getMagnitude() / this.getMass()));
		this.position = this.position.add(this.velocity);
		this.angle += this.angularVelocity;
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
		ng.drawRect(-20, -10, 40, 20);
		for (ShipComponent s : shipComponents)
			s.draw(ng);
		ng.rotate(-this.getAngle());
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.red);
		temp = new Vector(this.thrust.getAngle() + this.getAngle(), this.thrust.getMagnitude());
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.blue);
		temp = new Vector(this.velocity.getAngle(), this.velocity.getMagnitude());
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

	public void dampAngularVelocity() {
		this.angularVelocity = Math.abs(this.angularVelocity - this.prevAngularVelocity) > Math.PI / 72000
				? this.angularVelocity
				: this.prevAngularVelocity;
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
		return "" + this.position.toString() + " " + this.position.toString() + " " + this.thrust.toString() + " "
				+ this.angle + " " + this.getMass() + " " + this.airFrameMass + " " + this.getI() + " "
				+ this.elasticity + " " + this.thrusterThrottle + " " + this.angularVelocity + " " + this.temperature
				+ " " + this.flightMode;
	}

}
