package devoid_boosted;

import java.awt.Color;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import in_out.Out;

public class ComponentReader {
	private double thrustOffset, specificImpulse, maxThrust, capacity, mass, HP;

	private String line;
	private ArrayList<CustomPolygon> tempBounds = new ArrayList<CustomPolygon>();
	private CustomPolygon currentCustPoly = new CustomPolygon();
	private Polygon tempPoly = new Polygon();
	private int[] x = null, y = null;
	private double[] tx, ty;
	private boolean legacy = true;
	private int[] ax;
	private int[] ay;
	private Color color, accentColor;

	public ComponentReader(String fileName, String direction, PhysicsBox environment) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine().trim()) != null) {
				if (line.equals("type scf")) {
					this.legacy = false;
				} else if (line.startsWith("hp")) {
					this.setHP(Util.parseNextDouble(line));
				} else if (line.startsWith("mass")) {
					this.setMass(Util.parseNextDouble(line));
				} else if (line.startsWith("capacity")) {
					this.setCapacity(Util.parseNextDouble(line));
				} else if (line.startsWith("thrust")) {
					this.setMaxThrust(Util.parseNextDouble(line));
				} else if (line.startsWith("impulse")) {
					this.setSpecificImpulse(Util.parseNextDouble(line));
				} else if (line.startsWith("offset")) {
					this.setThrustOffset(Util.parseNextDouble(line) * Math.PI);
				} else if (line.equals("poly {")) {
					currentCustPoly = new CustomPolygon();
					while ((line = br.readLine()) != null & !line.equals("}")) {
						if (line.equals("hole {")) {
							while ((line = br.readLine()) != null & !line.equals("}")) {
								if (line.startsWith("x")) {
									x = Util.parseIntArray(line.substring(line.indexOf(" ")));
								} else if (line.startsWith("y")) {
									y = Util.parseIntArray(line.substring(line.indexOf(" ")));
									if (direction.equals("R")) {
										y = Util.mirror(y);
									}
								}
							}
							for (int i = 0; i < x.length; i++) {
								x[i] *= environment.getPixelsPerMetre();
								y[i] *= environment.getPixelsPerMetre();
							}
							tempPoly = new Polygon(x, y, x.length);
							currentCustPoly.addHole(tempPoly);
						} else if (line.endsWith("body {")) {
							while ((line = br.readLine()) != null & !line.equals("}")) {
								if (line.startsWith("x")) {
									x = Util.parseIntArray(line.substring(line.indexOf(" ")));
								} else if (line.startsWith("y")) {
									y = Util.parseIntArray(line.substring(line.indexOf(" ")));
									if (direction.equals("R")) {
										y = Util.mirror(y);
									}
								}
							}
							for (int i = 0; i < x.length; i++) {
								x[i] *= environment.getPixelsPerMetre();
								y[i] *= environment.getPixelsPerMetre();
							}
							tempPoly = new Polygon(x, y, x.length);
							currentCustPoly.setBounds(tempPoly);
						} else if (line.startsWith("color")) {
							int[] rgb = Util.parseIntArray(line.substring(line.indexOf(" ")));
							currentCustPoly.setColor(new Color(rgb[0], rgb[1], rgb[2]));
						}
					}
					tempBounds.add(currentCustPoly);
				} else if (this.legacy) {
					if (line.startsWith("color")) {
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
			}
			if (this.legacy) {
				if (direction.equals("R")) {
					y = Util.mirror(y);
					ay = Util.mirror(ay);
					this.setThrustOffset(0 - this.getThrustOffset());
				}
				currentCustPoly = new CustomPolygon(x, y, this.color);
				tempBounds.add(currentCustPoly);
				if (this.accentColor != null) {
					currentCustPoly = new CustomPolygon(ax, ay, this.accentColor);
					tempBounds.add(currentCustPoly);
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

	private void setColor(Color color) {
		this.color = color;
	}

	private void setAccentColor(Color color) {
		this.accentColor = color;
	}

	public double getThrustOffset() {
		return thrustOffset;
	}

	private void setThrustOffset(double thrustOffset) {
		this.thrustOffset = thrustOffset;
	}

	public double getSpecificImpulse() {
		return specificImpulse;
	}

	private void setSpecificImpulse(double specificImpulse) {
		this.specificImpulse = specificImpulse;
	}

	public double getMaxThrust() {
		return maxThrust;
	}

	private void setMaxThrust(double maxThrust) {
		this.maxThrust = maxThrust;
	}

	public double getCapacity() {
		return capacity;
	}

	private void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getMass() {
		return mass;
	}

	private void setMass(double mass) {
		this.mass = mass;
	}

	public double getHP() {
		return HP;
	}

	private void setHP(double hP) {
		HP = hP;
	}

	public ArrayList<CustomPolygon> getBounds() {
		return tempBounds;
	}

}
