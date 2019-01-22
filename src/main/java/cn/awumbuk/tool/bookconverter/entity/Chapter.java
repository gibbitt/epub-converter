package cn.awumbuk.tool.bookconverter.entity;

import java.util.List;

/**
 * @author leo
 * @date 2019/1/20
 */
public class Chapter {
    private String id;
    private String title;
    private String content;
    private List<String> paragraphs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
