public class Wrapper {
    public static String wrap(String tag, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        //String result = "";
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        //result = startTag + str + endTag;
        stringBuilder.append(startTag).append(str).append(endTag);
        return stringBuilder.toString();
    }
}