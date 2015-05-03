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
        return getBoolean("session_track_2011");
    }

    public boolean useSessionTrack2012() {
        return getBoolean("session_track_2012");
    }

    public boolean useSessionTrack2013() {
        return getBoolean("session_track_2013");
    }

    public boolean useSessionTrack2014() {
        return getBoolean("session_track_2014");
    }

    public boolean useSokeSessions() {
        return getBoolean("soke_sessions");
    }

    public boolean downloadContentsOfClickedResults() {
        return getBoolean("download_contents_of_clicked_results");
    }

    public double getRatioBetweenTrainingAndTestingSessions() {
        return getDouble("ratio_between_training_and_testing_sessions");
    }

    public int getLDAIterations() {
        return getInt("LDA_iterations");
    }

    public int getLDATopics() {
        return getInt("LDA_topics");
    }

    private boolean getBoolean(final String property) {
        return ((Boolean) jsonConfig.get(property)).booleanValue();
    }

    private double getDouble(final String property) {
        return ((Double) jsonConfig.get(property)).doubleValue();
    }

    private int getInt(final String property) {
        return ((Long) jsonConfig.get(property)).intValue();
    }

    public static AppConfig load(final String configFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object json = parser.parse(new FileReader(configFile));

        JSONObject jsonConfig = (JSONObject) json;

        return new AppConfig(jsonConfig);
    }

}
