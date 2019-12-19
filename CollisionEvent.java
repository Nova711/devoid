package devoid_boosted;

public class CollisionEvent {
	protected boolean collides;
	protected Vector position;
	protected double angle;

	public CollisionEvent() {

	}

	public CollisionEvent(Vector position, double angle) {
		this.position = position;
		this.angle = angle;
	}

	public boolean collides() {
		return this.collides;
	}

	public Vector getPosition() {
		return this.position;
	}

	public double getAngle() {
		return this.angle;
	}
}
