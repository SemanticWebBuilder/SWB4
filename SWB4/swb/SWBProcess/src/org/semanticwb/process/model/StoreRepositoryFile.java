package org.semanticwb.process.model;

import java.io.FileInputStream;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;


public class StoreRepositoryFile extends org.semanticwb.process.model.base.StoreRepositoryFileBase 
{
    private static Logger log=SWBUtils.getLogger(StoreRepositoryFile.class);        
    
    public StoreRepositoryFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);

        SWBClass obj=null;
        Iterator<ItemAwareReference> it=instance.listHeraquicalItemAwareReference().iterator();
        while (it.hasNext()) {
            ItemAwareReference ref = it.next();
            String n1=ref.getItemAware().getName();
            String n2=getNodeVarName();
            if(n1!=null && n2!=null)
            {
                if(n1.equals(n2))
                {
                    obj=ref.getProcessObject();
                    break;
                }
            }
            //System.out.println("n1:"+n1);
            //System.out.println("n2:"+n2);
        }
        
        //System.out.println("obj:"+obj);
        
        if(obj!=null && obj instanceof org.semanticwb.process.schema.File)
        {
            org.semanticwb.process.schema.File f=(org.semanticwb.process.schema.File)obj;
            //System.out.println("value:"+f.getValue());
            //System.out.println("file:"+f.getRepositoryFile());
            
            
            String filePath=SWBPortal.getWorkPath()+f.getWorkPath()+"/"+f.getValue();
            //System.out.println("filePath:"+filePath);

            
            RepositoryFile file=f.getRepositoryFile();
            if(file==null)
            {
                String id=this.getNodeId();
                if(id!=null)
                {
                    file=RepositoryFile.ClassMgr.getRepositoryFile(id, this.getProcessSite());
                }

                if(file==null)
                {
                    file=RepositoryFile.ClassMgr.createRepositoryFile(this.getProcessSite());
                }
                f.setRepositoryFile(file);
                file.setRepositoryDirectory(this.getNodeDirectory());
                file.setTitle(this.getNodeName());
                file.setOwnerUserGroup(user.getUserGroup());
            }
            
            try
            {
                file.storeFile(f.getValue(), new FileInputStream(filePath), "Created by process:"+instance.getProcessInstance().getProcessType().getId()+", processInstance:"+instance.getProcessInstance().getId(), false,getNodeStatus()!=null?getNodeStatus().getId():null);
            }catch(Exception e)
            {
                log.error(e);
            }
            
        }else if(obj!=null && obj instanceof org.semanticwb.process.schema.URL)
        {
            org.semanticwb.process.schema.URL f=(org.semanticwb.process.schema.URL)obj;
            //System.out.println("value:"+f.getValue());
            //System.out.println("file:"+f.getRepositoryURL());
            
            
            String url=f.getValue();
            //System.out.println("filePath:"+filePath);
            
            RepositoryURL rurl=f.getRepositoryURL();
            if(rurl==null)
            {
                String id=this.getNodeId();
                if(id!=null)
                {
                    rurl=RepositoryURL.ClassMgr.getRepositoryURL(id, this.getProcessSite());
                }

                if(rurl==null)
                {
                    rurl=RepositoryURL.ClassMgr.createRepositoryURL(this.getProcessSite());
                }
                f.setRepositoryURL(rurl);
                rurl.setRepositoryDirectory(this.getNodeDirectory());
                rurl.setTitle(this.getNodeName());
                rurl.setOwnerUserGroup(user.getUserGroup());
            }
            
            try
            {
                rurl.storeFile(f.getValue(), "Created by process:"+instance.getProcessInstance().getProcessType().getId()+", processInstance:"+instance.getProcessInstance().getId(), false,getNodeStatus()!=null?getNodeStatus().getId():null);
            }catch(Exception e)
            {
                log.error(e);
            }
            
        }
        
    }        
    
}
