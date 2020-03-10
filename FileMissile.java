package devoid_boosted;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileMissile extends StandardMissile {

	public FileMissile(String filePath) {
		this(new Vector(0, 0), filePath);
	}

	public FileMissile(Vector position, String filePath) {
		this.setPosition(position);
		this.setThrottle(100);
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			String[] lines;
			while ((line = br.readLine()) != null) {
				// Out.println(line);
				lines = line.substring(line.indexOf(" ") + 1).split(" ");
				if (line.startsWith("fuel")) {
					if (lines.length == 8) {
						this.fuelTanks.add(new FileFuelTank(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", lines[5], lines[6], lines[7]));
					} else if (lines.length == 6) {
						this.fuelTanks.add(new FileFuelTank(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", lines[5], "null", "null"));
					} else {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", "L", "null", "null"));
					}
				} else if (line.startsWith("accel")) {
					this.accelThrusters.add(this.parse(lines));
				} else if (line.startsWith("deccel")) {
					this.deccelThrusters.add(this.parse(lines));
				} else if (line.startsWith("leftStrafe")) {
					this.leftStrafeThrusters.add(this.parse(lines));
				} else if (line.startsWith("rightStrafe")) {
					this.rightStrafeThrusters.add(this.parse(lines));
				} else if (line.startsWith("leftTurn")) {
					this.leftTurnThrusters.add(this.parse(lines));
				} else if (line.startsWith("rightTurn")) {
					this.rightTurnThrusters.add(this.parse(lines));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Thruster t : this.leftStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightStrafeThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.accelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.deccelThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.leftTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.rightTurnThrusters) {
			if (!this.thrusters.contains(t))
				this.thrusters.add(t);
		}
		for (Thruster t : this.thrusters) {
			this.shipComponents.add(t);
		}
		for (FuelTank f : this.fuelTanks) {
			this.shipComponents.add(f);
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
		if (s.length == 8)
			return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
					Util.src.getAbsolutePath() + "\\" + s[4] + ".txt", s[5], s[6], s[7]);
		if (s.length == 6)
			return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
					Util.src.getAbsolutePath() + "\\" + s[4] + ".txt", s[5], "null", "null");
		return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
				Util.src.getAbsolutePath() + "\\" + s[4] + ".txt", "L", "null", "null");
	}

}
