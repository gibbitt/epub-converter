package cn.awumbuk.tool.bookconverter.formatter;

import cn.awumbuk.tool.bookconverter.entity.Book;
import cn.awumbuk.tool.bookconverter.entity.Chapter;
import cn.awumbuk.tool.bookconverter.entity.ContentTable;
import cn.awumbuk.tool.bookconverter.util.BookUtil;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Pattern pattern = Pattern.compile("<\\/*\\w+>");

    /**
     * 是否加载成功
     */
    private boolean isLoad = false;

    @Override
    public void load(FileInputStream stream) {
        EpubReader reader = new EpubReader();
        try {
            epubBook = reader.readEpub(stream);
            isLoad = true;
        } catch (Exception e) {
            logger.error("epubFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void load(Book book) {
        try {
            this.book = book;
        } catch (Exception e) {
            logger.error("epubFormatter加载文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Book parse() {
        if (isLoad) {
            book = new Book();
            book.setName(epubBook.getMetadata().getFirstTitle());
            book.setContentTable(this.parseContentTable(epubBook));
            book.setChapters(this.parseChapter(epubBook));
            return book;
        } else {
            return null;
        }
    }

    @Override
    public ContentTable parseContentTable(nl.siegmann.epublib.domain.Book book) {
        ContentTable table = new ContentTable();
        List<Resource> resourceList = book.getContents();
        LinkedHashMap<String, String> hrefMap = new LinkedHashMap<>();
        LinkedHashSet<String> chapterHrefMap = new LinkedHashSet<>();
        LinkedHashMap<String, String> tableOfContentsMap = new LinkedHashMap<>();
        try {
            for (Resource resource : resourceList) {
                String href = resource.getHref();
                chapterHrefMap.add(href);
                // 内容
                String content = new String(resource.getData(), "UTF-8");
                Document document = Jsoup.parse(content);
                Elements links = document.getElementsByTag("a");
                for (Element link: links) {
                    String linkHref = link.attr("href").trim();
                    if (StringUtils.isNotBlank(linkHref)) {
                        hrefMap.put(linkHref, link.text());
                        logger.info("菜单项：{} => {}", linkHref, link.text());
                    }
                }
            }

            for (Map.Entry<String, String> entry: hrefMap.entrySet()) {
                if (chapterHrefMap.contains(entry.getKey())) {
                    // 是目录
                    tableOfContentsMap.put(entry.getKey(), entry.getValue().trim());
                }
            }

            table.setTableOfContents(tableOfContentsMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return table;
    }

    @Override
    public LinkedHashMap<String, Chapter> parseChapter(nl.siegmann.epublib.domain.Book book) {
        LinkedHashMap<String, Chapter> chapterMap = new LinkedHashMap<>();
        ContentTable table = this.book.getContentTable();
        List<Resource> resourceList = book.getContents();
        try {
            for (Resource resource : resourceList) {
                Chapter chapter = new Chapter();
                String href = resource.getHref();
                // 标题
                String title = table.getTitleByHref(href);
                if (StringUtils.isBlank(title)) {
                    // 非内容章节
                    continue;
                }
                // 内容
                String content = new String(resource.getData(), "UTF-8");

                Document document = Jsoup.parse(content);
                Elements elements = document.getElementsByTag("body");
                Elements links = document.getElementsByTag("a");

                if (links.size() > 0) {
                    content = elements.text();
                } else {
                    Element body = elements.get(0);
                    Matcher matcher = pattern.matcher(body.html());
                    content = matcher.replaceAll("");
                }

                chapter.setContent(StringUtils.trim(content));
                chapter.setTitle(title);
                chapterMap.put(href, chapter);
                logger.info("title: {}", chapter.getTitle());
//                logger.info("content: {}", StringUtils.trim(chapter.getContent()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return chapterMap;
    }

    @Override
    public boolean export(OutputStream outputStream) {
        return false;
    }
}
