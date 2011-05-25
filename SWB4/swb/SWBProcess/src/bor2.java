
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.process.model.*;
import org.semanticwb.process.utils.XDocReport;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jei
 */
public class bor2
{
    public void main(FlowNodeInstance instance, User user, StoreArtifact store)
    {
        String filePath=SWBPortal.getWorkPath()+store.getProcessFileTemplate().getWorkPath()+"/"+store.getProcessFileTemplate().getFileName();
        XDocReport rep=new XDocReport(store.getProcessFileName(), filePath);

        //rep.addContextList("", null, null);
        rep.addContextObject("instance", instance);
        rep.addContextObject("user", user);

        RepositoryFile file=null;
        String id=store.getRepositoryFileId();
        if(id!=null)
        {
            file=RepositoryFile.ClassMgr.getRepositoryFile(id, store.getProcessSite());
        }

        if(file==null)
        {
            file=RepositoryFile.ClassMgr.createRepositoryFile(store.getProcessSite());
        }
        file.setRepositoryDirectory(store.getProcessDirectory());
        file.setTitle(store.getProcessFileName());

        try
        {
            rep.generateReport(file.storeFile(store.getProcessFileName(), null, false));
        }catch(Exception e){e.printStackTrace();}

    }

}
