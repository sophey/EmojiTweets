
public class Tweet {
	
	private String text;
	private double latitude;
	private double longitude;

	public Tweet(String text, double latitude, double longitude) {
		this.text = text;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getText() {
		return text;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	
}
