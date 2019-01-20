package cn.awumbuk.tool.bookconverter.formatter;

import java.io.FileInputStream;

/**
 * @author leo
 * @date 2019/1/20
 */
public interface FormatterInterface {
    /**
     * 从文件流加载
     * @param stream
     */
    void load(FileInputStream stream);
}
