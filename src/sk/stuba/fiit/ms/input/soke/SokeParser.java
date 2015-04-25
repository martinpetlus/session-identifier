package sk.stuba.fiit.ms.input.soke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sk.stuba.fiit.ms.input.Parser;
import sk.stuba.fiit.ms.session.Session;

import java.io.FileReader;
import java.util.List;

public final class SokeParser implements Parser {

    @Override
    public void parse(final String file, final List<Session> sessions) {
        JSONParser parser = new JSONParser();

        try {
            Object json = parser.parse(new FileReader(file));

            JSONArray jsonSessions = (JSONArray) json;

            for (int i = 0; i < jsonSessions.size(); i++) {
//                sessions.add(parseSession((JSONObject) jsonSessions.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Session parseSession(JSONObject jsonSession) {
        return null;
    }

}
