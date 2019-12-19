package devoid_boosted;

interface Ship extends DObject {
	void strafeLeft();

	void strafeRight();

	void accel();

	void deccel();

	void throttleUp();

	void throttleDown();

	double getThrottle();

	void rotateLeft();

	void rotateRight();

	double getMaxFuel();

	double getFuel();

	void toggleFlightMode();
}
