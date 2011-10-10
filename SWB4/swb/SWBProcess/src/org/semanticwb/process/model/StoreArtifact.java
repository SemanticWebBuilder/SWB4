/**
* SemanticWebBuilder Process (SWBP) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

package org.semanticwb.process.model;
import java.lang.reflect.Constructor;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.process.utils.XDocReport;

public class StoreArtifact extends org.semanticwb.process.model.base.StoreArtifactBase 
{
    private static Logger log=SWBUtils.getLogger(StoreArtifact.class);

    public StoreArtifact(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);

        String filePath=SWBPortal.getWorkPath()+this.getProcessFileTemplate().getWorkPath()+"/"+this.getProcessFileTemplate().getFileName();
        XDocReport rep=new XDocReport(this.getProcessFileName(), filePath);

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
        String id=this.getRepositoryFileId();
        if(id!=null)
        {
            file=RepositoryFile.ClassMgr.getRepositoryFile(id, this.getProcessSite());
        }

        if(file==null)
        {
            file=RepositoryFile.ClassMgr.createRepositoryFile(this.getProcessSite());
        }
        file.setRepositoryDirectory(this.getProcessDirectory());
        file.setTitle(this.getProcessFileName());

        try
        {
            rep.generateReport(file.storeFile(this.getProcessFileName(), null, false));
        }catch(Exception e){log.error(e);}

    }

}
