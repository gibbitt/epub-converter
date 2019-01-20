package cn.awumbuk.tool.bookconverter;

import cn.awumbuk.tool.bookconverter.formatter.FormatterInterface;

/**
 * @author leo
 * @date 2019/1/20
 */
public class BookConverter {
    /**
     *
     */
    private FormatterInterface formatter;

    public FormatterInterface getFormatter() {
        return formatter;
    }

    public void setFormatter(FormatterInterface formatter) {
        this.formatter = formatter;
    }


}
