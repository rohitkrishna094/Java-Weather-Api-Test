package com.example.demo;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

public class TestUtil {

    public static final String londonCorrectResponse = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"},{\"id\":301,\"main\":\"Drizzle\",\"description\":\"drizzle\",\"icon\":\"09n\"}],\"base\":\"stations\",\"main\":{\"temp\":16.11,\"pressure\":1014,\"humidity\":93,\"temp_min\":15,\"temp_max\":17},\"visibility\":10000,\"wind\":{\"speed\":3.1,\"deg\":220},\"clouds\":{\"all\":90},\"dt\":1537401000,\"sys\":{\"type\":1,\"id\":5093,\"message\":0.0052,\"country\":\"GB\",\"sunrise\":1537422191,\"sunset\":1537466626},\"id\":2643743,\"name\":\"London\",\"cod\":200}";
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    public static final List<String> keysToBeFound = Arrays.asList("base", "clouds", "cod", "coord", "dt", "id", "main", "name", "sys", "visibility", "weather", "wind");
    public static final String ChicagoCorrectResponse = "{\"coord\":{\"lon\":-87.62,\"lat\":41.88},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"base\":\"stations\",\"main\":{\"temp\":290.05,\"pressure\":1023,\"humidity\":59,\"temp_min\":288.75,\"temp_max\":290.95},\"visibility\":16093,\"wind\":{\"speed\":4.6,\"deg\":60},\"clouds\":{\"all\":5},\"dt\":1537656300,\"sys\":{\"type\":1,\"id\":1030,\"message\":0.0039,\"country\":\"US\",\"sunrise\":1537616336,\"sunset\":1537659959},\"id\":4887398,\"name\":\"Chicago\",\"cod\":200}";

    public static final String zipsCorrectContent = "62901\r\n" + "48335\r\n" + "90209\r\n" + "80201\r\n" + "32501\r\n" + "33124\r\n" + "32801\r\n" + "30301\r\n" + "96801\r\n" + "83254\r\n"
            + "60601\r\n" + "46201\r\n" + "52801\r\n" + "50301\r\n" + "67201\r\n" + "41701\r\n" + "70112\r\n" + "04032\r\n" + "21202\r\n" + "49036\r\n" + "";

    public static final String zipsWrongContent = "62901\r\n" + "48335\r\n" + "90209\r\n" + "80201\r\n" + "32501\r\n" + "33124\r\n" + "32801\r\n" + "30301\r\n" + "96801\r\n" + "83254\r\n"
            + "60601\r\n" + "46201\r\n" + "52801\r\n" + "50301\r\n" + "67201\r\n" + "41701\r\n" + "70112\r\n" + "04032\r\n" + "21202\r\n" + "49036\r\n" + "21202\r\n" + "";
}
