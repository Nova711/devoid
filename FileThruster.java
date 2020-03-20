package devoid_boosted;

public class FileThruster extends StandardThruster {
	public FileThruster(Vector position, double angle, String fileName, String direction, String color,
			String accentColor) {
		this.setPosition(position);
		this.setAngle(angle);
		ComponentReader cr = new ComponentReader(fileName, direction);
		this.setMass(cr.getMass());
		this.setMaxThrust(cr.getMaxThrust());
		this.setSpecificImpulse(cr.getSpecificImpulse());
		this.setThrustOffset(cr.getThrustOffset());
		this.setBounds(cr.getBounds());
	}
}
