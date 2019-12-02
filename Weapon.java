package devoid_boosted;

public interface Weapon extends ShipComponent {
	double getAngle();

	int getProjectileType();

	int getSizeClass();

	void fire();
}
