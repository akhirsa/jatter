import java.util.Arrays;

public class Wrapper {
    public static String wrap(String tag, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        stringBuilder.append(startTag).append(str).append(endTag);
        return stringBuilder.toString();
    }
    public static String unwrap(String tag, String str) {
        String result = str;
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        for (String s : Arrays.asList(startTag, endTag)) result = result.replace(s, "");
        return result;
    }
}