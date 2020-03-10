package devoid_boosted;

public interface ReactionWheel extends ShipComponent {
	double getThrottle();
	void setThrottle();
	double getMaxTorque();
	double torqueLeft();
	double torqueRight();
	void activate();
	void deactivate();
	boolean isActive();
}
