package cn.awumbuk.tool.bookconverter.formatter;

import cn.awumbuk.tool.bookconverter.entity.Book;
import cn.awumbuk.tool.bookconverter.entity.Chapter;
import cn.awumbuk.tool.bookconverter.entity.ContentTable;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leo
 * @date 2019/1/20
 */
public class TxtFormatter implements FormatterInterface {

    final static Logger logger = LoggerFactory.getLogger(TxtFormatter.class);

    private Book book;

    private final String chapterTitlePrefix = "### ";
    private final String paragraphPrefix = "  ";

    private Pattern pattern = Pattern.compile("^ *", Pattern.DOTALL);

    /**
     * 是否加载成功
     */
    private boolean isLoad = false;

    @Override
    public void load(FileInputStream stream) {
        try {
            isLoad = true;
        } catch (Exception e) {
            logger.error("txtFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void load(Book book) {
        try {
            this.book = book;
        } catch (Exception e) {
            logger.error("txtFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Book parse() {
        if (isLoad) {
            book = new Book();
            return book;
        } else {
            return null;
        }
    }

    @Override
    public ContentTable parseContentTable(nl.siegmann.epublib.domain.Book book) {
        ContentTable table = new ContentTable();
        return table;
    }

    @Override
    public LinkedHashMap<String, Chapter> parseChapter(nl.siegmann.epublib.domain.Book book) {
        LinkedHashMap<String, Chapter> chapterMap = new LinkedHashMap<>();
        return chapterMap;
    }

    @Override
    public boolean export(OutputStream outputStream) {
        if (book.getChapters() != null) {
            for (Map.Entry<String, Chapter> entry : book.getChapters().entrySet()) {
                String chapterContent = this.exportChapter(entry.getValue());
                try {
                    outputStream.write(chapterContent.getBytes());
                } catch (Exception e) {
                    logger.error("导出章节失败：{}", ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return false;
    }

    private String exportChapter(Chapter chapter) {

        StringBuilder builder = new StringBuilder();
        builder.append(chapterTitlePrefix);
        if (StringUtils.isNotBlank(chapter.getTitle())) {
            builder.append(chapter.getTitle());
        }
        builder.append("\n\n");
        Matcher matcher = pattern.matcher(chapter.getContent());
        String content = matcher.replaceAll(paragraphPrefix);
        builder.append(content.replace("\n", "\n\n"));
        builder.append("\n\n");
        return builder.toString();
    }
}
