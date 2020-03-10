package devoid_boosted;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rift.Ex;
import rpg.Out;

public class ShipBuilder extends JFrame implements KeyListener {
	BuilderBox tester = new BuilderBox();

	/**
	 * 
	 */
	private static final long serialVersionUID = 7670221733703356862L;

	public ShipBuilder() {
		this.setSize(1200, 800);
		JPanel pane = (JPanel) this.getContentPane();
		this.setVisible(true);
		pane.add(tester, BorderLayout.CENTER);
		pane.setFocusable(true);
		pane.addKeyListener(this);
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: {
			tester.moveCameraUp();
			break;
		}
		case KeyEvent.VK_DOWN: {
			tester.moveCameraDown();
			break;
		}
		case KeyEvent.VK_LEFT: {
			tester.moveCameraLeft();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			tester.moveCameraRight();
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
		case KeyEvent.VK_W: {
			tester.moveUp();
			break;
		}
		case KeyEvent.VK_S: {
			tester.moveDown();
			break;
		}
		case KeyEvent.VK_A: {
			tester.moveLeft();
			break;
		}
		case KeyEvent.VK_D: {
			tester.moveRight();
			break;
		}
		case KeyEvent.VK_Q: {
			tester.rotateLeft();
			break;
		}
		case KeyEvent.VK_E: {
			tester.rotateRight();
			break;
		}
		case KeyEvent.VK_R: {
			tester.selectNext();
			break;
		}
		case KeyEvent.VK_F: {
			tester.selectPrev();
			break;
		}
		case KeyEvent.VK_C: {
			tester.toggleEditingLayer();
			break;
		}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

class BuilderBox extends JComponent {
	private static final long serialVersionUID = 1l;
	protected double scale = 1;
	StandardShip newShip = new FileShip(Util.src + "\\" + "MF38" + ".txt");
	Polygon newPolygon = new Polygon();
	ShipComponent newComponent = newShip.shipComponents.get(0);
	boolean isMirrored;
	boolean editingComp;
	double cameraX = 0;
	double cameraY = 0;
	int compIndex = 0;
	int vertexIndex = 0;

	public BuilderBox() {
		this.repaint();
	}

	public void moveCameraLeft() {
		this.cameraX--;
		this.repaint();
	}

	public void moveCameraRight() {
		this.cameraX++;
		this.repaint();
	}

	public void moveCameraUp() {
		this.cameraY--;
		this.repaint();
	}

	public void moveCameraDown() {
		this.cameraY++;
		this.repaint();
	}

	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		this.repaint();
	}

	public void moveLeft() {
		if (this.editingComp) {

		} else {
			this.newComponent.setPosition(this.newComponent.getPosition().add(Vector.fromXY(-1, 0)));
		}
		this.repaint();
	}

	public void moveRight() {
		if (this.editingComp) {

		} else {
			this.newComponent.setPosition(this.newComponent.getPosition().add(Vector.fromXY(1, 0)));
		}
		this.repaint();
	}

	public void moveUp() {
		if (this.editingComp) {

		} else {
			this.newComponent.setPosition(this.newComponent.getPosition().add(Vector.fromXY(0, -1)));
		}
		this.repaint();
	}

	public void moveDown() {
		if (this.editingComp) {

		} else {
			this.newComponent.setPosition(this.newComponent.getPosition().add(Vector.fromXY(0, 1)));
		}
		this.repaint();
	}

	public void rotateLeft() {
		if (this.editingComp) {

		} else {
			this.newComponent.setAngle(this.newComponent.getAngle() - Math.PI / 12);
		}
		this.repaint();
	}

	public void rotateRight() {
		if (this.editingComp) {

		} else {
			this.newComponent.setAngle(this.newComponent.getAngle() + Math.PI / 12);
		}
		this.repaint();
	}

	public void selectNext() {
		if (this.editingComp) {

		} else {
			compIndex = compIndex + 1 >= this.newShip.shipComponents.size() ? 0 : this.compIndex + 1;
			this.newComponent = newShip.shipComponents.get(compIndex);
		}
		this.repaint();
	}

	public void selectPrev() {
		if (this.editingComp) {

		} else {
			compIndex = compIndex - 1 < 0 ? this.newShip.shipComponents.size() - 1 : this.compIndex - 1;
			this.newComponent = newShip.shipComponents.get(compIndex);
		}
		this.repaint();
	}

	public void toggleEditingLayer() {
		this.editingComp = !this.editingComp;
		this.repaint();
	}

	public void reset() {

	}

	public void paint(Graphics g) {
		AffineTransform tx = new AffineTransform();
		tx.translate(this.getBounds().getCenterX(), this.getBounds().getCenterY());
		if (this.editingComp) {
			tx.rotate(-this.newComponent.getAngle());
		}
		tx.scale(this.scale, this.scale);
		tx.translate(-this.getBounds().getCenterX(), -this.getBounds().getCenterY());
		if (this.editingComp) {
			tx.translate(-this.newComponent.getX(), -this.newComponent.getY());
		}
		tx.translate(-this.cameraX + this.getBounds().getCenterX(), -this.cameraY + this.getBounds().getCenterY());
		Graphics2D ng = (Graphics2D) g;
		ng.transform(tx);
		if (this.editingComp) {
			this.newComponent.draw(ng);
		} else {
			newShip.draw(ng);
		}
		try {
			ng.transform(tx.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message;

		if (this.editingComp) {
			message = "";
		} else {
			message = "x" + Math.round(this.newComponent.getX()) + " y" + Math.round(this.newComponent.getY()) + " r"
					+ Math.round(this.newComponent.getAngle() / Math.PI * 12) % 24 + "pi/12 i" + this.compIndex;
		}
		Util.drawText(message, 10, 10, ng);
	}

}
