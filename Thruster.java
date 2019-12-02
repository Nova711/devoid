package devoid_boosted;

public interface Thruster extends ShipComponent {
	Vector thrust(FuelTank fuel);
	
	double getThrottle();
	
	void setThrottle(double throttle);
}
