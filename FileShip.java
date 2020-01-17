package devoid_boosted;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileShip extends StandardShip {
	public FileShip(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			String[] lines;
			while ((line = br.readLine()) != null) {
				lines = line.substring(line.indexOf(" " + 1)).split(" ");
				if (line.startsWith("fuel")) {
					this.fuelTanks
							.add(new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]), lines[4]));
				} else if (line.startsWith("accel")) {
					this.accelThrusters.add(this.parse(lines));
				} else if (line.startsWith("deccel")) {
					this.deccelThrusters.add(this.parse(lines));
				} else if (line.startsWith("leftStrafe")) {
					this.leftStrafeThrusters.add(this.parse(lines));
				} else if (line.startsWith("rightStrafe")) {
					this.rightStrafeThrusters.add(this.parse(lines));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Thruster parse(String[] s) {
		switch (s[0]) {
		case "ac": {
			return this.accelThrusters.get(Integer.parseInt(s[1]));
		}
		case "dc": {
			return this.deccelThrusters.get(Integer.parseInt(s[1]));
		}
		case "ls": {
			return this.leftStrafeThrusters.get(Integer.parseInt(s[1]));
		}
		case "rs": {
			return this.rightStrafeThrusters.get(Integer.parseInt(s[1]));
		}
		}
		return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]), s[4]);
	}

	static Vector parseCoords(String[] s) {
		if (s[0].equals("c")) {
			return Vector.fromXY(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		} else {
			return new Vector(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		}
	}
}
