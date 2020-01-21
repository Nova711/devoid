package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import in_out.Out;

public class FileThruster extends StandardThruster {
	public FileThruster(Vector position, double angle, String fileName, String direction) {
		this.setPosition(position);
		this.setAngle(angle);
		String line;
		int[] x = null, y = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("hp")) {
					this.setHP(Util.parseNextDouble(line));
				} else if (line.startsWith("mass")) {
					this.setMass(Util.parseNextDouble(line));
				} else if (line.startsWith("thrust")) {
					this.setMaxThrust(Util.parseNextDouble(line));
				} else if (line.startsWith("impulse")) {
					this.setSpecificImpulse(Util.parseNextDouble(line));
				} else if (line.startsWith("offset")) {
					this.setThrustOffset(Util.parseNextDouble(line) * Math.PI);
				} else if (line.startsWith("color")) {
					int[] rgb = Util.parseIntArray(line.substring(line.indexOf(" ")));
					this.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				} else if (line.startsWith("x ")) {
					x = Util.parseIntArray(line.substring(line.indexOf(" ")));
				} else if (line.startsWith("y ")) {
					y = Util.parseIntArray(line.substring(line.indexOf(" ")));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (direction.equals("R")) {
			y = Util.mirror(y);
			this.setThrustOffset(0 - this.getThrustOffset());
		}
		this.setBounds(new HitBox(0, 0, new Polygon(x, y, x.length)));
	}
}
