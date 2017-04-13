import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by Sophey on 4/13/17.
 */
public class GeoLocator {

  private String api_key = "";

  public GeoLocator() throws IOException {
    Properties properties = new Properties();
    String propFile = "config.properties";
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile);
    if (inputStream != null) {
      properties.load(inputStream);
    } else {
      throw new FileNotFoundException("property file '" + propFile + "' not found in classpath");
    }
    api_key = properties.getProperty("google_api_key");
  }

  private String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public String getStateFromLatLong(double lat, double lon) throws IOException, JSONException {
    InputStream is = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat +
        "," + lon + "&key=" + api_key).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      JSONArray results = json.getJSONArray("results").getJSONObject(0).getJSONArray
          ("address_components");
      for (int i = 0; i < results.length(); i++) {
        if (results.getJSONObject(i).getJSONArray("types").get(0).equals
            ("administrative_area_level_1")) {
          return results.getJSONObject(i).getString("short_name");
        }
      }
    } finally {
      is.close();
    }
    return "";
  }

  public static void main(String[] args) throws IOException {
    GeoLocator locator = new GeoLocator();
    System.out.println(locator.getStateFromLatLong(40.714224, -73.961452));
  }

}
