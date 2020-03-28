package devoid_boosted;

public class FileCockpit extends StandardCockpit {
	public FileCockpit(Vector position, double angle, String fileName, String direction, String color, String accentColor, PhysicsBox environment) {
		this.setPosition(position);
		this.setAngle(angle);
		ComponentReader cr = new ComponentReader(fileName, direction, environment);
		this.setMass(cr.getMass());
		this.setBounds(cr.getBounds());
	}

}
