package devoid_boosted;

public class Material {
	private double density;
	private double cohesiveness;
	private double[] specificHeatCapacity;
	private double latentHeatOfFusion;

	public Material(double density, double cohesiveness, double[] specificHeatCapacity, double latentHeatOfFusion) {
		this.density = density;
		this.cohesiveness = cohesiveness;
		this.specificHeatCapacity = specificHeatCapacity;
		this.latentHeatOfFusion = latentHeatOfFusion;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getCohesiveness() {
		return cohesiveness;
	}

	public double getCSolid() {
		return this.specificHeatCapacity[0];
	}

	public double getCLiquid() {
		return this.specificHeatCapacity[1];
	}

	public void setCohesiveness(double cohesiveness) {
		this.cohesiveness = cohesiveness;
	}

	public double getLatentHeatOfFusion() {
		return latentHeatOfFusion;
	}

	public void setLatentHeatOfFusion(double latentHeatOfFusion) {
		this.latentHeatOfFusion = latentHeatOfFusion;
	}

}
