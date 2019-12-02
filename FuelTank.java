package devoid_boosted;

public interface FuelTank extends ShipComponent {
	double getFuelCapacity();

	double getFuelType();

	double getFuel();

	void transfer(FuelTank fuel);

	void transfer(FuelTank fuel, double amount);
}
