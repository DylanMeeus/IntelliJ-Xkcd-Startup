package xkcdotd;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.Random;

public interface XkcdService {

    // html to format the string for retrieving json
    static final String jsonUrl = "https://xkcd.com/%d/info.0.json";
    static final String latestUrl = "https://xkcd.com/info.0.json";
    static final JsonParser parser = new JsonParser();

    /**
     * Get the latest comic
     * We need this to determine the range of random numbers
     * @return
     */
    static int getLatestNum() throws IOException {
        JsonObject rootObject = getComicAsJsonObject(latestUrl);
        return rootObject.get("num").getAsInt();
    }

    @NotNull
    static JsonObject getComicAsJsonObject(String jsonUrl) throws IOException {
        URL url = new URL(jsonUrl);
        URLConnection request = url.openConnection();
        request.connect();
        JsonElement root = parser.parse(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }

    /**
     * Returns a random comic
     * @return
     */
    public static Optional<XkcdComic> getComic(){
        try {
            final int latestNum = getLatestNum();
            final Random random = new Random();
            final int randomComic = random.nextInt(latestNum + 1);// be inclusive :-)
            return Optional.of(getComic(randomComic));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @NotNull
    static XkcdComic getComic(final int id) throws IOException {
        JsonObject rootObject = getComicAsJsonObject(String.format(jsonUrl, id));
        String url = rootObject.get("img").getAsString();
        String title = rootObject.get("title").getAsString();
        String alt = rootObject.get("alt").getAsString();
        return new XkcdComic(id, url, title, alt);
    }
}
