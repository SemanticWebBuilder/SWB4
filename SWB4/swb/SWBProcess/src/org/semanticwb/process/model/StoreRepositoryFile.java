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
            String n2=getVarname();
            if(n1!=null && n2!=null)
            {
                obj=ref.getProcessObject();
                break;
            }
            System.out.println("n1:"+n1);
            System.out.println("n2:"+n2);
        }
        
        System.out.println("obj:"+obj);
        
        if(obj!=null && obj instanceof org.semanticwb.process.schema.File)
        {
            org.semanticwb.process.schema.File f=(org.semanticwb.process.schema.File)obj;
            System.out.println("value:"+f.getValue());
            System.out.println("file:"+f.getRepositoryFile());
            
            
            String filePath=SWBPortal.getWorkPath()+f.getWorkPath()+"/"+f.getValue();
            System.out.println("filePath:"+filePath);

            
            RepositoryFile file=f.getRepositoryFile();
            if(file==null)
            {
                String id=this.getRepositoryFileId();
                if(id!=null)
                {
                    file=RepositoryFile.ClassMgr.getRepositoryFile(id, this.getProcessSite());
                }

                if(file==null)
                {
                    file=RepositoryFile.ClassMgr.createRepositoryFile(this.getProcessSite());
                }
                f.setRepositoryFile(file);
                file.setRepositoryDirectory(this.getProcessDirectory());
                file.setTitle(this.getProcessFileName());
                file.setOwnerUserGroup(user.getUserGroup());
            }
            
            try
            {
                file.storeFile(f.getValue(), new FileInputStream(filePath), "Created by process:"+instance.getProcessInstance().getProcessType().getId()+", processInstance:"+instance.getProcessInstance().getId(), false);
            }catch(Exception e)
            {
                log.error(e);
            }
            
        }
        
//        XDocReport rep=new XDocReport(this.getProcessFileName(), filePath);
//
//        //rep.addContextList("", null, null);
//
//        rep.addContextObject("instance", instance);
//        rep.addContextObject("user", user);
//
//        List<ItemAwareReference> list=instance.listHeraquicalItemAwareReference();
//        for(ItemAwareReference item : list)
//        {
//            String varname=item.getItemAware().getName();
//            SemanticObject object=item.getProcessObject().getSemanticObject();
//            try
//            {
//                //System.out.println("Cargando clase "+className+" ...");
//                Class clazz=SWBPClassMgr.getClassDefinition(object.getSemanticClass());
//                //System.out.println("Obteniendo constructor...");
//                Constructor c=clazz.getConstructor(SemanticObject.class);
//                //System.out.println("Instanciando objeto...");
//                Object instanceObject=c.newInstance(object);
//                //System.out.println("Agregando variable "+varname+"="+instanceObject+" de tipo "+instanceObject.getClass());
//                rep.addContextObject(varname, instanceObject);
//                //System.out.println("Variable "+ varname +" agregada");
//            }
//            catch(Exception cnfe)
//            {
//                log.error("No se agrego variable "+varname+" a script relacionada con el objeto "+object.getURI()+" en la instancia de proceso "+instance.getURI(),cnfe);
//            }
//        }
//            try
//            {
//                rep.generateReport(file.storeFile(this.getProcessFileName(), null, false));
//            }catch(Exception e){log.error(e);}   

    }        
    
}
