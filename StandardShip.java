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
	protected double thrusterThrottle = 50;
	protected double angularVelocity = 0;
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

	public StandardShip(Vector position, Vector velocity, double mass) {
		this.position = position;
		this.velocity = velocity;
		this.airFrameMass = mass;
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-5, 4), 0));
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-5, -4), 0));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(5, 4), Math.PI));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(5, -4), Math.PI));
		this.fuelTanks.add(new StandardFuelTank(new Vector(0, 0)));

		for (Thruster t : this.leftStrafeThrusters) {
			this.thrusters.add(t);
		}
		for (Thruster t : this.rightStrafeThrusters) {
			this.thrusters.add(t);
		}
		for (Thruster t : this.accelThrusters) {
			this.thrusters.add(t);
		}
		for (Thruster t : this.deccelThrusters) {
			this.thrusters.add(t);
		}
		for (Thruster t : this.leftTurnThrusters) {
			this.thrusters.add(t);
		}
		for (Thruster t : this.rightTurnThrusters) {
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
		if (!this.flightMode) {
			this.thrust = new Vector(0, 0);
			for (Thruster t : this.accelThrusters) {
				t.setThrottle(this.thrusterThrottle / 20);
				this.thrust = this.thrust.add(t.thrust(this.fuelTanks.get(0)));
			}
		}
		this.velocity = this.velocity
				.add(new Vector(this.thrust.getAngle() + this.angle, this.thrust.getMagnitude() / this.getMass()));
		this.position = this.position.add(this.velocity);
		this.angle += this.angularVelocity;
		// Out.println(this.toString());
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
		ng.drawRect(-5, -5, 10, 10);
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
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public void strafeLeft() {
		this.velocity = this.velocity.add(new Vector(-Math.PI / 2 + this.angle, 1));
	}

	@Override
	public void strafeRight() {
		this.velocity = this.velocity.add(new Vector(Math.PI / 2 + this.angle, 1));
	}

	@Override
	public void accel() {
		if (this.flightMode) {
			for (Thruster t : this.accelThrusters) {
				this.thrust(t);
			}
		} else
			this.throttleDown();
	}

	@Override
	public void deccel() {
		if (this.flightMode) {
			for (Thruster t : this.deccelThrusters) {
				this.thrust(t);
			}
		} else
			this.throttleDown();
	}

	public void thrust(Thruster t) {
		t.setThrottle(this.thrusterThrottle);
		Vector tempThrust = t.thrust(this.fuelTanks.get(0));
		tempThrust = new Vector(tempThrust.getAngle() + this.angle, tempThrust.getMagnitude() / this.getMass());
		this.velocity = this.velocity.add(tempThrust);
		Vector tempPos = t.getPosition();
		double angularAcceleration = tempPos.getMagnitude() * tempThrust.getMagnitude()
				* Math.sin(tempPos.getAngle() - tempThrust.getAngle() - this.getAngle()) / this.getI();
		this.angularVelocity += Math.abs(angularAcceleration) > Math.PI / 3600 ? angularAcceleration : 0;
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
	public void rotateLeft() {
		this.angularVelocity -= Math.PI / 360;
	}

	@Override
	public void rotateRight() {
		this.angularVelocity += Math.PI / 360;

	}

	@Override
	public void toggleFlightMode() {
		this.thrusterThrottle = 50;
		this.thrust = new Vector(0, 0);
		this.flightMode = !this.flightMode;
	}

	@Override
	public String toString() {
		return "p" + this.position.toString() + "v" + this.position.toString() + "t" + this.thrust.toString() + "a"
				+ this.angle + "m" + this.mass + "f" + this.airFrameMass + "e" + this.elasticity + "t"
				+ this.thrusterThrottle + "v" + this.angularVelocity + "T" + this.temperature + "f" + this.flightMode;
	}

}
