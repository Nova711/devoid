package devoid_boosted;

import java.awt.BorderLayout;
import java.awt.Color;
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

class DrawTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DrawTest() {
		this.setSize(1200, 800);
		this.setVisible(true);
		JPanel pane = (JPanel) this.getContentPane();
		pane.add(new TesterShip(100, 100));
	}
}

class TesterShip extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x, y;

	public TesterShip(int x, int y) {
		this.x = x;
		this.y = y;
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.scale(3, 3);
		this.drawHull(0, 0, g);
		this.drawLeftWing(-4, -12, g);
		this.drawRightWing(-4, 12, g);
		this.drawLeftCanard(28, -8, g);
		this.drawRightCanard(28, 8, g);
		this.drawCockpit(0, 0, g);
		this.drawWeapon(36, 0, g);
		this.drawLeftMainEngine(-8, -8, g);
		this.drawRightMainEngine(-8, 8, g);
		this.drawLeftEnginePod(16, -44, g);
		this.drawRightEnginePod(16, 44, g);
		this.drawSmallLeftEngine(6, -46, g);
		this.drawSmallLeftEngine(6, 46, g);
		this.drawSmallRightEngine(6, -46, g);
		this.drawSmallRightEngine(6, 46, g);
	}

	void drawHull(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY.brighter());
		int[] xPoints = { 36, 40, 40, 36, -22, -22 };
		int[] yPoints = { -8, -4, 4, 8, 8, -8 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawLeftWing(int xOff, int yOff, Graphics g) {
		g.setColor(Color.gray);
		int[] xPoints = { 8, -8, 19, 14, 24 };
		int[] yPoints = { 0, 0, -27, -32, -32 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawRightWing(int xOff, int yOff, Graphics g) {
		g.setColor(Color.gray);
		int[] xPoints = { 8, -8, 19, 14, 24 };
		int[] yPoints = { 0, 0, 27, 32, 32 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawLeftCanard(int xOff, int yOff, Graphics g) {
		g.setColor(Color.gray);
		int[] xPoints = { -4, -4, 4 };
		int[] yPoints = { 0, -4, 0 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawRightCanard(int xOff, int yOff, Graphics g) {
		g.setColor(Color.gray);
		int[] xPoints = { -4, -4, 4 };
		int[] yPoints = { 0, 4, 0 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawCockpit(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { 4, 8, 8, 4, -8, -12, -12, -8 };
		int[] yPoints = { -6, -2, 2, 6, 6, 2, -2, -6 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawWeapon(int xOff, int yOff, Graphics g) {
		this.drawGunBarrel(xOff+4, yOff-1, g);
		this.drawGunBarrel(xOff+5, yOff, g);
		this.drawGunBarrel(xOff+5, yOff+1, g);
		this.drawGunBarrel(xOff+4, yOff+2, g);
		this.drawGunBarrel(xOff+3, yOff, g);
		this.drawGunBarrel(xOff+3, yOff+1, g);
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { -2, 4, 4, -2 };
		int[] yPoints = { -2, -2, 2, 2 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}
	
	void drawGunBarrel(int xOff, int yOff, Graphics g) {
		g.setColor(Color.gray);
		int[] xPoints = { 4, 4, 0, 0 };
		int[] yPoints = { -1, 0, 0, -1 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
		g.setColor(Color.black);
		int[] x1Points = { 1, 1, 0, 0 };
		int[] y1Points = { -1, 0, 0, -1 };
		p = new Polygon(x1Points, y1Points, xPoints.length);
		p.translate(xOff+4, yOff);
		this.fillPoly(p, g);
	}

	void drawLeftMainEngine(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { 14, 16, -6, -12, -20, -14, -12, -8 };
		int[] yPoints = { -4, 0, 0, 6, 6, 0, 0, -4 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawRightMainEngine(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { 14, 16, -6, -12, -20, -14, -12, -8 };
		int[] yPoints = { 4, 0, 0, -6, -6, 0, 0, 4 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawLeftEnginePod(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { 8, 12, -8, -10, -8 };
		int[] yPoints = { -4, 0, 0, -2, -4 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawRightEnginePod(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int[] xPoints = { 8, 12, -8, -10, -8 };
		int[] yPoints = { 4, 0, 0, 2, 4 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawSmallLeftEngine(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY.brighter());
		int[] xPoints = { 0, -6, -2, 2 };
		int[] yPoints = { 0, -6, -6, -2 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawSmallRightEngine(int xOff, int yOff, Graphics g) {
		g.setColor(Color.DARK_GRAY.brighter());
		int[] xPoints = { 0, -6, -2, 2 };
		int[] yPoints = { 0, 6, 6, 2 };
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		p.translate(xOff, yOff);
		this.fillPoly(p, g);
	}

	void drawPoly(Polygon p, Graphics g) {
		Polygon p2 = new Polygon(p.xpoints, p.ypoints, p.npoints);
		p2.translate(this.x, this.y);
		g.drawPolygon(p2);
	}

	void fillPoly(Polygon p, Graphics g) {
		Polygon p2 = new Polygon(p.xpoints, p.ypoints, p.npoints);
		p2.translate(this.x, this.y);
		g.fillPolygon(p2);
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
	// DObject[] objects = new DObject[50];
	public boolean paused = true;
	protected double scale = 1;
	protected int slowDown = 1;
	public int player;
	StandardShip playerShip = new StandardShip(new Vector(0, 0), new Vector(0, 0.0000001), 100);
	StandardGUI playerGUI = new StandardGUI();

	public PhysicsBox() {
		player = 0;
		for (int i = 0; i < 50; i++) {
			StandardMissile m1 = new StandardMissile(new Vector(Ex.rand(0, Math.PI * 2), Ex.rand(300, 1000)),
					new Vector(0, 0.000000001), 100);
			m1.setTarget(playerShip);
			objects.add(m1);
		}
		objects.add(playerShip);
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

	public void paint(Graphics g) {
		AffineTransform tx = new AffineTransform();
		tx.translate(this.getBounds().getCenterX(), this.getBounds().getCenterY());
		tx.rotate(-playerShip.getAngle() - Math.PI / 2);
		tx.scale(this.scale, this.scale);
		tx.translate(-this.getBounds().getCenterX(), -this.getBounds().getCenterY());
		tx.translate(-playerShip.getX() + this.getBounds().getCenterX(),
				-playerShip.getY() + this.getBounds().getCenterY());
		Graphics2D ng = (Graphics2D) g;
		ng.transform(tx);
		ng.setColor(Color.gray);
		for (int i = -100; i < 100; i++) {
			for (int j = -100; j < 100; j++) {
				if (i == 0 & j == 0) {
					ng.setColor(Color.blue);
				}
				ng.fillRect(i * 50, j * 50, 25, 25);
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
		try {
			ng.transform(tx.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerGUI.draw(playerShip, this.getBounds(), ng);
	}

	public void spawn(DObject obj) {
		this.objects.add(obj);
	}

	public void run() {
		while (true) {
			while (!this.paused) {
				for (int i = 0; i < objects.size(); i++) {
					objects.get(i).update();
				}
				repaint();
				Ex.timeout(0.0078125 * this.slowDown);
			}
			repaint();
		}
	}

}