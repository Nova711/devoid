package devoid_boosted;

public interface PowerSource extends ShipComponent {
	int getType();

	double getOutput();

	double getBufferSize();

	void discharge();
	
	double connect(ShipComponent sc);
	
	double diconnect(ShipComponent sc);
}
