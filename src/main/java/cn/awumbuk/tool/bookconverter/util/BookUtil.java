package cn.awumbuk.tool.bookconverter.util;

/**
 * @author leo
 * @date 2019/1/21
 */
public class BookUtil {

    public static String getChapterTitle(int index) {
        StringBuilder builder = new StringBuilder();
        builder.append("第").append(index).append("章");
        return builder.toString();
    }
}
