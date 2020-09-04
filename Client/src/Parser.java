public class Parser {
    public static String parse(String tag, String str) {
        String result = str;
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        result = result.replace(startTag, "");
        result = result.replace(endTag, "");

        return result;
    }
}