package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import in_out.Out;

public class CelestialBody extends StandardDObject {

	private double radius;
	private double atmosphereHeight;
	private double atmosphereDensity;
	private Color color;
	private Color accentColor;

	public CelestialBody() {
	}

	public CelestialBody(Vector position, double mass, double radius, double atmosphereHeight, double atmosphereDensity,
			PhysicsBox environment) {
		this(position, new Vector(0, 0), 1, 0, mass, 1, 0, 0, environment);
		this.radius = radius;
		this.atmosphereHeight = atmosphereHeight;
		this.atmosphereDensity = atmosphereDensity;
		this.setColor(new Color(0x228B22));
		this.setAccentColor(new Color(0x87CEEB));
	}

	public CelestialBody(Vector position, Vector velocity, double hp, double angle, double mass, double elasticity,
			double angularVelocity, double temperature, PhysicsBox environment) {
		super(position, velocity, hp, angle, mass, elasticity, angularVelocity, temperature, environment);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.translate(this.getX() * this.getEnvironment().getPixelsPerMetre(),
				this.getY() * this.getEnvironment().getPixelsPerMetre());
		ng.rotate(this.getAngle());
		ng.setColor(this.getAccentColor());
		ng.fillOval((int) -(this.radius + this.atmosphereHeight) * this.getEnvironment().getPixelsPerMetre(),
				(int) -(this.radius + this.atmosphereHeight) * this.getEnvironment().getPixelsPerMetre(),
				2 * (int) (this.radius + this.atmosphereHeight) * this.getEnvironment().getPixelsPerMetre(),
				2 * (int) (this.radius + this.atmosphereHeight) * this.getEnvironment().getPixelsPerMetre());
		ng.setColor(this.getColor());
		ng.fillOval((int) -this.radius * this.getEnvironment().getPixelsPerMetre(),
				(int) -this.radius * this.getEnvironment().getPixelsPerMetre(),
				2 * (int) this.radius * this.getEnvironment().getPixelsPerMetre(),
				2 * (int) this.radius * this.getEnvironment().getPixelsPerMetre());
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX() * this.getEnvironment().getPixelsPerMetre(),
				-this.getY() * this.getEnvironment().getPixelsPerMetre());
	}

	public void update() {
		if (this.getEnvironment() != null) {
			for (DObject obj : this.getEnvironment().objects) {
				if (!obj.equals(this)) {
					Vector relativeVelocity = obj.getVelocity().subtract(this.getVelocity());
					if (this.getPosition().subtract(obj.getPosition()).getMagnitude() > this.radius) {
						Vector force = Util.calculateGravity(this.getMass(), obj.getMass(), this.getPosition(),
								obj.getPosition());
						obj.applyForce(force.rotate(-obj.getAngle()), Vector.zero);
					} else if (this.getPosition().subtract(obj.getPosition()).getMagnitude() < this.radius
							+ this.atmosphereHeight) {
						Vector force = Util.calculateAirResistance(this.atmosphereDensity, 0.2, 50,
								relativeVelocity);
						//Out.println("A force of "+force.getMagnitude()+"N was applied");
						obj.applyForce(force.rotate(-obj.getAngle()), Vector.zero);
					}
					//Out.println("Relative velocity is "+relativeVelocity.getMagnitude()+"m/s");
				}
			}
		}
		this.setPosition(this.getPosition().add(this.getVelocity()));
		this.setAngle(this.getAngle() + this.getAngularVelocity());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getAccentColor() {
		return accentColor;
	}

	public void setAccentColor(Color accentColor) {
		this.accentColor = accentColor;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getAtmosphereHeight() {
		return atmosphereHeight;
	}

	public void setAtmosphereHeight(double atmosphereHeight) {
		this.atmosphereHeight = atmosphereHeight;
	}

	public double getAtmosphereDensity() {
		return atmosphereDensity;
	}

	public void setAtmosphereDensity(double atmosphereDensity) {
		this.atmosphereDensity = atmosphereDensity;
	}

}
