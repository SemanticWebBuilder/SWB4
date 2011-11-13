
import java.io.File;
import org.hsqldb.server.Server;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.SWBRTSBridge;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class SWBTSServer {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        if(args.length>2)
        {
            SWBUtils.createInstance(args[2]);
        }else
        {
            SWBUtils.createInstance(new File("").getAbsolutePath());
        }
        String base=SWBUtils.getApplicationPath();
        System.out.println("Path:"+base);
        Logger log=SWBUtils.getLogger(SWBTSServer.class);
        
        SWBPlatform platform = SWBPlatform.createInstance();
        platform.setPlatformWorkPath(base);
        platform.setProperties(SWBUtils.TEXT.getPropertyFile("/web.properties"));
        //platform.setPlatformWorkPath(workPath);
        String persistType = platform.getEnv("swb/triplepersist", SWBPlatform.PRESIST_TYPE_DEFAULT);
        platform.setPersistenceType(persistType);
        
        
        SWBPlatform.getSemanticMgr().initializeDB();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        //SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        //SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        //SWBPlatform.getSemanticMgr().getOntology().rebind();
        int port=6666;
        if(args.length>1)port=Integer.parseInt(args[1]);
        log.event("Initializing SWBRemoteTripleStoreServer on port:"+port);
        SWBRTSBridge server = new SWBRTSBridge();       
        server.setPort(port);
        server.start();
        //server.run();
        
        Server serv=new Server();
        serv.setSilent(true);
        serv.setPort(port+1);
        serv.setDatabaseName(0, "swb");
        serv.setDatabasePath(0, base+"/data/swb");
        serv.start();        
        
    }    
    
}
