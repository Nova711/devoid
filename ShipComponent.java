package devoid_boosted;

public interface ShipComponent extends DObject {

	void attach(ShipComponent comp);
	
	void detach(ShipComponent comp);

	void attach();
	
	void detach();
	
	boolean isAttached();
}
