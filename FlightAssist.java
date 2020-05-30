package devoid_boosted;

import rpg.Out;

public class FlightAssist {
	private double maxXAccel;
	private double maxYAccel;
	private double maxTurnAccel;
	private double xAccel;
	private double yAccel;
	private double turnAccel;
	private double targetAngularVelocity;

	private boolean[][] requests = new boolean[2][3];

	private StandardShip ship;

	public FlightAssist(StandardShip ship) {
		this.ship = ship;
	}

	public static void main(String[] args) {
		StandardThruster t1 = new StandardThruster(Vector.fromXY(0, -5), 0, 100, null);
		StandardThruster t2 = new StandardThruster(Vector.fromXY(0, 10), 0, 100, null);
		ThrusterPair tp = new ThrusterPair(t1, t2);
		tp.requestThrust(200, 0);
		System.out.println(t1.getThrottle() + " " + t2.getThrottle());
	}

	public void update() {
		if (this.getRequests()[0][0]) {

		}
		if (this.getRequests()[0][1]) {

		}
		if (this.getRequests()[0][2]) {

		}
		if (this.getRequests()[1][0]) {

		}
		if (this.getRequests()[1][1]) {

		}
		if (this.getRequests()[1][2]) {

		}
		this.clearRequests();
	}

	public double[] calculate(double[] forces, double[] crossProducts) {
		double[] throttle = new double[forces.length];
		return null;
	}

	public void requestForward() {
		this.requests[0][1] = true;
	}

	public void requestBackward() {
		this.requests[1][1] = true;
	}

	public void requestLeftStrafe() {
		this.requests[1][0] = true;
	}

	public void requestRightStrafe() {
		this.requests[1][2] = true;
	}

	public void requestLeftTurn() {
		this.requests[0][0] = true;
	}

	public void requestRightTurn() {
		this.requests[0][2] = true;
	}

	public void clearRequests() {
		this.requests = new boolean[this.requests.length][this.requests[0].length];
	}

	public double getMaxXAccel() {
		return maxXAccel;
	}

	public void setMaxXAccel(double maxXAccel) {
		this.maxXAccel = maxXAccel;
	}

	public double getMaxYAccel() {
		return maxYAccel;
	}

	public void setMaxYAccel(double maxYAccel) {
		this.maxYAccel = maxYAccel;
	}

	public double getMaxTurnAccel() {
		return maxTurnAccel;
	}

	public void setMaxTurnAccel(double maxTurnAccel) {
		this.maxTurnAccel = maxTurnAccel;
	}

	public double getxAccel() {
		return xAccel;
	}

	public void setxAccel(double xAccel) {
		this.xAccel = xAccel;
	}

	public double getyAccel() {
		return yAccel;
	}

	public void setyAccel(double yAccel) {
		this.yAccel = yAccel;
	}

	public double getTurnAccel() {
		return turnAccel;
	}

	public void setTurnAccel(double turnAccel) {
		this.turnAccel = turnAccel;
	}

	public double getTargetAngularVelocity() {
		return targetAngularVelocity;
	}

	public void setTargetAngularVelocity(double targetAngularVelocity) {
		this.targetAngularVelocity = targetAngularVelocity;
	}

	public boolean[][] getRequests() {
		return requests;
	}

	public void setRequests(boolean[][] requests) {
		this.requests = requests;
	}

	public StandardShip getShip() {
		return ship;
	}

	public void setShip(StandardShip ship) {
		this.ship = ship;
	}

}

class ThrusterPair {
	private Thruster t1;
	private Thruster t2;

	public ThrusterPair(Thruster t1, Thruster t2) {
		this.t1 = t1;
		this.t2 = t2;

	}

	public void requestThrust(double force, double torque) {
		t1.setThrottle(0);
		t2.setThrottle(0);
		if (force <= 0 | force > this.maxThrust(torque))
			return;
		double sTheta1 = Math.sin(t1.getPosition().getAngle() - t1.getAngle());
		double sTheta2 = Math.sin(t2.getPosition().getAngle() - t2.getAngle());
		double n1 = force * t2.getPosition().getMagnitude() * sTheta2 - torque;
		double d1 = t2.getPosition().getMagnitude() * sTheta2 * t1.getMaxThrust()
				* (1 - t1.getPosition().getMagnitude() * sTheta1 / (t2.getPosition().getMagnitude() * sTheta2));
		double n2 = force * t1.getPosition().getMagnitude() * sTheta1 - torque;
		double d2 = t1.getPosition().getMagnitude() * sTheta1 * t2.getMaxThrust()
				* (1 - t2.getPosition().getMagnitude() * sTheta2 / (t1.getPosition().getMagnitude() * sTheta1));
		double k1 = n1 / d1;
		double k2 = n2 / d2;
		if (k1 >= 0 & k1 <= 1 & k2 >= 0 & k2 <= 1) {
			t1.setThrottle(k1 * 100);
			t2.setThrottle(k2 * 100);
		}
	}

	public double maxThrust(double torque) {
		double maxThrust = 0;
		double sTheta1 = Math.sin(t1.getPosition().getAngle() - t1.getAngle());
		double sTheta2 = Math.sin(t2.getPosition().getAngle() - t2.getAngle());
		double k1 = -(t2.getMaxThrust() * t2.getPosition().getMagnitude() * sTheta2 - torque)
				/ (t1.getMaxThrust() * t1.getPosition().getMagnitude() * sTheta1);
		double k2 = -(t1.getMaxThrust() * t1.getPosition().getMagnitude() * sTheta1 - torque)
				/ (t2.getMaxThrust() * t2.getPosition().getMagnitude() * sTheta2);
		Out.println("k1 " + k1);
		Out.println("k2 " + k2);
		if (k1 <= 1) {
			maxThrust = k1 * t1.getMaxThrust() + t2.getMaxThrust();
		} else {
			maxThrust = t1.getMaxThrust() + k2 * t2.getMaxThrust();
		}
		Out.println(maxThrust);
		return maxThrust;
	}
}

class OpposedThrusterPair {
	private Thruster t1;
	private Thruster t2;

	public OpposedThrusterPair(Thruster t1, Thruster t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	public void requestTorque(double torque) {

	}

	public double maxTorque() {
		return 0;
	}
}
