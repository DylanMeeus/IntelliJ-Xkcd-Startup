package xkcdotd;

import com.intellij.ide.util.TipUIUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class XkcdDialog extends DialogWrapper {

    private JLabel testLabel;
    private JPanel XKCDPanel;
    private final String htmlContent = "<html><body><img src=\"%s\"><p>%s</p></body></html>";
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
            setTitle(comic.getTitle());
            browser.setText(String.format(htmlContent, comic.getUrl(), comic.getUrl()));
            XKCDPanel.add(browser.getComponent(), BorderLayout.CENTER);
            JTextArea altField = new JTextArea("Alt: " + comic.getAltText());
            altField.setEditable(false);
            altField.setLineWrap(true);
            altField.setColumns(50);
            XKCDPanel.add(altField, BorderLayout.SOUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return XKCDPanel;
    }
    public static XkcdDialog createForProject(final Project project) {
        return new XkcdDialog();
    }
}
