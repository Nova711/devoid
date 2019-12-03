package devoid_boosted;

public class StandardMissile extends StandardShip {

	protected double fieldOfView;

	protected DObject target;

	public StandardMissile(Vector position, Vector velocity, double mass) {
		super(position, velocity, mass);
		this.thrust = new Vector(0, 2.5);
	}

	@Override
	public void update() {
		if (target != null) {
			if ((Vector.fromXY(this.getX() - target.getX(), this.getY() - target.getY())).getAngle() > this.getAngle()
					+ Math.PI & !(this.angularVelocity > Math.PI / 45)) {
				this.rotateRight();
			}
			if ((Vector.fromXY(this.getX() - target.getX(), this.getY() - target.getY())).getAngle() < this.getAngle()
					+ Math.PI & !(this.angularVelocity < -Math.PI / 45)) {
				this.rotateLeft();
			}
			if ((int) ((Vector.fromXY(this.getX() - target.getX(), this.getY() - target.getY())).getAngle()
					* 1000) == (int) ((this.getAngle() + Math.PI) * 1000)) {
				this.setAngularVelocity(0);
			}
			if ((int) ((Vector.fromXY(this.getX() - target.getX(), this.getY() - target.getY())).getAngle()
					* 10) == (int) ((this.getAngle() + Math.PI) * 10)) {
				this.accel();
			}
			if ((int) ((Vector.fromXY(this.getX() - target.getX(), this.getY() - target.getY())).getAngle()
					* 10) == (int) (this.getAngle() * 10)) {
				this.deccel();
			}
		}
		super.update();
	}

	@Override
	public void accel() {
		this.velocity = this.velocity.add(new Vector(this.angle, 0.01));
	}

	@Override
	public void rotateLeft() {
		this.angularVelocity -= Math.PI / 360;
	}

	@Override
	public void rotateRight() {
		this.angularVelocity += Math.PI / 360;

	}

	void setTarget(DObject target) {
		this.target = target;
	}

}
