package autotests.payloads;

public class DuckPayload {

    public static String getDuckBody(String color, double height, String material, String sound, String wingsState) {
        return "{\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }

    public static String getDuckBodyWithIdMatcher(String color, double height, String material, String sound, String wingsState) {
        return "{\n" +
                "\"id\": \"@isNumber()@\",\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }
}
