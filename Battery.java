package devoid_boosted;

public interface Battery extends ShipComponent {
	int getType();

	double getCapacity();

	double getCharge();

	void discharge();

	void connect(ShipComponent sc);

	void diconnect(ShipComponent sc);
}
