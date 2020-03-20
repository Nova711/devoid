package devoid_boosted;

public class FlightAssist {
	private double maxXAccel;
	private double maxYAccel;
	private double maxTurnAccel;
	private double xAccel;
	private double yAccel;
	private double turnAccel;
	private double targetAngularVelocity;
	
	private boolean[][] requests = new boolean[3][3];
	
	private StandardShip ship;

	public FlightAssist(StandardShip ship) {
		this.ship = ship;
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
