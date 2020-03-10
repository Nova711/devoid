package devoid_boosted;

public class StandardReactionWheel extends StandardShipComponent implements ReactionWheel {

	private double maxTorque = 10;
	private double throttle = 1;
	private boolean isActive;

	@Override
	public double getThrottle() {
		return this.throttle;
	}

	@Override
	public void setThrottle() {
		this.throttle = throttle >= 0 ? throttle <= 100 ? throttle : 100 : 0;
	}

	@Override
	public double getMaxTorque() {
		return this.maxTorque;
	}

	public void setMaxTorque(double torque) {
		this.maxTorque = torque;
	}

	@Override
	public double torqueLeft() {
		return -this.getMaxTorque() * this.getThrottle() / 100;
	}

	@Override
	public double torqueRight() {
		return this.getMaxTorque() * this.getThrottle() / 100;
	}

	@Override
	public void activate() {
		this.isActive = true;
	}

	@Override
	public void deactivate() {
		this.isActive = false;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}
}
