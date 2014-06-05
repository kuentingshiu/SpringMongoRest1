package hello;

import java.util.List;

public class ActivityWrapper {

	private List<Activity> activities;
	
	public ActivityWrapper(List<Activity> activities) {
		super();
		this.activities = activities;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	
}
