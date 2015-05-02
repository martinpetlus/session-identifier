package sk.stuba.fiit.ms.downloading;

import java.io.Serializable;

public final class ResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String url;

    private final String content;

    public ResultContent(final String url, final String content) {
        this.url = url;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

}
