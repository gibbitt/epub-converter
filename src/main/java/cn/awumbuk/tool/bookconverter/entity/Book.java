package cn.awumbuk.tool.bookconverter.entity;

import java.util.Map;

/**
 * @author leo
 * @date 2019/1/20
 */
public class Book {
    /**
     * 书名
     */
    private String name;
    /**
     * ISBN编码
     */
    private String isbn;
    /**
     * 目录
     */
    private ContentTable contentTable;
    /**
     * 章节列表
     */
    private Map<String, Chapter> chapters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public ContentTable getContentTable() {
        return contentTable;
    }

    public void setContentTable(ContentTable contentTable) {
        this.contentTable = contentTable;
    }

    public Map<String, Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Map<String, Chapter> chapters) {
        this.chapters = chapters;
    }
}
