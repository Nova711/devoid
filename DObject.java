package devoid_boosted;

import java.awt.Graphics;

public interface DObject {

	double getHP();

	Vector getPosition();

	void setPosition(Vector position);

	Vector getVelocity();

	void setVelocity(Vector velocity);

	void applyForce(Vector force, Vector position);

	double getX();

	double getY();

	void setX(double x);

	void setY(double y);

	double getAngle();

	void setAngle(double angle);

	double getMass();

	void setMass(double mass);

	double getI();

	double getAngularVelocity();

	void setAngularVelocity(double velocity);

	double getTemperature();

	void setTemperature(double temperature);

	void update();

	void impact(DObject obj, CollisionEvent e);

	boolean hits(DObject[] objects);

	void draw(Graphics g);

	PhysicsBox getEnvironment();

	void setEnvironment(PhysicsBox b);

}
