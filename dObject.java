package devoid_boosted;

import java.awt.Graphics;
import java.awt.Rectangle;

import physics.Vector;

public interface DObject {

	double getHP();

	double getX();

	double getY();
	
	double getAngle();
	
	double getMass();
	
	double getI();

	void setX(double x);

	void setY(double y);
	
	void setAngle(double angle);

	Vector getVelocity();

	void setVelocity(Vector velocity);

	double getAngularVelocity();

	void setAngularVelocity(double velocity);
	
	double getTemperature();
	
	void setTemperature(double temperature);

	void update();

	void impact(DObject obj);

	boolean hits(DObject[] objects);
	
	void draw(Graphics g);

}
