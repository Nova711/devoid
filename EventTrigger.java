package devoid_boosted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class EventTrigger extends StandardDObject {
	private String message = "Test";
	private boolean triggered;

	public EventTrigger() {
	}

	public EventTrigger(Vector position, String message) {
		super(position, new Vector(0, 0), 0, Math.PI / 2, 0, 0, 0, 0);
		this.setColor(Color.black);
		int[] x = { 100, -100, -100, 100 };
		int[] y = { -100, -100, 100, 100 };
		this.setBounds(new HitBox(0, 0, new Polygon(x, y, x.length)));
		this.message = message;
	}

	public EventTrigger(Vector position, Polygon bounds, String message) {
		super(position, new Vector(0, 0), 0, Math.PI / 2, 0, 0, 0, 0);
		this.setColor(Color.black);
		this.setBounds(new HitBox(0, 0, bounds));
		this.message = message;
	}

	public void checkForTrigger(Vector position) {
		if (this.getBounds().getBounds().contains(position.getX() - this.getPosition().getX(),
				position.getY() - this.getPosition().getY()))
			this.trigger();
	}

	public boolean isTriggered() {
		return this.triggered;
	}

	public void trigger() {
		this.triggered = true;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics g) {
		Graphics2D ng = (Graphics2D) g;
		ng.setColor(this.getColor());
		ng.translate(this.getX(), this.getY());
		ng.rotate(this.getAngle());
		this.getBounds().draw(ng);
		ng.setColor(Color.white);
		if (this.triggered) {
			ng.setColor(Color.lightGray);
			int y = 0;
			String[] messages = this.message.split("\n");
			for (String s : messages) {
				Util.drawText(s, -90, -90 + y, ng);
				y += 10;
			}
		}
		ng.rotate(-this.getAngle());
		ng.translate(-this.getX(), -this.getY());
	}

}