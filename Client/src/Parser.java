import java.util.Arrays;

public class Parser {
    public static String parse(String tag, String str) {
        String result = str;
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        for (String s : Arrays.asList(startTag, endTag)) result = result.replace(s, "");
        return result;
    }
}