package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class StandardShip implements Ship {
	protected Vector position = new Vector(0, 0);
	protected Vector velocity = new Vector(0, 0);
	protected Vector thrust = new Vector(0, 0);

	protected double angle = 0;
	protected double mass;
	protected double airFrameMass = 500;
	protected double elasticity = 1;
	protected double thrusterThrottle = 0;
	protected double angularVelocity = 0;
	protected double temperature = 0;

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
	}

	@Override
	public double getHP() {
		double sum = 0;
		for (ShipComponent s : shipComponents)
			sum += s.getHP();
		return sum;
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
		return 0;
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
		ng.fillRect(-5, -5, 10, 10);
		ng.rotate(-this.getAngle());
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.red);
		temp = new Vector(this.thrust.getAngle(), this.thrust.getMagnitude());
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
		this.velocity = this.velocity.add(new Vector(this.angle, 1));
	}

	@Override
	public void deccel() {
		this.velocity = this.velocity.add(new Vector(Math.PI + this.angle, 1));

	}

	@Override
	public void rotateLeft() {
		this.angularVelocity -= Math.PI / 360;
	}

	@Override
	public void rotateRight() {
		this.angularVelocity += Math.PI / 360;

	}

}
