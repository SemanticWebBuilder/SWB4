package signatureapplet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

/**
 *
 * @author serch
 */
public class SignatureApplet extends JApplet {

   // private String urlGetSignatureString = "http://localhost:8888/demo.jsp";
    private String urlPostSignature = "http://localhost:8888/post.jsp";
    private String sessionid;
    private String message = "||TEST|RECORD|23232443434343|TOTAL|Acepted||";
    private java.applet.Applet applet;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        //System.out.println("Java Version:"+System.getProperty("java.version")+" "+System.getProperty("java.vendor"));
        applet = this;
        //sessionid = getParameter("sessionid");
        //urlGetSignatureString=getParameter("urlMessage");
        //urlPostSignature = getParameter("urlPost");
        message = getParameter("message");
        if (null==message) message="sin cadena";
          /*try {
            URL urlget = new URL(urlGetSignatureString);
            URLConnection ucon = urlget.openConnection();
            ucon.addRequestProperty("JSESSIONID", sessionid);
            ucon.connect();

            InputStream o = (InputStream) ucon.getContent();
            int size = 0;
            byte[] keyBytes = new byte[8092];
            while (o.available() > 0) {
                size = o.read(keyBytes);
            }
            message = new String(keyBytes);
            message = message.trim();
        } catch (IOException ex) {
            message = "No se pudo obtener cadena a firmar";
            ex.printStackTrace();
        }*/
        this.setSize(600, 250);
        this.setBackground(new java.awt.Color(255, 255, 255));
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    SignaturePanel lbl = new SignaturePanel();
                    lbl.setToSignString(message, urlPostSignature, sessionid, applet);
                    add(lbl);
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
            e.printStackTrace(System.err);
        }
    }
    // TODO overwrite start(), stop() and destroy() methods
}
