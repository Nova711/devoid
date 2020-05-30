package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import rpg.Out;

public class StandardShip extends StandardDObject implements Ship {
	protected Vector thrust = new Vector(0, 0);

	private double airFrameMass = 500;
	private double thrusterThrottle = 1;
	private double prevAngularVelocity = 0;
	private boolean flightMode = true;
	private boolean isCruising;

	private boolean isDampened;

	private Vector navPoint = new Vector(0, 0);

	protected ArrayList<CustomPolygon> bounds;
	protected double delay = 1;
	public boolean bounced;

	protected ArrayList<ShipComponent> shipComponents = new ArrayList<ShipComponent>();
	protected ArrayList<Thruster> thrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> leftStrafeThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> rightStrafeThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> accelThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> cruiseThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> deccelThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> leftTurnThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> rightTurnThrusters = new ArrayList<Thruster>();
	protected ArrayList<ReactionWheel> reactionWheels = new ArrayList<ReactionWheel>();
	protected ArrayList<FuelTank> fuelTanks = new ArrayList<FuelTank>();
	protected ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	protected ArrayList<PowerSource> powerSources = new ArrayList<PowerSource>();
	protected ArrayList<Battery> batteries = new ArrayList<Battery>();
	protected Cockpit cockPit = new StandardCockpit();
	
	protected ArrayList<FuelTank> upperFuelTanks = new ArrayList<FuelTank>();
	protected ArrayList<FuelTank> middleFuelTanks = new ArrayList<FuelTank>();
	protected ArrayList<FuelTank> lowerFuelTanks = new ArrayList<FuelTank>();
	protected ArrayList<Thruster> upperThrusters = new ArrayList<Thruster>();
	protected ArrayList<Thruster> lowerThrusters = new ArrayList<Thruster>();
	protected ArrayList<Weapon> upperWeapons = new ArrayList<Weapon>();
	protected ArrayList<Weapon> lowerWeapons = new ArrayList<Weapon>();

	public StandardShip() {

	}

	public StandardShip(Vector position, Vector velocity, double mass, PhysicsBox environment) {
		super(position, velocity, 5, 0, mass, 1, 0, 0, environment);
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-60, 30), 0, 1000, environment));
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-60, -30), 0, 1000, environment));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(60, 30), Math.PI, 1000, environment));
		this.deccelThrusters.add(new StandardThruster(Vector.fromXY(60, -30), Math.PI, 1000, environment));
		this.leftStrafeThrusters.add(new StandardThruster(Vector.fromXY(20, 20), -Math.PI / 2, environment));
		this.leftStrafeThrusters.add(new StandardThruster(Vector.fromXY(-20, 20), -Math.PI / 2, environment));
		this.rightStrafeThrusters.add(new StandardThruster(Vector.fromXY(20, -20), Math.PI / 2, environment));
		this.rightStrafeThrusters.add(new StandardThruster(Vector.fromXY(-20, -20), Math.PI / 2, environment));
		this.leftTurnThrusters.add(this.accelThrusters.get(0));
		this.leftTurnThrusters.add(this.deccelThrusters.get(1));
		this.leftTurnThrusters.add(this.leftStrafeThrusters.get(0));
		this.leftTurnThrusters.add(this.rightStrafeThrusters.get(1));
		this.rightTurnThrusters.add(this.accelThrusters.get(1));
		this.rightTurnThrusters.add(this.deccelThrusters.get(0));
		this.rightTurnThrusters.add(this.leftStrafeThrusters.get(1));
		this.rightTurnThrusters.add(this.rightStrafeThrusters.get(0));
		this.fuelTanks.add(new StandardFuelTank(new Vector(0, 0), environment));
		this.setMass(this.getMass());
	}

	public StandardShip(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature, PhysicsBox environment) {
		super(position, velocity, hp, angle, mass, elasticity, angularVelocity, temperature, environment);
	}

	@Override
	public double getHP() {
		double sum = 0;
		for (ShipComponent s : shipComponents)
			sum += s.getHP();
		return sum;
	}

	@Override
	public double getMass() {
		double sum = this.airFrameMass;
		for (ShipComponent s : shipComponents)
			sum += s.getMass();
		return sum;
	}

	@Override
	public double getI() {
		double sum = 10;
		for (ShipComponent s : shipComponents)
			sum += s.getI();
		return sum;
	}

	@Override
	public void update() {
		this.thrust = new Vector(0, 0);
		if (!this.flightMode) {
			for (Thruster t : this.isCruising ? this.cruiseThrusters : this.accelThrusters) {
				t.setThrottle(this.thrusterThrottle);
				t.activate();
			}
		}
		for (Thruster t : this.thrusters) {
			if (t.isThrusting()) {
				Vector temp = t.thrust(this.fuelTanks.get(0));
				this.applyForce(temp, t.getPosition());
				// this.thrust = this.thrust.add(temp);
				// if (temp.getMagnitude() > 0)
				// this.setAngularVelocity(this.getAngularVelocity() +
				// this.getAngularAcceleration(t));
				t.deactivate();
			}
		}
		if (this.isDampened) {
			this.setVelocity(new Vector(this.getVelocity().getAngle(), this.getVelocity().getMagnitude() * 0.99));
			this.setAngularVelocity(this.getAngularVelocity() * 0.99);
		}
		super.update();
	}

	@Override
	public void impact(DObject obj, CollisionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hits(DObject objects) {
		for (ShipComponent comp : this.shipComponents) {
			return comp.hits(objects);
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(Color.black);
		ng.translate(this.getX() * this.getEnvironment().getPixelsPerMetre(),
				this.getY() * this.getEnvironment().getPixelsPerMetre());// moves the origin of the graphics object to
																			// the coordinates of
		// this
		// ship
		ng.rotate(this.getAngle());
		for (ShipComponent s : shipComponents) {// draws all of the components in this ship
			s.draw(ng);
		}
		g.setColor(Color.black);
		g.drawLine(0, 0, 25, 0);
		g.setColor(Color.green);
		g.drawArc(-25, -25, 50, 50, 0,
				(int) -Math.toDegrees(this.getAngularVelocity() * 25 / (double) this.getEnvironment().getTickrate()));// displays
																														// the
																														// angular
		// velocity of this ship
		ng.rotate(-this.getAngle());
		g.setColor(Color.black);
		g.drawLine(0, 0, 25, 0);
		g.setColor(Color.red);
		Vector temp = new Vector(
				Vector.fromXY(this.navPoint.getX() - this.getX(), this.navPoint.getY() - this.getY()).getAngle(), 1);
		g.drawLine(0, 0, (int) (temp.getX() * 25), (int) (temp.getY() * 25));// draws the navpoint line
		g.setColor(Color.blue);
		temp = new Vector(this.getVelocity().getAngle(), 10 * Math.log10(
				this.getVelocity().scalarMultiply(1 / (double) this.getEnvironment().getTickrate()).getMagnitude() / 10
						+ 1));
		g.drawLine(0, 0, (int) (temp.getX() * 25), (int) (temp.getY() * 25));// draws the velocity line
		ng.translate(-this.getX() * this.getEnvironment().getPixelsPerMetre(),
				-this.getY() * this.getEnvironment().getPixelsPerMetre());// moves the origin back to its original
																			// position
	}

	@Override
	public void strafeLeft() {
		for (Thruster t : this.leftStrafeThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void strafeRight() {
		for (Thruster t : this.rightStrafeThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void accel() {
		for (Thruster t : this.isCruising ? this.cruiseThrusters : this.accelThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void deccel() {
		for (Thruster t : this.deccelThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	public double getAngularAcceleration(Thruster t) {
		t.setThrottle(this.thrusterThrottle);
		Vector tempPos = t.getPosition();
		Vector tempThrust = new Vector(t.getAngle(), t.getMaxThrust() * t.getThrottle() / 100);
		double angularAcceleration = tempPos.getMagnitude() * tempThrust.getMagnitude()
				* Math.sin(tempThrust.getAngle() - tempPos.getAngle()) / this.getI();
		return angularAcceleration;
	}

	@Override
	public void throttleUp() {
		if (this.thrusterThrottle < 100)
			this.thrusterThrottle++;
	}

	@Override
	public void throttleDown() {
		if (this.thrusterThrottle > 1)
			this.thrusterThrottle--;
	}

	@Override
	public double getThrottle() {
		return this.thrusterThrottle;
	}

	public void setThrottle(double throttle) {
		this.thrusterThrottle = throttle;
	}

	@Override
	public void rotateLeft() {
		for (Thruster t : this.leftTurnThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public void rotateRight() {
		for (Thruster t : this.rightTurnThrusters) {
			t.setThrottle(this.thrusterThrottle);
			t.activate();
		}
	}

	@Override
	public double getFuel() {
		double sum = 0;
		for (FuelTank f : this.fuelTanks)
			sum += f.getFuel();
		return sum;
	}

	@Override
	public double getMaxFuel() {
		double sum = 0;
		for (FuelTank f : this.fuelTanks)
			sum += f.getFuelCapacity();
		return sum;
	}

	@Override
	public void toggleFlightMode() {
		// this.thrusterThrottle = 1;
		this.thrust = new Vector(0, 0);
		this.flightMode = !this.flightMode;
	}

	@Override
	public void toggleCruise() {
		this.isCruising = !this.isCruising;
	}

	public void toggleDampening() {
		this.isDampened = !this.isDampened;
	}

	@Override
	public String toString() {
		return "" + this.getPosition().toString() + " " + this.getVelocity().toString() + " " + this.thrust.toString()
				+ " " + this.getAngle() + " " + this.getMass() + " " + this.airFrameMass + " " + this.getI() + " "
				+ this.thrusterThrottle + " " + this.getAngularVelocity() + " " + this.getTemperature() + " "
				+ this.flightMode;
	}

	public double airFrameMass() {
		return this.airFrameMass;
	}

	public void setAirFrameMass(double mass) {
		this.airFrameMass = mass;
	}

	public Vector getNavPoint() {
		return navPoint;
	}

	public void setNavPoint(Vector navPoint) {
		this.navPoint = navPoint;
	}

	public Cockpit getCockpit() {
		return this.cockPit;
	}

	public void setCockpit(Cockpit cockpit) {
		this.cockPit = cockpit;
		this.resetComponents();
	}

	public void resetComponents() {
		this.shipComponents = new ArrayList<ShipComponent>();
		this.shipComponents.addAll(fuelTanks);
		this.shipComponents.addAll(weapons);
		this.thrusters.addAll(accelThrusters);
		this.thrusters.addAll(cruiseThrusters);
		this.thrusters.addAll(deccelThrusters);
		this.thrusters.addAll(leftStrafeThrusters);
		this.thrusters.addAll(leftTurnThrusters);
		this.thrusters.addAll(rightStrafeThrusters);
		this.thrusters.addAll(rightTurnThrusters);
		this.shipComponents.addAll(thrusters);
		this.shipComponents.add(this.getCockpit());
	}

	@Override
	public ArrayList<CustomPolygon> getBounds() {
		return this.bounds;
	}

	@Override
	public void setEnvironment(PhysicsBox b) {
		super.setEnvironment(b);
		for (ShipComponent comp : this.shipComponents)
			comp.setEnvironment(b);
	}

	public Vector getThrust() {
		return thrust;
	}

	public void setThrust(Vector thrust) {
		this.thrust = thrust;
	}

	public double getThrusterThrottle() {
		return thrusterThrottle;
	}

	public void setThrusterThrottle(double thrusterThrottle) {
		this.thrusterThrottle = thrusterThrottle;
	}

	public double getPrevAngularVelocity() {
		return prevAngularVelocity;
	}

	public void setPrevAngularVelocity(double prevAngularVelocity) {
		this.prevAngularVelocity = prevAngularVelocity;
	}

	public boolean isFlightMode() {
		return flightMode;
	}

	public void setFlightMode(boolean flightMode) {
		this.flightMode = flightMode;
	}

	public boolean isCruising() {
		return isCruising;
	}

	public void setCruising(boolean isCruising) {
		this.isCruising = isCruising;
	}

	public boolean isDampened() {
		return isDampened;
	}

	public void setDampened(boolean isDampened) {
		this.isDampened = isDampened;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public boolean isBounced() {
		return bounced;
	}

	public void setBounced(boolean bounced) {
		this.bounced = bounced;
	}

	public ArrayList<ShipComponent> getShipComponents() {
		return shipComponents;
	}

	public void setShipComponents(ArrayList<ShipComponent> shipComponents) {
		this.shipComponents = shipComponents;
	}

	public ArrayList<Thruster> getThrusters() {
		return thrusters;
	}

	public void setThrusters(ArrayList<Thruster> thrusters) {
		this.thrusters = thrusters;
	}

	public ArrayList<Thruster> getLeftStrafeThrusters() {
		return leftStrafeThrusters;
	}

	public void setLeftStrafeThrusters(ArrayList<Thruster> leftStrafeThrusters) {
		this.leftStrafeThrusters = leftStrafeThrusters;
	}

	public ArrayList<Thruster> getRightStrafeThrusters() {
		return rightStrafeThrusters;
	}

	public void setRightStrafeThrusters(ArrayList<Thruster> rightStrafeThrusters) {
		this.rightStrafeThrusters = rightStrafeThrusters;
	}

	public ArrayList<Thruster> getAccelThrusters() {
		return accelThrusters;
	}

	public void setAccelThrusters(ArrayList<Thruster> accelThrusters) {
		this.accelThrusters = accelThrusters;
	}

	public ArrayList<Thruster> getCruiseThrusters() {
		return cruiseThrusters;
	}

	public void setCruiseThrusters(ArrayList<Thruster> cruiseThrusters) {
		this.cruiseThrusters = cruiseThrusters;
	}

	public ArrayList<Thruster> getDeccelThrusters() {
		return deccelThrusters;
	}

	public void setDeccelThrusters(ArrayList<Thruster> deccelThrusters) {
		this.deccelThrusters = deccelThrusters;
	}

	public ArrayList<Thruster> getLeftTurnThrusters() {
		return leftTurnThrusters;
	}

	public void setLeftTurnThrusters(ArrayList<Thruster> leftTurnThrusters) {
		this.leftTurnThrusters = leftTurnThrusters;
	}

	public ArrayList<Thruster> getRightTurnThrusters() {
		return rightTurnThrusters;
	}

	public void setRightTurnThrusters(ArrayList<Thruster> rightTurnThrusters) {
		this.rightTurnThrusters = rightTurnThrusters;
	}

	public ArrayList<ReactionWheel> getReactionWheels() {
		return reactionWheels;
	}

	public void setReactionWheels(ArrayList<ReactionWheel> reactionWheels) {
		this.reactionWheels = reactionWheels;
	}

	public ArrayList<FuelTank> getFuelTanks() {
		return fuelTanks;
	}

	public void setFuelTanks(ArrayList<FuelTank> fuelTanks) {
		this.fuelTanks = fuelTanks;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public ArrayList<PowerSource> getPowerSources() {
		return powerSources;
	}

	public void setPowerSources(ArrayList<PowerSource> powerSources) {
		this.powerSources = powerSources;
	}

	public ArrayList<Battery> getBatteries() {
		return batteries;
	}

	public void setBatteries(ArrayList<Battery> batteries) {
		this.batteries = batteries;
	}

	public ArrayList<FuelTank> getUpperFuelTanks() {
		return upperFuelTanks;
	}

	public void setUpperFuelTanks(ArrayList<FuelTank> upperFuelTanks) {
		this.upperFuelTanks = upperFuelTanks;
	}

	public ArrayList<FuelTank> getMiddleFuelTanks() {
		return middleFuelTanks;
	}

	public void setMiddleFuelTanks(ArrayList<FuelTank> middleFuelTanks) {
		this.middleFuelTanks = middleFuelTanks;
	}

	public ArrayList<FuelTank> getLowerFuelTanks() {
		return lowerFuelTanks;
	}

	public void setLowerFuelTanks(ArrayList<FuelTank> lowerFuelTanks) {
		this.lowerFuelTanks = lowerFuelTanks;
	}

	public ArrayList<Thruster> getUpperThrusters() {
		return upperThrusters;
	}

	public void setUpperThrusters(ArrayList<Thruster> upperThrusters) {
		this.upperThrusters = upperThrusters;
	}

	public ArrayList<Thruster> getLowerThrusters() {
		return lowerThrusters;
	}

	public void setLowerThrusters(ArrayList<Thruster> lowerThrusters) {
		this.lowerThrusters = lowerThrusters;
	}

	public ArrayList<Weapon> getUpperWeapons() {
		return upperWeapons;
	}

	public void setUpperWeapons(ArrayList<Weapon> upperWeapons) {
		this.upperWeapons = upperWeapons;
	}

	public ArrayList<Weapon> getLowerWeapons() {
		return lowerWeapons;
	}

	public void setLowerWeapons(ArrayList<Weapon> lowerWeapons) {
		this.lowerWeapons = lowerWeapons;
	}

	public double getAirFrameMass() {
		return airFrameMass;
	}

	public void setBounds(ArrayList<CustomPolygon> bounds) {
		this.bounds = bounds;
	}
}
