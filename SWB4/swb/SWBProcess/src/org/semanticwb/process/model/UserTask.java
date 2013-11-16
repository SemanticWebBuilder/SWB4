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

import java.util.Date;
import java.util.List;
import java.util.Random;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.process.utils.SWBScriptParser;

public class UserTask extends org.semanticwb.process.model.base.UserTaskBase 
{
    private static Logger log=SWBUtils.getLogger(UserTask.class);
    public static final int START_ACTIONCODE=1;
    public static final int CLOSE_ACTIONCODE=2;
    public static final int ABORT_ACTIONCODE=3;
    public static final int ASSIGN_ACTIONCODE=4;
    
    public UserTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        getTaskWebPage();
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        //System.out.println("execute:"+instance+" "+user);
        if(getResourceAssignationRule()>0 && instance.getAssignedto()==null)
        {
            if(getResourceAssignationRule()<4) // De momento todos son aleatorios
            {
                List<User> users=SWBProcessMgr.getUsers(instance);
                int s=users.size();
                User assigned=users.get(new Random().nextInt(s));                
                instance.setAssignedto(assigned);
                //System.out.println("assigned:"+assigned);
                NotificationTemplate tpl=getProcess().getAssigmentNotificationTemplate();
                if(tpl!=null)
                {
                    try
                    {
                        //System.out.println("send mail:"+assigned);
                        SWBUtils.EMAIL.sendBGEmail(assigned.getEmail(), SWBScriptParser.parser(instance, assigned, tpl.getSubject()), SWBScriptParser.parser(instance, assigned, tpl.getBody()));
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            }else if(getResourceAssignationRule()==4) // Usuario Creador del Proceso
            {
                instance.setAssignedto(instance.getProcessInstance().getCreator());
            }else if(getResourceAssignationRule()==5) // Usuario Creador de la Tarea
            {
                instance.setAssignedto(user);
            }
            instance.setAssigned(new Date());
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