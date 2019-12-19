package devoid_boosted;

public interface Thruster extends ShipComponent {
	void activate();
	
	void deactivate();

	boolean isThrusting();

	Vector thrust(FuelTank fuel);

	double getMaxThrust();

	double getThrottle();

	void setThrottle(double throttle);

	double getSpecificImpulse();
}
