package sk.stuba.fiit.ms.session;

import sk.stuba.fiit.ms.utils.TextNormalizer;

import java.util.ArrayList;
import java.util.List;

public final class Result {

    private final int rank;

    private final String url;

    private final String title;

    private final String snippet;

    private List<Click> clicks;

    private String content;

    private Result(final Builder builder) {
        this.rank = builder.rank;
        this.url = builder.url;
        this.title = builder.title;
        this.snippet = builder.snippet;

        this.clicks = null;
        this.content = "";
    }

    public int getRank() {
        return rank;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public List<Click> getClicks() {
        if (clicks == null || clicks.isEmpty()) {
            return new ArrayList<Click>();
        } else {
            return new ArrayList<Click>(clicks);
        }
    }

    void addClick(final Click click) {
        if (clicks == null) {
            clicks = new ArrayList<Click>();
        }

        clicks.add(click);
    }

    public double getSpentTime() {
        if (isClicked()) {
            double sum = 0.0;

            for (Click click : clicks) {
                sum += click.getSpentTime();
            }

            return  sum;
        } else {
            return 0.0;
        }
    }

    public boolean isClicked() {
        return clicks != null && !clicks.isEmpty();
    }

    public int getNumberOfClicks() {
        return clicks == null ? 0 : clicks.size();
    }

    public void setContent(final String content) {
        if (content != null) {
            this.content = content.trim();
        }
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName());
        sb.append('[');

        sb.append("rank=");
        sb.append(rank);

        sb.append(" url=");
        sb.append(url);

        sb.append(" title=");
        sb.append(title);

        sb.append(" snippet=");
        sb.append(snippet);

        sb.append(" clicks=");
        sb.append(getNumberOfClicks());

        sb.append(" content=");
        sb.append(content.length());

        return sb.append(']').toString();
    }

    public static final class Builder {

        private String url;

        private String title = "";

        private String snippet = "";

        private int rank;

        public void setUrl(final String url) {
            this.url = url;
        }

        public void setTitle(final String title) {
            this.title = TextNormalizer.normalize(title);
        }

        public void setSnippet(final String snippet) {
            this.snippet = TextNormalizer.normalize(snippet);
        }

        public void setRank(final int rank) {
            this.rank = rank;
        }

        public Result build() {
            return new Result(this);
        }

    }

}
