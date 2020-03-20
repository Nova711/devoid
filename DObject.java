package devoid_boosted;

import java.awt.Graphics;

/**
 * DObject--All of the behavior a physics enabled object is supposed to have
 * 
 * @author Carter Rye
 */
public interface DObject {

	/**
	 * Gets the hit points of this DObject
	 * 
	 * @return The hit points of this DObject
	 */
	double getHP();

	/**
	 * Sets the hit points of this DObject
	 * 
	 * @param hp The new hit points of this DObject
	 */
	void setHP(double hp);

	/**
	 * Gets the position of this DObject as a {@link Vector}
	 * 
	 * @return The position of this DObject
	 */
	Vector getPosition();

	/**
	 * Sets the position of this DObject as a {@link Vector}
	 * 
	 * @param position The new position of this DObject
	 */
	void setPosition(Vector position);

	/**
	 * Gets the velocity of this DObject as a {@link Vector}
	 * 
	 * @return The velocity of this DObject
	 */
	Vector getVelocity();

	/**
	 * Sets the velocity of this DObject as a {@link Vector}
	 * 
	 * @param velocity The new velocity of this DObject
	 */
	void setVelocity(Vector velocity);

	/**
	 * Applies a {@link Vector} force on this object at the specified {@link Vector}
	 * position
	 * 
	 * @param force    the force applied
	 * @param position the position of the applied force relative to the origin of
	 *                 this DObject
	 */
	void applyForce(Vector force, Vector position);	

	void applyTorque(double torque);

	/**
	 * Gets the x position of this DObject
	 * 
	 * @return The x coordinate of this DObject
	 */
	double getX();

	/**
	 * Gets the y position of this DObject
	 * 
	 * @return The y coordinate of this DObject
	 */
	double getY();

	/**
	 * Sets the x position of this DObject
	 * 
	 * @param x The new x coordinate
	 */
	void setX(double x);

	/**
	 * Sets the y position of this DObject
	 * 
	 * @param y The new y coordinate
	 */
	void setY(double y);

	/**
	 * Gets the angle of this DObject
	 * 
	 * @return The angle of this DObject
	 */
	double getAngle();

	/**
	 * Sets the angle of this DObject
	 * 
	 * @param angle The new angle
	 */
	void setAngle(double angle);

	/**
	 * Gets the mass of this DObject
	 * 
	 * @return The mass of this DObject
	 */
	double getMass();

	/**
	 * Sets the mass of this DObject
	 * 
	 * @param mass The new mass
	 */
	void setMass(double mass);

	/**
	 * Gets the moment of inertia of this DObject
	 * 
	 * @return The moment of inertia of this DObject
	 */
	double getI();

	/**
	 * Gets the angular velocity of this DObject
	 * 
	 * @return The angular velocity of this DObject
	 */
	double getAngularVelocity();

	/**
	 * Sets the angular velocity of this DObject
	 * 
	 * @param velocity The new angular velocity
	 */
	void setAngularVelocity(double velocity);

	/**
	 * Gets the temperature of this DObject
	 * 
	 * @return The temperature of this DObject
	 */
	double getTemperature();

	/**
	 * Sets the temperature of this DObject
	 * 
	 * @param temperature The new temperature
	 */
	void setTemperature(double temperature);

	/**
	 * Updates this object by one interval
	 */
	void update();

	/**
	 * Calculates the effects of the collision between DObjects
	 * 
	 * @param obj The DObject being collided with
	 * @param e   The location of the collision
	 */
	void impact(DObject obj, CollisionEvent e);

	/**
	 * Checks if this object collides with the specified DObject
	 * 
	 * @param objects The object possibly being collided with
	 * @return If there is a collision
	 */
	boolean hits(DObject object);

	boolean checkForDestruction();

	/**
	 * Draws this object
	 * 
	 * @param g The graphics object used to draw this object
	 */
	void draw(Graphics g);

	/**
	 * Gets the environment this DObject is contained in
	 * 
	 * @return The environment of this DObject
	 */
	PhysicsBox getEnvironment();

	/**
	 * Sets the environment of this DObject
	 * 
	 * @param b The new environment
	 */
	void setEnvironment(PhysicsBox b);

}
