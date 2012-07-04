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

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.WrapperProcessWebPage;

/**
 *
 * @author juan.fernandez
 */
public class CreateProcessInstance extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url =  paramRequest.getActionUrl();
        Process process = null;
        String label = paramRequest.getLocaleString("lblCreateInstance");
        String pid = "";
        
        if (paramRequest.getArguments().containsKey("label")) {
            label = (String)paramRequest.getArguments().get("label");
        }
        if (paramRequest.getArguments().containsKey("process")) {
            pid = (String)paramRequest.getArguments().get("process");
        }
        
        if (paramRequest.getWebPage() instanceof WrapperProcessWebPage) {
            process = ((WrapperProcessWebPage)paramRequest.getWebPage()).getProcess();
        } else if (!pid.trim().equals("")) {
            process = Process.ClassMgr.getProcess(pid, paramRequest.getWebPage().getWebSite());
        }
        
        if (process != null) {
            url.setParameter("prid", process.getId());
            url.setAction("CREATE");
            out.println("<a href=\""+url.toString()+"\">"+label+"</a>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage wp = response.getWebPage();
        User user=response.getUser();
        String act = response.getAction();
        ProcessInstance inst = null;
        
        if (act.equals("CREATE")) {
            String pid = request.getParameter("prid");
            Process process = Process.ClassMgr.getProcess(pid, wp.getWebSite());
            
            if(process!=null) inst = SWBProcessMgr.createProcessInstance(process, user);
            
            String url=process.getProcessWebPage().getUrl();
            ResourceType rtype=ResourceType.ClassMgr.getResourceType("ProcessTaskInbox", wp.getWebSite());

            if (rtype != null) {
                Resource res=rtype.getResource();
                if(res!=null)
                {
                    Resourceable resable=res.getResourceable();
                    if(resable instanceof WebPage)
                    {
                        url=((WebPage)resable).getUrl();
                    }
                }
            }
            if (inst != null) {
                request.getSession(true).setAttribute("msg", "OK"+inst.getId());
            }
            response.sendRedirect(url);
        }
    }
}