package devoid_boosted;

import java.util.ArrayList;

public class StandardShipComponent extends StandardDObject implements ShipComponent {

	private boolean isAttached = true;
	private ArrayList<ShipComponent> subcomponents = new ArrayList<ShipComponent>();

	public StandardShipComponent() {

	}

	public StandardShipComponent(Vector position, Vector velocity, double hp, double angle, double mass,
			double elasticity, double angularVelocity, double temperature, PhysicsBox environment) {
		super(position, velocity, hp, angle, mass, elasticity, angularVelocity, temperature, environment);
	}

	@Override
	public void attach(ShipComponent comp) {
		this.subcomponents.add(comp);
	}

	@Override
	public boolean checkForDestruction() {
		if (super.checkForDestruction())
			this.destruct();
		return super.checkForDestruction();
	}

	public void destruct() {
		for (ShipComponent comp : this.subcomponents) {
		}
	}

	@Override
	public void detach(ShipComponent comp) {
		if (this.subcomponents.contains(comp)) {
			this.subcomponents.remove(comp);
			this.getEnvironment().spawn(comp);
			comp.detach();
		}
	}

	@Override
	public boolean isAttached() {
		return this.isAttached;
	}

	@Override
	public void attach() {
		this.isAttached = true;
	}

	@Override
	public void detach() {
		this.isAttached = false;
	}

	@Override
	public void update() {
		if (!this.isAttached)
			super.update();
	}
}
