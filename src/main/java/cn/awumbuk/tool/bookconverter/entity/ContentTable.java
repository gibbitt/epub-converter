package cn.awumbuk.tool.bookconverter.entity;

import java.util.LinkedHashMap;

/**
 * @author leo
 * @date 2019/1/20
 */
public class ContentTable {
    private LinkedHashMap<String, String> tableOfContents;

    public LinkedHashMap<String, String> getTableOfContents() {
        return tableOfContents;
    }

    public void setTableOfContents(LinkedHashMap<String, String> tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    public String getTitleByHref(String href) {
        if (tableOfContents.containsKey(href)) {
            return tableOfContents.get(href);
        }
        return null;
    }

}
