/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;

public class UserTask extends org.semanticwb.process.model.base.UserTaskBase 
{
    public static final int START_ACTIONCODE=1;
    public static final int CLOSE_ACTIONCODE=2;
    public static final int ABORT_ACTIONCODE=3;
    
    public UserTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        getTaskWebPage();
    }
    



    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        System.out.println("execute:"+instance+" "+user);
        if(getResourceAssignationRule()>0 && instance.getAssignedto()==null)
        {
            boolean groupFilter=getProcess().isFilterByOwnerUserGroup();
            if(getResourceAssignationRule()>0) // De momento todos son aleatorios
            {
                List<User> users=SWBProcessMgr.getUsers(instance);
                int s=users.size();
                User assigned=users.get(new Random().nextInt(s));                
                instance.setAssigned(new Date());
                instance.setAssignedto(assigned);
                if(getProcess().isSendAssigmentNotifications())
                {
                    //SWBUtils.EMAIL.sendBGEmail(user.getEmail(), "Notificacion de Asignacion ", null);
                }
            }
        }
    }
    
    

    @Override
    public WrapperTaskWebPage getTaskWebPage() 
    {
        WrapperTaskWebPage wp=super.getTaskWebPage();
        if(wp==null)
        {
            wp=WrapperTaskWebPage.ClassMgr.createWrapperTaskWebPage(getId()+"_swpt", getProcessSite());
            setTaskWebPage(wp);
            wp.setActive(true);
            //eliminar cache
            //getSemanticObject().removeCache(wp.getURI());
            //wp=super.getTaskWebPage();
        }
        return wp;
    }

}
