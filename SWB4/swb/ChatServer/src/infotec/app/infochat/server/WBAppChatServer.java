/*
 * WBAppChatServer.java
 *
 * Created on 13 de mayo de 2003, 10:52
 */
package infotec.app.infochat.server;

//import com.infotec.appfw.lib.AFAppObject;
//import com.infotec.appfw.util.AFUtils;
//
//import com.infotec.wb.util.WBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author  Administrador
 */
public class WBAppChatServer implements SWBAppObject//AFAppObject
{
    private static Logger log = SWBUtils.getLogger(WBAppChatServer.class);
    ChatServer server = null;
    
    //    private static WBAppChatServer instance=null;
    
    /** Creates a new instance of WBAppChatServer */
    public WBAppChatServer()
    {
    }
    
    //    static synchronized public WBAppChatServer getInstance() {
    //        if (instance == null) {
    //            instance = new WBAppChatServer();
    //            instance.init();
    //        }
    //        return instance;
    //    }
    public void destroy()
    {
        server.stop();
        server = null;
        log.event("ChatServer finalizado...");
    }
    
    public void init()
    {
        server = new ChatServer();
        server.setPort(9494);
        server.logPath = SWBPlatform.createInstance().getPlatformWorkPath() + "/logs"; //WBUtils.getInstance().getWorkPath() + "/logs";
        server.start();
        log.event("ChatServer Iniciado...");
    }
    
    public void refresh()
    {
    }
}
