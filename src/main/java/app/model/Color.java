package app.model;

public enum Color {
	
	GREEN, PINK, PURPLE;
	
	public String getString() {
		switch (this) {
		case GREEN: return "Green";
		case PINK: return "Pink";
		case PURPLE: return "Purple";
		default: return null;
		}
	}
	
	public static Color getColor(final String string) {
		if (string.equals("Green")) {
			return Color.GREEN;
		} else if (string.equals("Pink")) {
			return Color.PINK;
		} else if (string.equals("Purple")) {
			return Color.PURPLE;
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		switch (this) {
		case GREEN: return "Verde";
		case PINK: return "Rosa";
		case PURPLE: return "Roxo";
		default: return null;
		}
	}
	
}
