package sk.stuba.fiit.ms.features.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

final class Util {

    private Util() {}

    static List<String> mapToHosts(final List<String> urls) {
        List<String> hosts = new ArrayList<String>();

        for (String url : urls) {
            try {
                hosts.add(new URL(url).getHost());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return hosts;
    }

}
