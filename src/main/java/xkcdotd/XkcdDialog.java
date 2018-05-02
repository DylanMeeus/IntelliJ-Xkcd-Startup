package xkcdotd;

import com.intellij.ide.util.TipUIUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class XkcdDialog extends DialogWrapper {

    private JLabel testLabel;
    private JPanel XKCDPanel;
    private final String htmlContent = "<html><body><img src=\"%s\"></body></html>";
    public XkcdDialog(){
        super(WindowManagerEx.getInstanceEx().findVisibleFrame(), true);
        initialize();
    }



    private void initialize(){
        // load the data
        XKCDPanel = new JPanel();
        try {
            TipUIUtil.Browser browser = TipUIUtil.createBrowser();
            XkcdComic comic = XkcdService.getComic().orElseThrow(Exception::new);
            browser.setText(String.format(htmlContent, comic.getUrl()));
            XKCDPanel.add(browser.getComponent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        testLabel = new JLabel();
        testLabel.setText("Hello World");
        System.out.println("initialized");
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
