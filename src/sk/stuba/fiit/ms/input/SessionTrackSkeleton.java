package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sk.stuba.fiit.ms.features.lexical.TextNormalizer;
import sk.stuba.fiit.ms.session.*;
import sk.stuba.fiit.ms.session.Search;

public abstract class SessionTrackSkeleton implements SessionTrack {

    @Override
    public Time parseTime(final String time) {
        if (time.equals("-1")) {
            return DayTime.ZERO;
        } else {
            return DayTime.valueOf(time);
        }
    }

    @Override
    public Session parseSession(final Node node) {
        Session s = new Session();

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if (Util.isNode(child, "topic")) {
                s.setTopic(parseTopic(child));
            } else if (Util.isNode(child, "interaction")) {
                s.add(parseInteraction(child, s).build());
            }
        }

        return s;
    }

    @Override
    public Result parseResult(final Node node) {
        String url = "";
        String title = "";
        String snippet = "";

        int rank = Integer.parseInt(Util.getAttrValue(node, "rank"));

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if (Util.isNode(child, "url")) {
                url = child.getTextContent();
            } else if (Util.isNode(child, "title")) {
                title = TextNormalizer.normalize(child.getTextContent());
            } else if (Util.isNode(child, "snippet")) {
                snippet = TextNormalizer.normalize(child.getTextContent());
            }
        }

        return new Result(rank, url, title, snippet);
    }

    @Override
    public Search.Builder parseInteraction(final Node node, final Session session) {
        Search.Builder builder = new Search.Builder();

        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            if (Util.isNode(child, "query")) {
                builder.setQuery(TextNormalizer.normalize(child.getTextContent()));
            } else if (Util.isNode(child, "results")) {
                parseResults(child, builder);
            } else if (Util.isNode(child, "clicked")) {
                parseClicked(child, builder);
            }
        }

        return builder;
    }

    @Override
    public Click parseClick(final Node node) {
        int num = Integer.parseInt(Util.getAttrValue(node, "num"));

        Time startTime = parseTime(Util.getAttrValue(node, "starttime"));
        Time endTime = parseTime(Util.getAttrValue(node, "endtime"));

        int rank = Integer.parseInt(Util.getChildValue(node, "rank"));

        return new Click(num, rank, startTime, endTime);
    }

    private void parseResults(final Node node, final Search.Builder b) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (Util.isNode(childNodes.item(i), "result")) {
                b.addResult(parseResult(childNodes.item(i)));
            }
        }
    }

    private void parseClicked(final Node node, final Search.Builder b) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (Util.isNode(childNodes.item(i), "click")) {
                b.addClick(parseClick(childNodes.item(i)));
            }
        }
    }

    private static final class DayTime implements Time {

        public static final DayTime ZERO = new DayTime(0.0, 0.0, 0.0);

        private static final int SECONDS_PER_HOUR = 3600;

        private static final int SECONDS_PER_MINUTE = 60;

        private final double hour;

        private final double minute;

        private final double second;

        public DayTime(final double hour, final double minute, final double second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        @Override
        public double getDifference(final Time time) {
            if (this == ZERO || time == ZERO) {
                return 0.0;
            }

            DayTime dayTime = (DayTime) time;

            double hours = Math.abs(this.hour - dayTime.hour);
            double minutes = Math.abs(this.minute - dayTime.minute);
            double seconds = Math.abs(this.second - dayTime.second);

            return hours * SECONDS_PER_HOUR + minutes * SECONDS_PER_MINUTE + seconds;
        }

        public static DayTime valueOf(final String time) {
            String[] t = time.split("[:]");

            return new DayTime(Double.parseDouble(t[0]), Double.parseDouble(t[1]), Double.parseDouble(t[2]));
        }

        @Override
        public String toString() {
            return "DayTime[hour=" + hour +
                    " minute=" + minute +
                    " second=" + second + "]";
        }

    }

}
