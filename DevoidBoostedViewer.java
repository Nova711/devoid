package devoid_boosted;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rift.Ex;
import rpg.Out;

@SuppressWarnings("unused")
public class DevoidBoostedViewer {

	public static void main(String[] args) {
		JFrame frame = new Interface();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

}

class Interface extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PhysicsBox tester = new PhysicsBox();
	private final Set<Integer> pressed = new HashSet<Integer>();

	public Interface() {
		Thread thread = new Thread(tester);
		this.setSize(1200, 800);
		JPanel pane = (JPanel) this.getContentPane();
		this.setVisible(true);
		pane.add(tester, BorderLayout.CENTER);
		pane.setFocusable(true);
		pane.addKeyListener(this);
		thread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		pressed.add(e.getKeyCode());
		if (pressed.size() > 0) {
			for (int keyCode : pressed) {
				switch (keyCode) {
				case KeyEvent.VK_LEFT: {
					tester.rotateLeft();
					break;
				}
				case KeyEvent.VK_RIGHT: {
					tester.rotateRight();
					break;
				}
				case KeyEvent.VK_UP: {
					tester.throttleUp();
					break;
				}
				case KeyEvent.VK_DOWN: {
					tester.throttleDown();
					break;
				}
				case KeyEvent.VK_W: {
					tester.accel();
					break;
				}
				case KeyEvent.VK_S: {
					tester.deccel();
					break;
				}
				case KeyEvent.VK_A: {
					tester.strafeLeft();
					break;
				}
				case KeyEvent.VK_D: {
					tester.strafeRight();
					break;
				}
				case KeyEvent.VK_F: {
					tester.toggleFlightMode();
					break;
				}
				case KeyEvent.VK_SPACE: {
					tester.toggleDampening();
					break;
				}
				case KeyEvent.VK_C: {
					tester.toggleCruise();
					break;
				}
				case KeyEvent.VK_P: {
					tester.pauseUnpause();
					break;
				}
				case KeyEvent.VK_EQUALS: {
					tester.setScale(tester.getScale() + 0.1);
					break;
				}
				case KeyEvent.VK_MINUS: {
					tester.setScale(tester.getScale() - 0.1);
					break;
				}
				case KeyEvent.VK_OPEN_BRACKET: {
					tester.slowDown();
					break;
				}
				case KeyEvent.VK_CLOSE_BRACKET: {
					tester.speedUp();
					break;
				}
				case KeyEvent.VK_ENTER: {
					tester.start();
					break;
				}
				case KeyEvent.VK_R: {
					tester.reset();
					break;
				}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

}

class PhysicsBox extends JComponent implements Runnable {
	private static final long serialVersionUID = 1l;
	ArrayList<DObject> objects = new ArrayList<DObject>();
	ArrayList<EventTrigger> events = new ArrayList<EventTrigger>();
	// DObject[] objects = new DObject[50];
	public boolean paused;
	public boolean atStart;
	protected double scale = 1;
	protected int slowDown = 1;
	public int player;
	StandardShip playerShip = new FileShip(Util.src + "\\ships\\" + "MF22594" + ".txt");
	// new StandardShip(new Vector(0, 0), new Vector(0, 0.0000001), 100);
	StandardGUI playerGUI = new StandardGUI();
	CelestialBody testPlanet = new CelestialBody(new Vector(0,2000), 5.972*Math.pow(10, 18), 1000, 100);

	public PhysicsBox() {
		testPlanet.setEnvironment(this);
		objects.add(testPlanet);
		player = 0;

		events.add(new EventTrigger(new Vector(0, 0), "Tap the w key to move forward"));
		events.add(new EventTrigger(new Vector(0, 400), "Tap the s key to stop\nTap the a key to move left"));
		events.add(new EventTrigger(Vector.fromXY(400, -400), "Tap the d key to stop\nTap the s key to move down"));
		events.add(new EventTrigger(Vector.fromXY(-400, -400), "Tap the w key to stop\nTap the d key to move right"));
		events.add(new EventTrigger(Vector.fromXY(-400, 400),
				"Tap the a key to stop\nUse the left and right arrow\nkeys to turn until the red\nline faces up then move forward"));
		events.add(new EventTrigger(new Vector(Math.PI / 4, 1000),
				"Deccelerate\nThe red line shows the location\nof your navpoint\nfollow it"));
		events.add(new EventTrigger(new Vector(3 * Math.PI / 4, 1500),
				"You can change your throttle\nwith the up and down arrow\nkeys"));
		objects.addAll(events);
		/*
		for (int i = 0; i < 50; i++) {
			StandardMissile m1 = new FileMissile(new Vector(Ex.rand(0, Math.PI * 2), Ex.rand(300, 1000)),
					Util.src + "\\" + "FE22" + ".txt"); // new StandardMissile(new Vector(Ex.rand(0, Math.PI
			// * 2), Ex.rand(300, 1000)), // new Vector(0, 0.000000001), 100);
			m1.setAngle(Math.PI * Math.random() * 2);
			m1.setTarget(playerShip);
			objects.add(m1);
		}*/
		Vector pos = new Vector(3 * Math.PI / 4, 1500);
		double x = pos.getX();
		double y = pos.getY();
		FileShip temp = new FileShip(Util.src + "\\ships\\" + "LF91A.txt");
		temp.setPosition(Vector.fromXY(x, y + 300));
		objects.add(temp);
		temp = new FileShip(Util.src + "\\ships\\" + "LT172.txt");
		temp.setPosition(Vector.fromXY(x - 100, y + 300));
		objects.add(temp);
		objects.add(playerShip);
		playerShip.setThrottle(15);
		this.atStart = true;
		playerShip.setEnvironment(this);
	}

	public void pauseUnpause() {
		if (this.paused)
			this.paused = false;
		else
			this.paused = true;
		// System.out.println("" + this.paused);
	}

	public void rotateLeft() {
		playerShip.rotateLeft();
	}

	public void rotateRight() {
		playerShip.rotateRight();
	}

	public void throttleUp() {
		playerShip.throttleUp();
	}

	public void throttleDown() {
		playerShip.throttleDown();
	}

	public void strafeLeft() {
		playerShip.strafeLeft();
	}

	public void strafeRight() {
		playerShip.strafeRight();
	}

	public void accel() {
		playerShip.accel();
	}

	public void deccel() {
		playerShip.deccel();
	}

	public void toggleFlightMode() {
		playerShip.toggleFlightMode();
	}

	public void toggleCruise() {
		playerShip.toggleCruise();
	}

	public void toggleDampening() {
		playerShip.toggleDampening();
	}

	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void slowDown() {
		this.slowDown++;
	}

	public void speedUp() {
		this.slowDown = this.slowDown > 1 ? this.slowDown - 1 : 1;
	}

	public void resetSpeed() {
		this.slowDown = 1;
	}

	public void start() {
		this.atStart = false;
	}

	public void reset() {
		for (EventTrigger e : this.events) {
			e.reset();
		}
		String shipName = "LF109";
		switch ((int) (Math.random() * 21)) {
		case 0: {
			shipName = "LF109";
			break;
		}
		case 1: {
			shipName = "MF66";
			break;
		}
		case 2: {
			shipName = "LF91A";
			break;
		}
		case 3: {
			shipName = "MB2";
			break;
		}
		case 4: {
			shipName = "MF140";
			break;
		}
		case 5: {
			shipName = "LT172";
			break;
		}
		case 6: {
			shipName = "MF70";
			break;
		}
		case 7: {
			shipName = "LF104";
			break;
		}
		case 8: {
			shipName = "LA55";
			break;
		}
		case 9: {
			shipName = "LF91";
			break;
		}
		case 10: {
			shipName = "MF38";
			break;
		}
		case 11: {
			shipName = "LF700";
			break;
		}
		case 12: {
			shipName = "LC617";
			break;
		}
		case 13: {
			shipName = "MF21315";
			break;
		}
		case 14: {
			shipName = "MF79";
			break;
		}
		case 15: {
			shipName = "MF18002";
			break;
		}
		case 17: {
			shipName = "MF86";
			break;
		}
		case 18: {
			shipName = "LF21315";
			break;
		}
		case 19: {
			shipName = "LF79";
			break;
		}
		case 20: {
			shipName = "MF22594";
			break;
		}
		}
		playerShip = new FileShip(Util.src + "\\ships\\" + shipName + ".txt");
		objects.add(playerShip);
		playerShip.setThrottle(15);
	}

	public void paint(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(Color.black);
		ng.fillRect(-1, -1, (int)this.getBounds().getWidth()+1, (int)this.getBounds().getHeight()+1);
		AffineTransform tx = new AffineTransform();
		tx.translate(this.getBounds().getCenterX(), this.getBounds().getCenterY());
		tx.rotate(-playerShip.getAngle() - Math.PI / 2);
		tx.scale(this.scale, this.scale);
		Vector cameraPos = this.playerShip.cockPit.getPosition().rotate(this.playerShip.getAngle());
		tx.translate(-this.getBounds().getCenterX(), -this.getBounds().getCenterY());
		tx.translate(-playerShip.getX() - cameraPos.getX() + this.getBounds().getCenterX(),
				-playerShip.getY() - cameraPos.getY() + this.getBounds().getCenterY());
		ng.transform(tx);
		ng.setColor(Color.gray);
		for (int i = -100; i < 100; i++) {
			for (int j = -100; j < 100; j++) {
				if (i == 0 & j == 0) {
					ng.setColor(Color.blue);
				}
				ng.fillRect(i * 48 - 12, j * 48 - 12, 24, 24);
				if (i == 0 & j == 0) {
					ng.setColor(Color.gray);
				}
			}
		}
		for (int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).equals(playerShip))
				objects.get(i).draw(ng);
		}
		playerShip.draw(ng);
		ng.setColor(Color.cyan);
		Vector forceLine = Util.calculateGravity(this.testPlanet.getMass(), this.playerShip.getMass(), this.testPlanet.getPosition(), this.playerShip.getPosition());
		ng.drawLine((int)playerShip.getX(), (int)playerShip.getY(), (int)(playerShip.getX() + forceLine.getX()), (int)(playerShip.getY() + forceLine.getY()));
		try {
			ng.transform(tx.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerGUI.draw(playerShip, this.getBounds(), ng);
		if (this.atStart) {
			ng.setColor(Color.black);
			ng.fillRect(0, 0, this.getBounds().width, this.getBounds().height);
			Font font = new Font("Arial", Font.BOLD, 30);
			ng.setFont(font);
			g.setColor(Color.white);
			Util.drawText("Controls", 10, 30, ng);
			Util.drawText("W - Strafe Forwards", 10, 70, ng);
			Util.drawText("A - Strafe Left", 10, 100, ng);
			Util.drawText("S - Strafe Backwards", 10, 130, ng);
			Util.drawText("D - Strafe Right", 10, 160, ng);
			Util.drawText("Up Arrow - Throttle Up", 10, 190, ng);
			Util.drawText("Down Arrow - Throttle Down", 10, 220, ng);
			Util.drawText("Left Arrow - Turn Left", 10, 250, ng);
			Util.drawText("Right Arrow - Turn Right", 10, 280, ng);
			Util.drawText("Spacebar - Toggle Dampener", 10, 310, ng);
			Util.drawText("R - Reset", 10, 340, ng);
			Util.drawText("Press Enter To Continue", 10, 400, ng);
		} else if (this.paused) {
			ng.setColor(Color.red);
			Font font = new Font("Arial", Font.BOLD, 30);
			ng.setFont(font);
			Util.drawText("Simulation Paused", (int) this.getBounds().getCenterX() - "Simulation Paused".length() * 8,
					(int) this.getBounds().getCenterY() - 100, ng);
		}
	}

	public void spawn(DObject obj) {
		this.objects.add(obj);
	}

	public void run() {
		while (true) {
			while (!this.paused & !this.atStart) {
				for (int i = 0; i < objects.size(); i++) {
					objects.get(i).update();
				}
				repaint();
				for (EventTrigger e : this.events)
					e.checkForTrigger(playerShip.getPosition());
				Ex.timeout(0.03125 * this.slowDown);
				for (int i = 0; i < this.events.size() - 1; i++) {
					if (this.events.get(i).isTriggered())
						this.playerShip.setNavPoint(this.events.get(i + 1).getPosition());
				}
			}
			repaint();
		}
	}

}