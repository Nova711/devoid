package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import in_out.Out;

public class CelestialBody extends StandardDObject {

	private double radius;
	private double atmosphereHeight;

	public CelestialBody() {
	}

	public CelestialBody(Vector position, double mass, double radius, double atmosphereHeight) {
		this(position, new Vector(0, 0), 1, 0, mass, 1, 0, 0);
		this.radius = radius;
		this.atmosphereHeight = atmosphereHeight;
		this.setColor(new Color(0x228B22));
		this.setAccentColor(new Color(0x87CEEB));
	}

	public CelestialBody(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature) {
		super(position, velocity, hp, angle, mass, elasticity, angularVelocity, temperature);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		ng.setColor(this.getAccentColor());
		ng.fillOval((int) -(this.radius + this.atmosphereHeight), (int) -(this.radius + this.atmosphereHeight),
				2 * (int) (this.radius + this.atmosphereHeight), 2 * (int) (this.radius + this.atmosphereHeight));
		ng.setColor(this.getColor());
		ng.fillOval((int) -this.radius, (int) -this.radius, 2 * (int) this.radius, 2 * (int) this.radius);
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

	public void update() {
		if (this.getEnvironment() != null) {
			for (DObject obj : this.getEnvironment().objects) {
				if (!obj.equals(this)) {
					if(this.getPosition().subtract(obj.getPosition()).getMagnitude() > this.radius) {
					Vector force = Util.calculateGravity(this.getMass(), obj.getMass(), this.getPosition(),
							obj.getPosition());
					obj.applyForce(force, Vector.zero);
					}else if(this.getPosition().subtract(obj.getPosition()).getMagnitude() < this.radius + this.atmosphereHeight) {
						obj.setVelocity(new Vector(obj.getVelocity().getAngle(), obj.getVelocity().getMagnitude() * 0.9));
					}
				}
			}
		}
		this.setPosition(this.getPosition().add(this.getVelocity()));
		this.setAngle(this.getAngle() + this.getAngularVelocity());
	}

}
