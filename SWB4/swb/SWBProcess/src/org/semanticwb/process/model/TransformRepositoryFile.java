package org.semanticwb.process.model;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.process.utils.SWBScriptParser;
import org.semanticwb.process.utils.XDocReport;


public class TransformRepositoryFile extends org.semanticwb.process.model.base.TransformRepositoryFileBase 
{
    private static Logger log=SWBUtils.getLogger(TransformRepositoryFile.class);    
    
    public TransformRepositoryFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);

        String filePath=SWBPortal.getWorkPath()+this.getFileTemplate().getWorkPath()+"/"+this.getFileTemplate().getFileName();
        XDocReport rep=new XDocReport(this.getNodeName(), filePath);

        //rep.addContextList("", null, null);

        rep.addContextObject("instance", instance);
        rep.addContextObject("user", user);

        List<ItemAwareReference> list=instance.listHeraquicalItemAwareReference();
        for(ItemAwareReference item : list)
        {
            String varname=item.getItemAware().getName();
            SemanticObject object=item.getProcessObject().getSemanticObject();
            try
            {
                //System.out.println("Cargando clase "+className+" ...");
                Class clazz=SWBPClassMgr.getClassDefinition(object.getSemanticClass());
                //System.out.println("Obteniendo constructor...");
                Constructor c=clazz.getConstructor(SemanticObject.class);
                //System.out.println("Instanciando objeto...");
                Object instanceObject=c.newInstance(object);
                //System.out.println("Agregando variable "+varname+"="+instanceObject+" de tipo "+instanceObject.getClass());
                rep.addContextObject(varname, instanceObject);
                //System.out.println("Variable "+ varname +" agregada");
            }
            catch(Exception cnfe)
            {
                log.error("No se agrego variable "+varname+" a script relacionada con el objeto "+object.getURI()+" en la instancia de proceso "+instance.getURI(),cnfe);
            }
        }

        RepositoryFile file=null;
        String id=this.getNodeId();
        if(id!=null)
        {
            file=RepositoryFile.ClassMgr.getRepositoryFile(id, this.getProcessSite());
        }

        if(file==null)
        {
            file=RepositoryFile.ClassMgr.createRepositoryFile(this.getProcessSite());
        }
        String name=this.getNodeName();
        String comments = this.getNodeComment();
        
        file.setRepositoryDirectory(this.getNodeDirectory());
        if(name!=null)name=name.replace("{filename}", this.getFileTemplate().getFileName());
        if(comments!=null)comments=SWBScriptParser.parser(instance, user, comments);
        file.setTitle(SWBScriptParser.parser(instance, user, name));
        file.setOwnerUserGroup(user.getUserGroup());

        try
        {
            ItemAwareStatus _status = getNodeStatus();
            String status = null;
            if (_status != null) status = _status.getId();
            OutputStream ous = file.storeFile(SWBScriptParser.parser(instance, user, name)+".docx", comments, false, status);
            rep.generateReport(ous);
            
            if (getNodeVarName() != null && !getNodeVarName().trim().equals("")){
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
                    f.setRepositoryFile(file);
                }
            }
        }catch(Exception e){log.error(e);}
    }    
}
