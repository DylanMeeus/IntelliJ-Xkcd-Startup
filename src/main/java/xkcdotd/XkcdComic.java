package xkcdotd;

import org.jetbrains.annotations.NotNull;

public class XkcdComic {

    private final String url;
    private final String title;
    private final String altText;

    public XkcdComic(@NotNull final String url,
                     @NotNull final String title,
                     @NotNull final String altText) {
        this.url = url;
        this.title = title;
        this.altText = altText;
    }

    @NotNull
    public String getUrl(){
        return url;
    }


}
