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

	public FileShip(String filePath, PhysicsBox environment) {
		this(new Vector(0, 0), filePath, environment);
	}

	public FileShip(Vector position, String filePath, PhysicsBox environment) {
		this.setEnvironment(environment);
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			String[] lines;
			PaintJob paint = new PaintJob();
			while ((line = br.readLine()) != null) {
				// Out.println(line);
				lines = line.substring(line.indexOf(" ") + 1).split(" ");
				if (line.startsWith("paintjob")) {
					paint = new PaintJob(Util.src.getAbsolutePath() + "\\paintJobs\\" + lines[0] + ".txt");
				} else if (line.startsWith("fuel")) {
					if (lines.length == 8) {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\fuelTanks\\" + lines[4] + ".txt", lines[5],
										lines[6], lines[7], this.getEnvironment(), paint));
					} else if (lines.length == 6) {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\fuelTanks\\" + lines[4] + ".txt", lines[5],
										"null", "null", this.getEnvironment(), paint));
					} else {
						this.fuelTanks.add(
								new FileFuelTank(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\fuelTanks\\" + lines[4] + ".txt", "L", "null",
										"null", this.getEnvironment(), paint));
					}
				} else if (line.startsWith("cockpit")) {
					if (lines.length == 8) {
						this.cockPit = new FileCockpit(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\cockpits\\" + lines[4] + ".txt", lines[5], lines[6],
								lines[7], this.getEnvironment(), paint);
					} else if (lines.length == 6) {
						this.cockPit = new FileCockpit(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\cockpits\\" + lines[4] + ".txt", lines[5], "null",
								"null", this.getEnvironment(), paint);
					} else {
						this.cockPit = new FileCockpit(FileShip.parseCoords(lines),
								Double.parseDouble(lines[3]) * Math.PI,
								Util.src.getAbsolutePath() + "\\cockpits\\" + lines[4] + ".txt", "L", "null", "null",
								this.getEnvironment(), paint);
					}
				} else if (line.startsWith("weapon")) {
					if (lines.length == 6) {
						this.weapons
								.add(new FileWeapon(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\weapons\\" + lines[4] + ".txt", lines[5],
										this.getEnvironment(), paint));
					} else {
						this.weapons
								.add(new FileWeapon(FileShip.parseCoords(lines), Double.parseDouble(lines[3]) * Math.PI,
										Util.src.getAbsolutePath() + "\\weapons\\" + lines[4] + ".txt", "L",
										this.getEnvironment(), paint));
					}
				} else if (line.startsWith("accel")) {
					this.accelThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("cruise")) {
					this.cruiseThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("deccel")) {
					this.deccelThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("leftStrafe")) {
					this.leftStrafeThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("rightStrafe")) {
					this.rightStrafeThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("leftTurn")) {
					this.leftTurnThrusters.add(this.parse(lines, paint));
				} else if (line.startsWith("rightTurn")) {
					this.rightTurnThrusters.add(this.parse(lines, paint));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.resetComponents();
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

	Thruster parse(String[] s, PaintJob paint) {
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
					Util.src.getAbsolutePath() + "\\thrusters\\" + s[4] + ".txt", s[5], s[6], s[7],
					this.getEnvironment(), paint);
		if (s.length == 6)
			return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
					Util.src.getAbsolutePath() + "\\thrusters\\" + s[4] + ".txt", s[5], "null", "null",
					this.getEnvironment(), paint);
		return new FileThruster(FileShip.parseCoords(s), Double.parseDouble(s[3]) * Math.PI,
				Util.src.getAbsolutePath() + "\\thrusters\\" + s[4] + ".txt", "L", "null", "null",
				this.getEnvironment(), paint);
	}

	static Vector parseCoords(String[] s) {
		if (s[0].equals("c")) {
			return Vector.fromXY(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		} else {
			return new Vector(Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		}
	}
}
