package xkcdotd;

import com.intellij.ide.util.TipUIUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class XkcdDialog extends DialogWrapper {

    private JLabel testLabel;
    private JPanel XKCDPanel;
    private final String htmlContent = "<html><body><img src=\"%s\"></br><p>%s</p></body></html>";
    public XkcdDialog(){
        super(WindowManagerEx.getInstanceEx().findVisibleFrame(), true);
        initialize();
    }


    private void initialize(){
        // load the data
        XKCDPanel = new JPanel(new BorderLayout());
        try {
            TipUIUtil.Browser browser = TipUIUtil.createBrowser();
            XkcdComic comic = XkcdService.getComic().orElseThrow(Exception::new);
            setTitle(String.format("Xkcd %d: %s",comic.getId() , comic.getTitle()));
            browser.setText(String.format(htmlContent, comic.getImageUrl(), comic.getUrl()));
            XKCDPanel.add(browser.getComponent(), BorderLayout.CENTER);
            JTextArea altField = new JTextArea("Alt: " + comic.getAltText());
            altField.setEditable(false);
            altField.setLineWrap(true);
            altField.setColumns(50);
            XKCDPanel.add(altField, BorderLayout.SOUTH);
            initializeActions(comic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    private void initializeActions(XkcdComic comic) {
        this.setCancelButtonText("Close");
        this.myOKAction = new OpenInBrowserAction(comic);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return XKCDPanel;
    }
    public static XkcdDialog createForProject(final Project project) {
        return new XkcdDialog();
    }

    private static class OpenInBrowserAction extends AbstractAction{

        private final XkcdComic comic;

        public OpenInBrowserAction(@NotNull final XkcdComic comic){
            putValue(NAME, "View in browser");
            this.comic = comic;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!Desktop.isDesktopSupported()) {
                return;
            }
            try {
                Desktop.getDesktop().browse(new URI(comic.getUrl()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
