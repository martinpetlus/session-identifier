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

    private JSONObject getSessionTrack2011Config() {
        return getJSONObject("2011", getSessionTracksSessionsConfig());
    }

    private JSONObject getSessionTrack2012Config() {
        return getJSONObject("2012", getSessionTracksSessionsConfig());
    }

    private JSONObject getSessionTrack2013Config() {
        return getJSONObject("2013", getSessionTracksSessionsConfig());
    }

    private JSONObject getSessionTrack2014Config() {
        return getJSONObject("2014", getSessionTracksSessionsConfig());
    }

    public boolean loadSessionTrack2011() {
        return getBoolean("load", getSessionTrack2011Config());
    }

    public String getSessionTrack2011File() {
        return getString("file", getSessionTrack2011Config());
    }

    public boolean loadSessionTrack2012() {
        return getBoolean("load", getSessionTrack2012Config());
    }

    public String getSessionTrack2012File() {
        return getString("file", getSessionTrack2012Config());
    }

    public boolean loadSessionTrack2013() {
        return getBoolean("load", getSessionTrack2013Config());
    }

    public String getSessionTrack2013File() {
        return getString("file", getSessionTrack2013Config());
    }

    public boolean loadSessionTrack2014() {
        return getBoolean("load", getSessionTrack2014Config());
    }

    public boolean useLDA() {
        return getBoolean("LDA", jsonConfig);
    }

    public String getSessionTrack2014File() {
        return getString("file", getSessionTrack2014Config());
    }

    private JSONObject getSokeSessionsConfig() {
        return getJSONObject("soke_sessions", jsonConfig);
    }

    public boolean loadSokeSessions() {
        return getBoolean("load", getSokeSessionsConfig());
    }

    public String getSokeSessionsFile() {
        return getString("file", getSokeSessionsConfig());
    }

    public boolean loadingSessionTrackSessions(){
        return loadSessionTrack2011() || loadSessionTrack2012() ||
            loadSessionTrack2013() || loadSessionTrack2014();
    }

    public boolean shuffleSessionTrackTestingQueries() {
        return getBoolean("shuffle_testing_queries", getSessionTracksSessionsConfig());
    }

    private JSONObject getSessionTracksSessionsConfig() {
        return getJSONObject("session_tracks_sessions", jsonConfig);
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

    private static String getString(final String property, final JSONObject jsonObject) {
        return (String) jsonObject.get(property);
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
