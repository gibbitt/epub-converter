package cn.awumbuk.tool.bookconverter;

import cn.awumbuk.tool.bookconverter.entity.Book;
import cn.awumbuk.tool.bookconverter.formatter.EpubFormatter;
import cn.awumbuk.tool.bookconverter.formatter.FormatterInterface;
import cn.awumbuk.tool.bookconverter.formatter.TxtFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leo
 * @date 2019/1/20
 */
public class BookConverter {
    static String TXT = "TxtFormatter";
    static String EPUB = "EpubFormatter";

    final static Logger logger = LoggerFactory.getLogger(BookConverter.class);
    /**
     * instance
     */
    private static BookConverter converter = null;
    /**
     *
     */
    private Map<String, FormatterInterface> formatters;
    /**
     *
     */
    private String formatterName;
    /**
     * 书籍文件流
     */
    private FileInputStream stream;
    /**
     * 书籍节点
     */
    private Book book;

    /**
     * @return
     */
    public static BookConverter getInstance() {
        if (converter == null) {
            converter = new BookConverter();
            converter.formatters = new HashMap<String, FormatterInterface>();
            converter.formatters.put(TXT, new TxtFormatter());
            converter.formatters.put(EPUB, new EpubFormatter());
        }
        return converter;
    }

    public void setData(FileInputStream inputStream, String type) {
        this.stream = inputStream;
        if (formatters.containsKey(type)) {
            formatterName = type;
            formatters.get(formatterName).load(stream);
        }
    }

    /**
     * 导出为txt文件
     * @return
     */
    public boolean asTxt() {
        book = formatters.get(formatterName).parse();
        FormatterInterface txtFormatter = formatters.get(TXT);
        txtFormatter.load(book);
        //txtFormatter.export();
        logger.info("{}：{}", formatterName, book.getName());
        return false;
    }

    /**
     * 导出为mobi格式文件
     * @return
     */
    public FileOutputStream asMobi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
