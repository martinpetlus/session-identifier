package sk.stuba.fiit.ms.apps;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public final class AppConfig {

    private final JSONObject jsonConfig;

    private AppConfig(final JSONObject jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    public boolean useSessionTrack2011() {
        return getBoolean("2011", getSessionTrackSessions());
    }

    public boolean useSessionTrack2012() {
        return getBoolean("2012", getSessionTrackSessions());
    }

    public boolean useSessionTrack2013() {
        return getBoolean("2013", getSessionTrackSessions());
    }

    public boolean useSessionTrack2014() {
        return getBoolean("2014", getSessionTrackSessions());
    }

    /**
     * We can load sessions from session tracks (2011, 2012,
     * 2013, 2014), or Soke sessions, but not both at same time.
     * @return true if we are loading Soke sessions
     */
    public boolean useSokeSessions() {
        if (useSessionTrack2011() || useSessionTrack2012() ||
            useSessionTrack2013() || useSessionTrack2014()) {
            return false;
        } else {
            return getBoolean("soke_sessions", jsonConfig);
        }
    }

    private JSONObject getSessionTrackSessions() {
        return getJSONObject("session_track_sessions", jsonConfig);
    }

    public boolean downloadContentsOfClickedResults() {
        return getBoolean("download_contents_of_clicked_results", jsonConfig);
    }

    public double getRatioBetweenTrainingAndTestingSessions() {
        return getDouble("ratio_between_training_and_testing_sessions", jsonConfig);
    }

    public double getSVM_C() {
        return getDouble("SVM_C", jsonConfig);
    }

    public int getLDAIterations() {
        return getInt("LDA_iterations", jsonConfig);
    }

    public int getLDATopics() {
        return getInt("LDA_topics", jsonConfig);
    }

    private static JSONObject getJSONObject(final String property, final JSONObject jsonObject) {
        return (JSONObject) jsonObject.get(property);
    }

    private static boolean getBoolean(final String property, final JSONObject jsonObject) {
        return (Boolean) jsonObject.get(property);
    }

    private static double getDouble(final String property, final JSONObject jsonObject) {
        return (Double) jsonObject.get(property);
    }

    private static int getInt(final String property, final JSONObject jsonObject) {
        return ((Long) jsonObject.get(property)).intValue();
    }

    public static AppConfig load(final String configFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object json = parser.parse(new FileReader(configFile));

        JSONObject jsonConfig = (JSONObject) json;

        return new AppConfig(jsonConfig);
    }

}
