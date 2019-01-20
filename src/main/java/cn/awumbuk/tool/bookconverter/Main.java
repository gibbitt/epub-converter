package cn.awumbuk.tool.bookconverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author leo
 * @date 2019/1/20
 */
public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.info(helpText("book-converter"));
            return;
        }
        String fileName = args[0];
        handleBook(fileName);
    }

    /**
     *
     * @param name
     * @return
     */
    public static String helpText(String name) {
        return name + " /path/to/book/file";
    }

    /**
     *
     * @param fileName
     */
    public static void handleBook(String fileName) {
        logger.info("处理epub文件：{}", fileName);
        try {
            // 获取转换器实力
            BookConverter converter = BookConverter.getInstance();
            // 打开文件并读取流
            File file = FileUtils.getFile(fileName);
            String outFileName = file.getName() + ".txt";
            FileInputStream inputStream = FileUtils.openInputStream(file);
            // 加载文件数据
            converter.setData(inputStream, BookConverter.EPUB);
            converter.asTxt();
            // 写入文件
            logger.info("写入文件：{}", outFileName);
//            outputStream.
        } catch (IOException e) {
            logger.error("打开文件{}失败：", fileName, ExceptionUtils.getMessage(e));
        }
    }
}
