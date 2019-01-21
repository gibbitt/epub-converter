package cn.awumbuk.tool.bookconverter;

import cn.awumbuk.tool.bookconverter.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author leo
 * @date 2019/1/20
 */
public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            logger.info(helpText("book-converter"));
            return;
        }
        String inPath  = args[0];
        String outPath = args[1];
        handleEpubBook(inPath, outPath);
    }

    /**
     *
     * @param name
     * @return
     */
    public static String helpText(String name) {
        return name + " /path/to/input/directory /path/to/output/directory";
    }

    /**
     *
     * @param fileName
     */
    public static void handleEpubBook(String inPath, String outPath) {
        logger.info("处理epub文件：{}/*.epub", inPath);
        try {
            // 获取转换器实例
            BookConverter converter = BookConverter.getInstance();
            // 打开文件并读取流
            List<File> files = getFileList(inPath);

            for (File epubFile: files) {
                FileInputStream inputStream = FileUtils.openInputStream(epubFile);
                // 加载文件数据
                converter.setData(inputStream, BookConverter.EPUB);
                String content = converter.asTxt();

                String outFileName = outPath + File.separator + FileUtil.getNameWithoutExension(epubFile.getName()) + ".txt";
                File outFile = FileUtils.getFile(outFileName);
                // 写入文件
                logger.info("写入文件：{}", outFileName);
                FileUtils.write(outFile, content);
            }

        } catch (Exception e) {
            logger.error("打开目录{}失败：", inPath, ExceptionUtils.getMessage(e));
        }
    }

    public static List<File> getFileList(String directory) {
        File inDirectory = FileUtils.getFile(directory);
        if (inDirectory.isDirectory()) {
            String[] extensions = {"epub"};
            Collection<File> fileList = FileUtils.listFiles(inDirectory, extensions, false);
            return new ArrayList<>(fileList);
        }
        return new ArrayList<>();
    }
}
