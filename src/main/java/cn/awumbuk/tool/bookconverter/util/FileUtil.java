package cn.awumbuk.tool.bookconverter.util;

/**
 * @author leo
 * @date 2019/1/22
 */
public class FileUtil {

    public static String getExtension(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }

    public static String getNameWithoutExension(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }
}
