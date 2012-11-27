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
package org.semanticwb.process.resources.tracer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.WrapperProcessWebPage;

public class ProcessTracer extends org.semanticwb.process.resources.tracer.base.ProcessTracerBase 
{
    public static Logger log = SWBUtils.getLogger(ProcessTracer.class);
    public static final int MODE_OVERVIEW = 1;
    public static final int MODE_TRACKING = 2;
    
    public ProcessTracer() {}

   /**
   * Constructs a ProcessTracer with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessTracer
   */
    public ProcessTracer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String jsp = "/swbadmin/jsp/process/listProcessInstances.jsp";
        
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("statusWP", getDisplayMapPage());
            request.setAttribute("instances", getProcessInstances(request, paramRequest));
            request.setAttribute("itemsPerPage", getItemsPerPage());
            request.setAttribute("viewMode", getViewMode());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ProcessTracer: Error including view JSP", e);
        }
    }
    
    /**
     * Obtiene las instancias de los procesos a desplegar.
     * @param request
     * @param paramRequest
     * @return Lista con las instancias de procesos.
     */
    private ArrayList<ProcessInstance> getProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<ProcessInstance> _ret = new ArrayList<ProcessInstance>();
        String piid = request.getParameter("prid");
        WebSite site = paramRequest.getWebPage().getWebSite();
        Process process = null;
        User creator = null;
        Iterator<ProcessInstance> it = null;
        int itemsPerPage = getItemsPerPage();
        int page = 1;
        
        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) page = 1;
        }
        
        if (itemsPerPage < 5) itemsPerPage = 5;
        
        if (getUserID() != null && getUserID().length() > 0) {
            creator = User.ClassMgr.getUser(getUserID(), site);
        }
        
        if (creator == null) {
            creator = paramRequest.getUser();
        }
        
        if (piid != null && piid.length() > 0) { // El proceso viene por parámetro
            process = Process.ClassMgr.getProcess(piid, site);
        }
        
        if (process == null && getProcessID() != null && getProcessID().length() > 0) { // El proceso fue configurado en la administración
            process = Process.ClassMgr.getProcess(getProcessID(), site);
        }
        
        if (process == null && paramRequest.getWebPage() instanceof WrapperProcessWebPage) { //El componente se encuentra en una página de proceso
            process = ((WrapperProcessWebPage)paramRequest.getWebPage()).getProcess();
        }

        if (getViewMode() == MODE_OVERVIEW) { //Modo de listado general
            if (isFilterByCreator()) {
                it = ProcessInstance.ClassMgr.listProcessInstanceByCreator(creator, site);
            } else {
                it = ProcessInstance.ClassMgr.listProcessInstances(site);
            }
        } else if (process != null) { //Modo de seguimiento de procesos
            it = ProcessInstance.ClassMgr.listProcessInstanceByProcessType(process);
        }
        
        if (it != null) {
            while (it.hasNext()) {
                ProcessInstance processInstance = it.next();
                _ret.add(processInstance);
            }
        }
        
        int maxPages = 1;
        if (_ret.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)_ret.size() / itemsPerPage);
        }
        if (page > maxPages) page = maxPages;

        int sIndex = (page - 1) * itemsPerPage;
        if (_ret.size() > itemsPerPage && sIndex > _ret.size() - 1) {
            sIndex = _ret.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= _ret.size()) eIndex = _ret.size();

        request.setAttribute("maxPages", maxPages);
        ArrayList<ProcessInstance> ret = new ArrayList<ProcessInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            ProcessInstance instance = _ret.get(i);
            ret.add(instance);
        }
        return ret;
    }
}