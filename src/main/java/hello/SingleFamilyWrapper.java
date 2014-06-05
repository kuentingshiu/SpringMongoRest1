package hello;

public class SingleFamilyWrapper {
	
	private Family family;
	
	public SingleFamilyWrapper(Family family) {
		super();
		this.family = family;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}
	
	
}
