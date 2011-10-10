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
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 *
 * @author javier.solis
 */
public class SWBProcessMgr
{

    public static List<ProcessInstance> getActiveProcessInstance(ProcessSite site, Process process)
    {
        ArrayList ret=new ArrayList();
        
        Iterator<ProcessInstance> it=process.listProcessInstances();
        while (it.hasNext())
        {
            ProcessInstance processInstance = (ProcessInstance)it.next();
            if(processInstance.getStatus()==Instance.STATUS_PROCESSING)
            {
                ret.add(processInstance);
            }
        }
        return ret;
    }

    public static ProcessInstance createProcessInstance(Process process, User user)
    {
        ProcessInstance pinst=process.createInstance();
        pinst.start(user);
        return pinst;
    }

    public static List<FlowNodeInstance> getUserTaskInstances(ContainerInstanceable pinst, User user)
    {
        ArrayList ret=new ArrayList();
        Iterator<FlowNodeInstance> it=pinst.listFlowNodeInstances();
        while(it.hasNext())
        {
            FlowNodeInstance actins=it.next();
            FlowNode type=actins.getFlowNodeType();
            if(actins instanceof SubProcessInstance)
            {
                SubProcessInstance processInstance=(SubProcessInstance)actins;
                if(processInstance.getStatus()==Instance.STATUS_PROCESSING || processInstance.getStatus()==Instance.STATUS_OPEN)
                {
                    List aux=getUserTaskInstances((SubProcessInstance)actins, user);
                    ret.addAll(aux);
                }
            }else if(type instanceof Task)
            {
                if(actins.getStatus()==Instance.STATUS_PROCESSING || actins.getStatus()==Instance.STATUS_OPEN)
                {
                    if(user.haveAccess(type))
                        ret.add(actins);
                }
            }
        }
        return ret;
    }

    public static Process getProcess(WebPage page)
    {
        Process ret=null;
        if(page!=null)
        {
            if(page instanceof ProcessWebPage)
            {
                ret=((ProcessWebPage)page).getProcess();
            }else
            {
                ret=getProcess(page.getParent());
            }
        }
        return ret;
    }


}
