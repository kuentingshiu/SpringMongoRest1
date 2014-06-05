package hello;

import java.util.List;

// This is created because the client side need to wrap List<Family> into a new nested JSON
public class FamilyWrapper {
	
	private List<Family> family;

	
	public FamilyWrapper(List<Family> family) {
		super();
		this.family = family;
	}

	public List<Family> getFamily() {
		return family;
	}

	public void setFamily(List<Family> family) {
		this.family = family;
	}
	

}
