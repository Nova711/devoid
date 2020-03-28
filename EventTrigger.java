package devoid_boosted;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class EventTrigger extends StandardDObject {
	private String message = "Test";
	private boolean triggered;

	public EventTrigger() {
	}

	public EventTrigger(Vector position, CustomPolygon bounds, String message, PhysicsBox environment) {
		super(position, new Vector(0, 0), 0, Math.PI / 2, 0, 0, 0, 0, environment);
		this.setBounds(bounds);
		this.message = message;
	}

	public EventTrigger(Vector position, String message, PhysicsBox environment) {
		super(position, new Vector(0, 0), 0, Math.PI / 2, 0, 0, 0, 0, environment);
		int[] x = { 200, -200, -200, 200 };
		int[] y = { -200, -200, 200, 200 };
		this.setBounds(new CustomPolygon(x, y, Color.white));
		this.message = message;
	}

	public void checkForTrigger(Vector position) {
		if (this.getBoundingPolygon().contains(
				(position.getX() - this.getPosition().getX()) * this.getEnvironment().getPixelsPerMetre(),
				(position.getY() - this.getPosition().getY()) * this.getEnvironment().getPixelsPerMetre()))
			this.trigger();
	}

	public boolean isTriggered() {
		return this.triggered;
	}

	public void trigger() {
		this.triggered = true;
	}

	public void reset() {
		this.triggered = false;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.translate(this.getX() * this.getEnvironment().getPixelsPerMetre(),
				this.getY() * this.getEnvironment().getPixelsPerMetre());
		ng.rotate(this.getAngle());
		for (CustomPolygon p : this.getBounds())
			p.draw(ng);
		ng.setColor(Color.black);
		if (this.triggered) {
			ng.setColor(Color.black);
			Font font = new Font("Arial", Font.BOLD, 20);
			ng.setFont(font);
			int y = 0;
			String[] messages = this.message.split("\n");
			for (String s : messages) {
				Util.drawText(s, -180, -180 + y, ng);
				y += 30;
			}
		}
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX() * this.getEnvironment().getPixelsPerMetre(),
				-this.getY() * this.getEnvironment().getPixelsPerMetre());
	}

}
