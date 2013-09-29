package me.nickpierson.StatsCalculator.basic.reference;

public class ReferenceListItem {

	private int imageId;
	private String title;

	public ReferenceListItem(int imageId, String title) {
		this.setImageId(imageId);
		this.setTitle(title);
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
