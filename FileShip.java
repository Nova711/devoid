package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import in_out.Out;

public class FileShip extends StandardShip {
	public FileShip(String filePath) {
		this(new Vector(0, 0), filePath);
	}

	public FileShip(Vector position, String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			String[] lines;
			while ((line = br.readLine()) != null) {
				//Out.println(line);
				lines = line.substring(line.indexOf(" ") + 1).split(" ");
				if (line.startsWith("fuel")) {
					if (lines.length == 6) {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", lines[5]));
					} else {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", "L"));
					}
				} else if (line.startsWith("cockpit")) {
					if (lines.length == 6) {
						this.cockPit = new FileCockpit(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", lines[5]);
					} else {
						this.cockPit = new FileCockpit(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\" + lines[4] + ".txt", "L");
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
		for (FuelTank f : this.fuelTanks) {
			this.shipComponents.add(f);
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
		this.shipComponents.add(this.getCockPit());
	}

	@Override
	public void update() {
		super.update();
		if (this.fuelTanks.size() > 1) {
			FuelTank f = this.fuelTanks.get(0);
			double drain = f.getFuelCapacity() - f.getFuel();
			if (drain > 0) {
				drain /= (this.fuelTanks.size() - 1);
				for (int i = 1; i < this.fuelTanks.size(); i++) {
					this.fuelTanks.get(i).transfer(f, drain);
				}
			}
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
		if (s.length == 6)
			return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
					Util.src.getAbsolutePath() + "\\" + s[4] + ".txt", s[5]);
		return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
				Util.src.getAbsolutePath() + "\\" + s[4] + ".txt", "L");
	}

	static Vector parseCoords(String[] s) {
		if (s[0].equals("c")) {
			return Vector.fromXY(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		} else {
			return new Vector(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		}
	}
}
