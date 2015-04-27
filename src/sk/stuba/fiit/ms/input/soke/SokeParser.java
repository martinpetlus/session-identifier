package sk.stuba.fiit.ms.input.soke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sk.stuba.fiit.ms.input.Parser;
import sk.stuba.fiit.ms.input.Sessions;
import sk.stuba.fiit.ms.session.Intent;
import sk.stuba.fiit.ms.session.*;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class SokeParser implements Parser {

    @Override
    public void parse(final String file, final Sessions sessions) {
        JSONParser parser = new JSONParser();

        try {
            Object json = parser.parse(new FileReader(file));

            JSONArray jsonSessions = (JSONArray) json;

            for (int i = 0; i < jsonSessions.size(); i++) {
                sessions.add(parseSession((JSONObject) jsonSessions.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Session parseSession(final JSONObject jsonSession) {
        Session session = new Session();

        session.setIntent(new SokeIntent(((Long) jsonSession.get("id")).intValue()));

        parseInteractions((JSONArray) jsonSession.get("interactions"), session);

        return session;
    }

    private static void parseInteractions(final JSONArray jsonInteractions, final Session session) {
        for (int i = 0; i < jsonInteractions.size(); i++) {
            Search search = parseInteraction((JSONObject) jsonInteractions.get(i));

            if (search != null) {
                session.add(search);
            }
        }
    }

    private static Search parseInteraction(final JSONObject jsonInteraction) {
        try {
            Search.Builder builder = new Search.Builder();

            builder.setQuery((String) jsonInteraction.get("query"));

            builder.setTimeStamp(SokeTime.parseToTimeStamp((String) jsonInteraction.get("start_time")));

            parseResults((JSONArray) jsonInteraction.get("results"), builder);

            parseClicks((JSONArray) jsonInteraction.get("clicks"), builder);

            return builder.build();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseClicks(final JSONArray jsonClicks, final Search.Builder searchBuilder) {
        for (int i = 0; i < jsonClicks.size(); i++) {
            searchBuilder.addClick(parseClick((JSONObject) jsonClicks.get(i)));
        }
    }

    private static Click parseClick(final JSONObject jsonClick) {
        int num = ((Long) jsonClick.get("number")).intValue();

        int rank = ((Long) jsonClick.get("rank")).intValue();

        double dwellTime= (double) jsonClick.get("dwell_time");

        return new Click(num, rank, dwellTime);
    }

    private static void parseResults(final JSONArray jsonResults, final Search.Builder searchBuilder) {
        for (int i = 0; i < jsonResults.size(); i++) {
            searchBuilder.addResult(parseResult((JSONObject) jsonResults.get(i)));
        }
    }

    private static Result parseResult(final JSONObject jsonResult) {
        Result.Builder builder = new Result.Builder();

        builder.setRank(((Long) jsonResult.get("rank")).intValue());

        builder.setUrl((String) jsonResult.get("url"));

        builder.setTitle((String) jsonResult.get("title"));

        builder.setSnippet((String) jsonResult.get("snippet"));

        return builder.build();
    }

    private static final class SokeIntent implements Intent {

        private final int sessionId;

        public SokeIntent(final int sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public boolean same(final Intent intent) {
            return (intent instanceof SokeIntent) && ((SokeIntent) intent).sessionId == this.sessionId;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(getClass().getSimpleName()).append('[');

            sb.append("sessionId=").append(sessionId);

            return sb.append(']').toString();
        }

    }

    private static final class SokeTime implements Time {

        // e.g. 2015-03-08 19:30:55 UTC
        private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");

        // In milliseconds
        private final long timeStamp;

        public SokeTime(final String time) throws ParseException {
            this.timeStamp = parseToTimeStamp(time);
        }

        public static long parseToTimeStamp(final String time) throws ParseException {
            return dateTimeFormat.parse(time).getTime();
        }

        @Override
        public double getDifference(final Time time) {
            return ((this.timeStamp - ((SokeTime) time).timeStamp) / 1000.0);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(getClass().getSimpleName()).append('[');

            sb.append(timeStamp);

            return sb.append(']').toString();
        }
    }

}
