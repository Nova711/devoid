package devoid_boosted;

public class Bullet extends StandardDObject {

	protected double hp = 5;
	protected double mass = 50;

	public Bullet(Vector position, Vector velocity, double angle) {
		super(position, velocity, 5, angle, 50, 1, 0, 0);
	}

	@Override
	public void update() {
		this.setPosition(this.getPosition().add(this.getVelocity()));
		this.setAngle(this.getAngle() + this.getAngularVelocity());
	}

}
