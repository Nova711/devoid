package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardDObject implements DObject {
	private Vector position = new Vector(0, 0);
	private Vector velocity = new Vector(0, 0);

	private double hp;
	private double angle;
	private double mass;
	// private double elasticity;
	private double angularVelocity;
	private double temperature;
	private PhysicsBox environment;
	private HitBox bounds;
	private Color color = Color.black;

	public StandardDObject() {

	}

	public StandardDObject(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature) {
		this.position = position;
		this.velocity = velocity;
		this.hp = hp;
		this.angle = angle;
		this.mass = mass;
		// this.elasticity = elasticity;
		this.angularVelocity = angularVelocity;
		this.temperature = temperature;
	}

	public double getHP() {
		return this.hp;
	}

	public void setHP(double hp) {
		this.hp = hp;
	}

	public Vector getPosition() {
		return this.position;
	}

	public double getX() {
		return this.position.getX();
	}

	public double getY() {
		return this.position.getY();
	}

	public double getAngle() {
		return this.angle;
	}

	public double getMass() {
		return this.mass;
	}

	public double getI() {
		return this.getMass() * Math.pow(this.getPosition().getMagnitude(), 2);
	}

	public void setX(double x) {
		// TODO Auto-generated method stub
	}

	public void setY(double y) {
		// TODO Auto-generated method stub
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Vector getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void applyForce(Vector force, Vector position) {
		Vector acceleration = new Vector(force.getAngle() + this.getAngle(), force.getMagnitude() / this.getMass());
		this.setVelocity(this.getVelocity().add(acceleration));
		if (acceleration.getMagnitude() != 0)
			this.setAngularVelocity(this.getAngularVelocity() + this.getAngularAcceleration(force, position));
	}

	public double getAngularAcceleration(Vector force, Vector position) {
		double angularAcceleration = position.getMagnitude() * force.getMagnitude()
				* Math.sin(force.getAngle() - position.getAngle()) / this.getI();
		return angularAcceleration;
	}

	public double getAngularVelocity() {
		return this.angularVelocity;
	}

	public void setAngularVelocity(double velocity) {
		this.angularVelocity = velocity;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public void update() {
		// TODO Auto-generated method stub
	}

	public void impact(DObject obj, CollisionEvent e) {
		Vector tempVelocity = new Vector(this.getVelocity().getMagnitude(),
				this.getVelocity().getAngle() - e.getAngle());
		Vector objTempVelocity = new Vector(obj.getVelocity().getMagnitude(),
				obj.getVelocity().getAngle() - e.getAngle());
		double y = (this.getMass() - obj.getMass()) / (this.getMass() + obj.getMass()) * tempVelocity.getY()
				+ 2 * obj.getMass() / (this.getMass() + obj.getMass()) * objTempVelocity.getY();
		Vector newVelocity = Vector.fromXY(tempVelocity.getX(), y);
		Vector force = newVelocity.add(new Vector(tempVelocity.getAngle(), -tempVelocity.getMagnitude()));
		this.applyForce(force, e.getPosition());
	}

	public boolean hits(DObject objects) {
		// TODO Auto-generated method stub
		return false;
	}

	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(this.getColor());
		//g.drawLine(0, 0, (int) (this.getX()), (int) (this.getY()));
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		this.getBounds().draw(ng);
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	public PhysicsBox getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(PhysicsBox b) {
		this.environment = b;
	}

	@Override
	public void setPosition(Vector position) {
		this.position = position;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;
	}

	public HitBox getBounds() {
		return this.bounds;
	}

	public void setBounds(HitBox bounds) {
		this.bounds = bounds;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
