package devoid_boosted;

import java.awt.Color;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileWeapon extends StandardWeapon {
	public FileWeapon(Vector position, double angle, String fileName, String direction){
		this.setPosition(position);
		this.setAngle(angle);
		String line;
		int[] x = null, y = null, ax = null, ay = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("hp")) {
					this.setHP(Util.parseNextDouble(line));
				} else if (line.startsWith("mass")) {
					this.setMass(Util.parseNextDouble(line));
				} else if (line.startsWith("color")) {
					int[] rgb = Util.parseIntArray(line.substring(line.indexOf(" ")));
					this.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				} else if (line.startsWith("x ")) {
					x = Util.parseIntArray(line.substring(line.indexOf(" ")));
				} else if (line.startsWith("y ")) {
					y = Util.parseIntArray(line.substring(line.indexOf(" ")));
				} else if (line.startsWith("accentColor")) {
					int[] rgb = Util.parseIntArray(line.substring(line.indexOf(" ")));
					this.setAccentColor(new Color(rgb[0], rgb[1], rgb[2]));
				} else if (line.startsWith("ax ")) {
					ax = Util.parseIntArray(line.substring(line.indexOf(" ")));
				} else if (line.startsWith("ay ")) {
					ay = Util.parseIntArray(line.substring(line.indexOf(" ")));
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
			ay = Util.mirror(ay);
		}
		this.setBounds(new HitBox(0, 0, new Polygon(x, y, x.length)));
		if (ax != null & ay != null)
			this.setAccent(new HitBox(0, 0, new Polygon(ax, ay, ax.length)));
	}

}
