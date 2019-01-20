package cn.awumbuk.tool.bookconverter.formatter;

import cn.awumbuk.tool.bookconverter.entity.Book;
import cn.awumbuk.tool.bookconverter.entity.ContentTable;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @author leo
 * @date 2019/1/20
 */
public class TxtFormatter implements FormatterInterface {

    final static Logger logger = LoggerFactory.getLogger(TxtFormatter.class);

    private Book book;

    /**
     * 是否加载成功
     */
    private boolean isLoad = false;

    public void load(FileInputStream stream) {
        try {
            isLoad = true;
        } catch (Exception e) {
            logger.error("txtFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public void load(Book book) {
        try {
            this.book = book;
        } catch (Exception e) {
            logger.error("txtFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public Book parse() {
        if (isLoad) {
            book = new Book();
            return book;
        } else {
            return null;
        }
    }

    public ContentTable parseContentTable(nl.siegmann.epublib.domain.Book book) {
        ContentTable table = new ContentTable();
        return table;
    }

    public boolean export(OutputStream outputStream) {
        return false;
    }
}
