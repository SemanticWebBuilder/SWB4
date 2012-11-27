/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import java.io.FileInputStream;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.process.utils.SWBScriptParser;


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
            
            String filename = null;
            if (f.getValue() != null) {
                filename=f.getValue().substring(16+f.getId().length());
            }
            RepositoryFile file=f.getRepositoryFile();
            if(file==null && filename != null)
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
                String name=this.getNodeName();
                String comments = this.getNodeComment();
                if(name!=null)name=name.replace("{filename}", filename);
                if(comments!=null)comments=SWBScriptParser.parser(instance, user, comments);
                file.setTitle(SWBScriptParser.parser(instance, user, name==null?filename:name));
                file.setOwnerUserGroup(user.getUserGroup());
                
                try
                {
                    file.storeFile(filename, new FileInputStream(filePath), comments, false,getNodeStatus()!=null?getNodeStatus().getId():null);
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }if(obj!=null && obj instanceof org.semanticwb.process.schema.FileCollection)
        {
            org.semanticwb.process.schema.FileCollection f=(org.semanticwb.process.schema.FileCollection)obj;
            
            Iterator<String> it2=f.listValues();
            while (it2.hasNext())
            {
                String filename=it2.next();
                String filePath=SWBPortal.getWorkPath()+f.getWorkPath()+"/"+filename;
                //System.out.println("filePath:"+filePath);


                RepositoryFile file=null;
                Iterator<RepositoryFile> it3=f.listRepositoryFiles();
                while (it3.hasNext())
                {
                    RepositoryFile repositoryFile = it3.next();
                    if(filename.endsWith("_"+repositoryFile.getLastVersion().getVersionFile()))
                    {
                        file=repositoryFile;
                    }
                }
                
                filename=filename.substring(20+f.getId().length());
                
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
                    f.addRepositoryFile(file);
                    file.setRepositoryDirectory(this.getNodeDirectory());
                    String name=this.getNodeName();                    
                    if(name!=null)name=name.replace("{filename}", filename);                    
                    file.setTitle(SWBScriptParser.parser(instance, user, name==null?filename:name));
                    file.setOwnerUserGroup(user.getUserGroup());
                }
                String comments = this.getNodeComment();
                if(comments!=null)comments=SWBScriptParser.parser(instance, user, comments);
                try
                {
                    file.storeFile(filename, new FileInputStream(filePath), comments, false,getNodeStatus()!=null?getNodeStatus().getId():"Indefinido");
                }catch(Exception e)
                {
                    log.error(e);
                }
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
                rurl.setTitle(SWBScriptParser.parser(instance, user, this.getNodeName()));
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
