package hello;

import java.util.List;

public class ItemWrapper {
	
	private List<Item> items;
	
	public ItemWrapper(List<Item> items) {
		super();
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	

}
