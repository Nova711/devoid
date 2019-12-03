package devoid_boosted;

public interface Weapon extends ShipComponent {
	double getAngle();

	int getProjectileType();

	int getSizeClass();

	double getRateOfFire();

	double getReloadTime();

	int getClipSize();

	void fire();
}
