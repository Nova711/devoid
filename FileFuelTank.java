package devoid_boosted;

import java.awt.Color;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileFuelTank extends StandardFuelTank {
	public FileFuelTank(Vector position, double angle, String fileName, String direction, String color,
			String accentColor, PhysicsBox environment) {
		this.setPosition(position);
		this.setAngle(angle);
		ComponentReader cr = new ComponentReader(fileName, direction, environment);
		this.setTankMass(cr.getMass());
		this.setCapacity(cr.getCapacity());
		this.setBounds(cr.getBounds());
		this.setCurrentVolume(this.getCapacity());
	}

}
