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
public class EpubFormatter implements FormatterInterface {

    final static Logger logger = LoggerFactory.getLogger(EpubFormatter.class);

    /**
     *
     */
    private nl.siegmann.epublib.domain.Book epubBook;

    private Book book;

    /**
     * 是否加载成功
     */
    private boolean isLoad = false;

    public void load(FileInputStream stream) {
        EpubReader reader = new EpubReader();
        try {
            epubBook = reader.readEpub(stream);
            isLoad = true;
        } catch (Exception e) {
            logger.error("epubFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public void load(Book book) {
        try {
            this.book = book;
        } catch (Exception e) {
            logger.error("epubFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public Book parse() {
        if (isLoad) {
            book = new Book();
            book.setName(epubBook.getMetadata().getFirstTitle());
            book.setContentTable(this.parseContentTable(epubBook));
            return book;
        } else {
            return null;
        }
    }

    public ContentTable parseContentTable(nl.siegmann.epublib.domain.Book book) {
        ContentTable table = new ContentTable();
        TableOfContents contents = book.getTableOfContents();
        return table;
    }

    public boolean export(OutputStream outputStream) {
        return false;
    }
}
