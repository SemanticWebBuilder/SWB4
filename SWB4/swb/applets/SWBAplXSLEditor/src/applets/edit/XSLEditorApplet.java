package applets.edit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.swing.JApplet;
import javax.swing.UIManager;

public class XSLEditorApplet extends JApplet {
    @Override
    public void init() {
        final String file = getParameter("file");
        String isDef = getParameter("isDefaultTemplate");
        boolean isDefaultTemplate = Boolean.valueOf(isDef);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception localException) {
            localException.printStackTrace(System.out);
        }
        XSLRootPane rp = new XSLRootPane(isDefaultTemplate);
        String content = null;
        if (isDefaultTemplate) {
            try {
                content = URLDecoder.decode(getParameter("content"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                content = e.getMessage();
            }
            rp.setText(content);
        } else {
            rp.setContent(file);
        }
        setRootPane(rp);
    }

    @Override
    public void start() {
        super.start();
        XSLRootPane rp = (XSLRootPane) getRootPane();
        rp.focusTextArea();
    }
}
