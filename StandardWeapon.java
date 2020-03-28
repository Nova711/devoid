package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardWeapon extends StandardShipComponent implements Weapon {
	private int projectileType;
	private double rateOfFire;
	private double reloadTime;
	private int clipSize;

	public StandardWeapon() {

	}

	public StandardWeapon(Vector position, double angle, PhysicsBox environment) {
		super(position, new Vector(0, 0), 5, angle, 5, 1, 0, 0, environment);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact(DObject obj, CollisionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hits(DObject objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getProjectileType() {
		return this.projectileType;
	}

	public void setProjectileType(int type) {
		this.projectileType = type;
	}

	@Override
	public int getSizeClass() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRateOfFire() {
		return this.reloadTime;
	}

	public void setRateOfFire(double rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	@Override
	public double getReloadTime() {
		return this.reloadTime;
	}

	public void setReloadTime(double time) {
		this.reloadTime = time;
	}

	@Override
	public int getClipSize() {
		return this.clipSize;
	}

	public void setClipSize(int size) {
		this.clipSize = size;
	}

	@Override
	public void fire(double angle) {
		this.getEnvironment()
				.spawn(new Bullet(this.getPosition(),
						new Vector(this.getVelocity().angle, this.getVelocity().getMagnitude() + 20),
						this.getAngle() + angle, this.getEnvironment()));
	}

}
