package xkcdotd;

import org.jetbrains.annotations.NotNull;

public class XkcdComic {

    private final int id;
    private final String url;
    private final String title;
    private final String altText;

    public XkcdComic(final int id,
                     @NotNull final String url,
                     @NotNull final String title,
                     @NotNull final String altText) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.altText = altText;
    }

    public int getId(){
        return id;
    }

    /**
     * Direct URL to the image hosted on XKCD
     * @return
     */
    @NotNull
    public String getImageUrl(){
        return url;
    }

    /**
     * Main URL to the cominc on XKCD (xkcd.com/#)
     * @return
     */
    @NotNull
    public String getUrl(){
        return String.format("https://www.xkcd.com/%d", getId());
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public String getAltText() {
        return altText;
    }

}
