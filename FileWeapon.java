package devoid_boosted;

import java.awt.Color;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileWeapon extends StandardWeapon {
	public FileWeapon(Vector position, double angle, String fileName, String direction, PhysicsBox environment, PaintJob paint){
		this.setPosition(position);
		this.setAngle(angle);
		ComponentReader cr = new ComponentReader(fileName, direction, environment, paint);
		this.setMass(cr.getMass());
		this.setBounds(cr.getBounds());
	}

}
