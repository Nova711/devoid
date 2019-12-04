package devoid_boosted;

interface Ship extends DObject {
	void strafeLeft();

	void strafeRight();

	void accel();

	void deccel();

	void throttleUp();

	void throttleDown();

	void rotateLeft();

	void rotateRight();

	void toggleFlightMode();
}
