package devoid_boosted;

import physics.Vector;

public interface Thruster extends ShipComponent {
	Vector thrust(FuelTank fuel);
	
	double getThrottle();
	
	void setThrottle(double throttle);
}
