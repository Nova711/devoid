package devoid_boosted;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import rift.Out;

public class PaintJob {
	private Color matteMetalLight;
	private Color matteMetalMedium;
	private Color matteMetalDark;
	private Color matteMetalDarker;
	private Color shinyMetalLight;
	private Color shinyMetalMedium;
	private Color shinyMetalDark;
	private Color shinyMetalDarker;
	private Color matteCompositeLight;
	private Color matteCompositeMedium;
	private Color matteCompositeDark;
	private Color matteCompositeDarker;
	private Color shinyCompositeLight;
	private Color shinyCompositeMedium;
	private Color shinyCompositeDark;
	private Color shinyCompositeDarker;
	private Color frostedGlassLight;
	private Color frostedGlassMedium;
	private Color frostedGlassDark;
	private Color frostedGlassDarker;
	private Color clearGlassLight;
	private Color clearGlassMedium;
	private Color clearGlassDark;
	private Color clearGlassDarker;

	public PaintJob() {
		this(Util.src.getAbsolutePath() + "\\paintJobs\\default.txt");
	}

	public PaintJob(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				//Out.println(line);
				this.setColor(line.substring(0, line.indexOf("=")-1).trim(),
						this.parseColor(line.substring(line.indexOf("=")+1).trim()));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Color parseColor(String color) throws IOException {
		Color temp = null;
		if (color.contains(",")) {
			temp = Util.parseColor(color);
		} else {
			try (BufferedReader br = new BufferedReader(
					new FileReader(Util.src.getAbsolutePath() + "\\defaultColors.txt"))) {
				String line;
				while ((line = br.readLine()) != null) {
					if (line.startsWith(color)) {
						line = line.substring(line.indexOf("=")+1).trim();
						if (line.startsWith("0x")) {
							temp = new Color(Integer.decode(line));
						} else {
							int[] rgb = Util.parseIntArray(line.substring(line.indexOf("=")).trim());
							if (rgb.length == 3)
								temp = new Color(rgb[0], rgb[1], rgb[2]);
							else
								temp = new Color(rgb[0], rgb[1], rgb[2], rgb[3]);
							break;
						}
					}
				}
			}
		}
		return temp;
	}

	public Color getColor(String colorName) {
		switch (colorName) {
		case "matteMetalLight": {
			return this.getMatteMetalLight();
		}
		case "matteMetalMedium": {
			return this.getMatteMetalMedium();
		}
		case "matteMetalDark": {
			return this.getMatteMetalDark();
		}
		case "matteMetalDarker": {
			return this.getMatteMetalDarker();
		}
		case "shinyMetalLight": {
			return this.getShinyMetalLight();
		}
		case "shinyMetalMedium": {
			return this.getShinyMetalMedium();
		}
		case "shinyMetalDark": {
			return this.getShinyMetalDark();
		}
		case "shinyMetalDarker": {
			return this.getShinyMetalDarker();
		}
		case "matteCompositeLight": {
			return this.getMatteCompositeLight();
		}
		case "matteCompositeMedium": {
			return this.getMatteCompositeMedium();
		}
		case "matteCompositeDark": {
			return this.getMatteCompositeDark();
		}
		case "matteCompositeDarker": {
			return this.getMatteCompositeDarker();
		}
		case "shinyCompositeLight": {
			return this.getShinyCompositeLight();
		}
		case "shinyCompositeMedium": {
			return this.getShinyCompositeMedium();
		}
		case "shinyCompositeDark": {
			return this.getShinyCompositeDark();
		}
		case "shinyCompositeDarker": {
			return this.getShinyCompositeDarker();
		}
		case "frostedGlassLight": {
			return this.getFrostedGlassLight();
		}
		case "frostedGlassMedium": {
			return this.getFrostedGlassMedium();
		}
		case "frostedGlassDark": {
			return this.getFrostedGlassDark();
		}
		case "frostedGlassDarker": {
			return this.getFrostedGlassDarker();
		}
		case "clearGlassLight": {
			return this.getClearGlassLight();
		}
		case "clearGlassMedium": {
			return this.getClearGlassMedium();
		}
		case "clearGlassDark": {
			return this.getClearGlassDark();
		}
		case "clearGlassDarker": {
			return this.getClearGlassDarker();
		}
		}
		return null;
	}
	
	public void setColor(String colorName, Color color) {
		switch (colorName) {
		case "matteMetalLight": {
			this.setMatteMetalLight(color);
			break;
		}
		case "matteMetalMedium": {
			this.setMatteMetalMedium(color);
			break;
		}
		case "matteMetalDark": {
			this.setMatteMetalDark(color);
			break;
		}
		case "matteMetalDarker": {
			this.setMatteMetalDarker(color);
			break;
		}
		case "shinyMetalLight": {
			this.setShinyMetalLight(color);
			break;
		}
		case "shinyMetalMedium": {
			this.setShinyMetalMedium(color);
			break;
		}
		case "shinyMetalDark": {
			this.setShinyMetalDark(color);
			break;
		}
		case "shinyMetalDarker": {
			this.setShinyMetalDarker(color);
			break;
		}
		case "matteCompositeLight": {
			this.setMatteCompositeLight(color);
			break;
		}
		case "matteCompositeMedium": {
			this.setMatteCompositeMedium(color);
			break;
		}
		case "matteCompositeDark": {
			this.setMatteCompositeDark(color);
			break;
		}
		case "matteCompositeDarker": {
			this.setMatteCompositeDarker(color);
			break;
		}
		case "shinyCompositeLight": {
			this.setShinyCompositeLight(color);
			break;
		}
		case "shinyCompositeMedium": {
			this.setShinyCompositeMedium(color);
			break;
		}
		case "shinyCompositeDark": {
			this.setShinyCompositeDark(color);
			break;
		}
		case "shinyCompositeDarker": {
			this.setShinyCompositeDarker(color);
			break;
		}
		case "frostedGlassLight": {
			this.setFrostedGlassLight(color);
			break;
		}
		case "frostedGlassMedium": {
			this.setFrostedGlassMedium(color);
			break;
		}
		case "frostedGlassDark": {
			this.setFrostedGlassDark(color);
			break;
		}
		case "frostedGlassDarker": {
			this.setFrostedGlassDarker(color);
			break;
		}
		case "clearGlassLight": {
			this.setClearGlassLight(color);
			break;
		}
		case "clearGlassMedium": {
			this.setClearGlassMedium(color);
			break;
		}
		case "clearGlassDark": {
			this.setClearGlassDark(color);
			break;
		}
		case "clearGlassDarker": {
			this.setClearGlassDarker(color);
			break;
		}
		}
	}

	public Color getMatteMetalLight() {
		return matteMetalLight;
	}

	public void setMatteMetalLight(Color matteMetalLight) {
		this.matteMetalLight = matteMetalLight;
	}

	public Color getMatteMetalMedium() {
		return matteMetalMedium;
	}

	public void setMatteMetalMedium(Color matteMetalMedium) {
		this.matteMetalMedium = matteMetalMedium;
	}

	public Color getMatteMetalDark() {
		return matteMetalDark;
	}

	public void setMatteMetalDark(Color matteMetalDark) {
		this.matteMetalDark = matteMetalDark;
	}

	public Color getMatteMetalDarker() {
		return matteMetalDarker;
	}

	public void setMatteMetalDarker(Color matteMetalDarker) {
		this.matteMetalDarker = matteMetalDarker;
	}

	public Color getShinyMetalLight() {
		return shinyMetalLight;
	}

	public void setShinyMetalLight(Color shinyMetalLight) {
		this.shinyMetalLight = shinyMetalLight;
	}

	public Color getShinyMetalMedium() {
		return shinyMetalMedium;
	}

	public void setShinyMetalMedium(Color shinyMetalMedium) {
		this.shinyMetalMedium = shinyMetalMedium;
	}

	public Color getShinyMetalDark() {
		return shinyMetalDark;
	}

	public void setShinyMetalDark(Color shinyMetalDark) {
		this.shinyMetalDark = shinyMetalDark;
	}

	public Color getShinyMetalDarker() {
		return shinyMetalDarker;
	}

	public void setShinyMetalDarker(Color shinyMetalDarker) {
		this.shinyMetalDarker = shinyMetalDarker;
	}

	public Color getMatteCompositeLight() {
		return matteCompositeLight;
	}

	public void setMatteCompositeLight(Color matteCompositeLight) {
		this.matteCompositeLight = matteCompositeLight;
	}

	public Color getMatteCompositeMedium() {
		return matteCompositeMedium;
	}

	public void setMatteCompositeMedium(Color matteCompositeMedium) {
		this.matteCompositeMedium = matteCompositeMedium;
	}

	public Color getMatteCompositeDark() {
		return matteCompositeDark;
	}

	public void setMatteCompositeDark(Color matteCompositeDark) {
		this.matteCompositeDark = matteCompositeDark;
	}

	public Color getMatteCompositeDarker() {
		return matteCompositeDarker;
	}

	public void setMatteCompositeDarker(Color matteCompositeDarker) {
		this.matteCompositeDarker = matteCompositeDarker;
	}

	public Color getShinyCompositeLight() {
		return shinyCompositeLight;
	}

	public void setShinyCompositeLight(Color shinyCompositeLight) {
		this.shinyCompositeLight = shinyCompositeLight;
	}

	public Color getShinyCompositeMedium() {
		return shinyCompositeMedium;
	}

	public void setShinyCompositeMedium(Color shinyCompositeMedium) {
		this.shinyCompositeMedium = shinyCompositeMedium;
	}

	public Color getShinyCompositeDark() {
		return shinyCompositeDark;
	}

	public void setShinyCompositeDark(Color shinyCompositeDark) {
		this.shinyCompositeDark = shinyCompositeDark;
	}

	public Color getShinyCompositeDarker() {
		return shinyCompositeDarker;
	}

	public void setShinyCompositeDarker(Color shinyCompositeDarker) {
		this.shinyCompositeDarker = shinyCompositeDarker;
	}

	public Color getFrostedGlassLight() {
		return frostedGlassLight;
	}

	public void setFrostedGlassLight(Color frostedGlassLight) {
		this.frostedGlassLight = frostedGlassLight;
	}

	public Color getFrostedGlassMedium() {
		return frostedGlassMedium;
	}

	public void setFrostedGlassMedium(Color frostedGlassMedium) {
		this.frostedGlassMedium = frostedGlassMedium;
	}

	public Color getFrostedGlassDark() {
		return frostedGlassDark;
	}

	public void setFrostedGlassDark(Color frostedGlassDark) {
		this.frostedGlassDark = frostedGlassDark;
	}

	public Color getFrostedGlassDarker() {
		return frostedGlassDarker;
	}

	public void setFrostedGlassDarker(Color frostedGlassDarker) {
		this.frostedGlassDarker = frostedGlassDarker;
	}

	public Color getClearGlassLight() {
		return clearGlassLight;
	}

	public void setClearGlassLight(Color clearGlassLight) {
		this.clearGlassLight = clearGlassLight;
	}

	public Color getClearGlassMedium() {
		return clearGlassMedium;
	}

	public void setClearGlassMedium(Color clearGlassMedium) {
		this.clearGlassMedium = clearGlassMedium;
	}

	public Color getClearGlassDark() {
		return clearGlassDark;
	}

	public void setClearGlassDark(Color clearGlassDark) {
		this.clearGlassDark = clearGlassDark;
	}

	public Color getClearGlassDarker() {
		return clearGlassDarker;
	}

	public void setClearGlassDarker(Color clearGlassDarker) {
		this.clearGlassDarker = clearGlassDarker;
	}

}
