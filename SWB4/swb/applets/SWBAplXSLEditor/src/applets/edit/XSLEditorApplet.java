package applets.edit;

import javax.swing.JApplet;
import javax.swing.UIManager;

public class XSLEditorApplet extends JApplet {
    @Override
     public void init() {
        final String  file = getParameter("file");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception localException) {
            localException.printStackTrace(System.out);
        }
        XSLRootPane rp = new XSLRootPane();
        rp.setContent(file);
        setRootPane(rp);
    }

    @Override
    public void start() {
        super.start();
        XSLRootPane rp = (XSLRootPane)getRootPane();
        //((XSLRootPane)getRootPane()).focusTextArea();
        rp.focusTextArea();
        //((XSLRootPane)getRootPane()).setText("Hola mmmlola");
    }
}
