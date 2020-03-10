package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardMissile extends StandardShip {

	protected double fieldOfView;

	protected DObject target = null;

	public StandardMissile() {

	}

	public StandardMissile(Vector position, Vector velocity, double mass) {
		super(position, new Vector(0, 0), 5, 0, mass, 1, 0, 0);
		this.setAirFrameMass(mass);
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-15, 5), 0, 1200, 90));
		this.accelThrusters.add(new StandardThruster(Vector.fromXY(-15, -5), 0, 1200, 90));
		this.leftTurnThrusters.add(this.accelThrusters.get(0));
		this.rightTurnThrusters.add(this.accelThrusters.get(1));
		this.fuelTanks.add(new StandardFuelTank(new Vector(0, 0)));

		for (Thruster t : this.leftStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.accelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.deccelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.leftTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.thrusters) {
			this.shipComponents.add(t);
		}
		for (FuelTank f : this.fuelTanks) {
			this.shipComponents.add(f);
		}
		this.setThrottle(50);
		this.setMass(this.getMass());
	}

	@Override
	public void update() {
		if (target != null) {
			Vector targetVector = Vector.fromXY(target.getX() - this.getX(), target.getY() - this.getY());
			double anglevr = 0;
			for (Thruster t : this.rightTurnThrusters) {
				anglevr += this.getAngularAcceleration(t);
			}
			double anglevl = 0;
			for (Thruster t : this.leftTurnThrusters) {
				anglevl += this.getAngularAcceleration(t);
			}
			if (Math.abs(this.getAngle() - targetVector.getAngle()) > 3 * Math.PI / 4) {
				if (this.getAngle() > targetVector.getAngle() & this.getAngularVelocity() < anglevl * 4) {
					this.rotateLeft();
				} else if (this.getAngle() < targetVector.getAngle() & this.getAngularVelocity() < anglevr * 4) {
					this.rotateRight();
				} else if (this.getAngularVelocity() > anglevl * 5) {
					this.rotateLeft();
				} else if (this.getAngularVelocity() < anglevr * 5) {
					this.rotateRight();
				}
			} else if (Math.abs(this.getAngle() - targetVector.getAngle()) > Math.PI / 2) {
				if (this.getAngle() > targetVector.getAngle() & this.getAngularVelocity() < anglevl * 3) {
					this.rotateLeft();
				} else if (this.getAngle() < targetVector.getAngle() & this.getAngularVelocity() < anglevr * 3) {
					this.rotateRight();
				} else if (this.getAngularVelocity() > anglevl * 4) {
					this.rotateLeft();
				} else if (this.getAngularVelocity() < anglevr * 4) {
					this.rotateRight();
				}

			} else if (Math.abs(this.getAngle() - targetVector.getAngle()) > Math.PI / 4) {
				if (this.getAngle() > targetVector.getAngle() & this.getAngularVelocity() < anglevl * 2) {
					this.rotateLeft();
				} else if (this.getAngle() < targetVector.getAngle() & this.getAngularVelocity() < anglevr * 2) {
					this.rotateRight();
				} else if (this.getAngularVelocity() > anglevl * 3) {
					this.rotateLeft();
				} else if (this.getAngularVelocity() < anglevr * 3) {
					this.rotateRight();
				}

			} else if (Math.abs(this.getAngle() - targetVector.getAngle()) > Math.PI / 12) {
				if (this.getAngle() > targetVector.getAngle() & this.getAngularVelocity() < anglevl * 1) {
					this.rotateLeft();
				} else if (this.getAngle() < targetVector.getAngle() & this.getAngularVelocity() < anglevr * 1) {
					this.rotateRight();
				} else if (this.getAngularVelocity() > anglevl * 2) {
					this.rotateLeft();
				} else if (this.getAngularVelocity() < anglevr * 2) {
					this.rotateRight();
				}

			} else if (Math.abs(this.getAngle() - targetVector.getAngle()) > Math.PI / 24) {
				if (this.getAngle() > targetVector.getAngle() & this.getAngularVelocity() < anglevl * 1) {
					this.rotateLeft();
				} else if (this.getAngle() < targetVector.getAngle() & this.getAngularVelocity() < anglevr * 1) {
					this.rotateRight();
				} else if (this.getAngularVelocity() > anglevl) {
					this.rotateLeft();
				} else if (this.getAngularVelocity() > anglevr) {
					this.rotateRight();
				}

			} else {
				// this.thrusterThrottle = 100;
				this.accel();
			}
		}
		super.update();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(Color.black);
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		//ng.drawRect(-20, -10, 40, 20);
		for (ShipComponent s : shipComponents)
			s.draw(ng);
		ng.rotate(-this.getAngle());
		Vector temp = new Vector(this.getAngle(), 2);
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.green);
		// temp = Vector.fromXY(target.getX()-this.getX(), target.getY()-this.getY());
		// g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.red);
		// temp = new Vector(this.thrust.getAngle() + this.getAngle(),
		// this.thrust.getMagnitude());
		g.drawLine(0, 0, (int) (temp.getX() * 5), (int) (temp.getY() * 5));
		g.setColor(Color.blue);
		temp = new Vector(this.getVelocity().getAngle(), this.getVelocity().getMagnitude());
		g.drawLine(0, 0, (int) (temp.getX() * 25), (int) (temp.getY() * 25));
		ng.translate(-this.getX(), -this.getY());
	}

	void setTarget(DObject target) {
		this.target = target;
	}

	public void rotateLeft() {
		// this.thrusterThrottle = 5;
		super.rotateLeft();
	}

	public void rotateRight() {
		// this.thrusterThrottle = 5;
		super.rotateRight();
	}

}
