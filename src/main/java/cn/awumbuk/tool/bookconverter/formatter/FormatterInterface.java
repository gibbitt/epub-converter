package cn.awumbuk.tool.bookconverter.formatter;

import cn.awumbuk.tool.bookconverter.entity.Book;
import cn.awumbuk.tool.bookconverter.entity.ContentTable;

import java.io.FileInputStream;
import java.io.OutputStream;

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

    /**
     * 导入书籍
     * @param book
     */
    void load(Book book);

    /**
     * 解析
     * @return
     */
    Book parse();

    /**
     * 解析目录
     * @return
     */
    ContentTable parseContentTable(nl.siegmann.epublib.domain.Book book);

    /**
     *
     * @param outputStream
     * @return
     */
    boolean export(OutputStream outputStream);
}
