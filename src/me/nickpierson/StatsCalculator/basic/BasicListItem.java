package me.nickpierson.StatsCalculator.basic;

public class BasicListItem {

	private String title;
	private Double result;

	public BasicListItem(String title, Double result) {
		this.setTitle(title);
		this.setAnswer(result);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAnswer() {
		return result;
	}

	public void setAnswer(Double answer) {
		this.result = answer;
	}
}
