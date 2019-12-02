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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hitBox.HitBox;
import physics.Vector;
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
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT: {
			tester.rotateLeft();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			tester.rotateRight();
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
		case KeyEvent.VK_SPACE: {
			tester.pauseUnpause();
			break;
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

class PhysicsBox extends JComponent implements Runnable {
	private static final long serialVersionUID = 1l;
	DObject[] objects = new DObject[100];
	public boolean paused = true;
	public int player;
	StandardShip playerShip = new StandardShip(new Vector(0, 0), new Vector(0, 0.0000001), 100);

	public PhysicsBox() {
		player = 0;
		for (int i = 0; i < objects.length; i++) {
			objects[i] = new StandardShip(new Vector(Ex.rand(0, Math.PI * 2), Ex.rand(0, 1000)),
					new Vector((Ex.rand(0, Math.PI * 2)), 1), 100);
		}
		objects[player] = playerShip;
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

	public void paint(Graphics g) {
		AffineTransform tx = new AffineTransform();
		tx.translate(this.getBounds().getCenterX(), this.getBounds().getCenterY());
		tx.rotate(-playerShip.getAngle() - Math.PI / 2);
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
		for (int i = 0; i < objects.length; i++) {
			if (!objects[i].equals(playerShip))
				objects[i].draw(ng);
		}
		playerShip.draw(ng);
		try {
			ng.transform(tx.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ng.drawChars(("" + playerShip.getAngularVelocity()).toCharArray(), 0,
				("" + playerShip.getAngularVelocity()).length(), 0, 10);
		ng.drawChars(("" + Math.round(playerShip.getX()) + "" + Math.round(playerShip.getY())).toCharArray(), 0,
				("" + Math.round(playerShip.getX()) + "" + Math.round(playerShip.getY())).length(), 0, 30);
	}

	public void run() {
		while (true) {
			while (!this.paused) {
				for (int i = 0; i < objects.length; i++) {
					objects[i].update();
				}
				repaint();
				Ex.timeout(0.0078125);
			}
			repaint();
		}
	}

}