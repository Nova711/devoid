package devoid_boosted;

public interface WeaponMount extends Weapon {
	double getGymbalRange();

	void track(DObject target);
}
