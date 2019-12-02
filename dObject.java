package devoid_boosted;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface dObject {

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

	void impact(dObject obj);

	boolean hits(dObject[] objects);
	
	void draw(Graphics g);

}
