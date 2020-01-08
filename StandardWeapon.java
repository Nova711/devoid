package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class StandardWeapon extends StandardDObject implements Weapon {

	public StandardWeapon(Vector position, double angle) {
		super(position, new Vector(0, 0), 5, angle, 5, 1, 0, 0);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void impact(DObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hits(DObject[] objects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(Color.black);
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		ng.drawRect(-2, -2, 4, 4);
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	@Override
	public int getProjectileType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSizeClass() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRateOfFire() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getReloadTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getClipSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fire(double angle) {
		this.getEnvironment().spawn(new Bullet(this.getPosition(),
				new Vector(this.getVelocity().angle, this.getVelocity().getMagnitude() + 20), this.getAngle() + angle));
	}

}
