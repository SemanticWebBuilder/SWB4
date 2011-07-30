package org.semanticwb.process.resources.controlpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.ProcessSite;

/***
 * Recurso Panel de Control para monitoreo de instancias de procesos.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class ControlPanelResource extends org.semanticwb.process.resources.controlpanel.base.ControlPanelResourceBase 
{
    private static Logger log = SWBUtils.getLogger(ControlPanelResource.class);
    public static final int SORT_DATE = 1;
    public static final int SORT_NAME = 2;
    public static final int STATUS_ALL = -1;
    private Comparator processNameComparator = new Comparator() {
        String lang = "es";
        public void Comparator (String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            return ((ProcessInstance)t).getProcessType().getDisplayTitle(lang).compareTo(((ProcessInstance)t1).getProcessType().getDisplayTitle(lang));
        }
    };

    public ControlPanelResource()
    {
    }

   /**
   * Construye una nueva instancia de un ControlPanelResource dado un SemanticObject
   * @param base El SemanticObject con las propiedades para el ControlPanelResource
   */
    public ControlPanelResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/businessControlPanel.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getProcessInstances(request, paramRequest));
            rd.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /***
     * Obtiene la lista de insancias de los procesos existentes en el
     * sitio Web de procesos aplicando los filtros y ordenamiento indicados.
     * @param request Objeto HttpServletRequest
     * @param paramRequest Objeto SWBParamRequest
     * @return Lista de instancias de procesos filtradas y ordenadas.
     */
    private ArrayList<ProcessInstance> getProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        WebSite site = paramRequest.getWebPage().getWebSite();
        ProcessGroup group = null;
        String sortType = request.getParameter("sort");
        int statusFilter = STATUS_ALL;

        if (sortType == null || sortType.trim().equals("")) {
            sortType = "date";
        } else {
            sortType = sortType.trim();
        }

        if (request.getParameter("sFilter") != null && !request.getParameter("sFilter").trim().equals("")) {
            statusFilter = Integer.valueOf(request.getParameter("sFilter"));
        }
        
        if (request.getParameter("gFilter") != null && !request.getParameter("gFilter").trim().equals("")) {
            group = ProcessGroup.ClassMgr.getProcessGroup(request.getParameter("gFilter"), site);
        }

        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
        if (processes != null && processes.hasNext()) {
            while(processes.hasNext()) {
                Process process = processes.next();
                instances.addAll(_getProcessInstances((ProcessSite)site, group, process, statusFilter));
            }
        }

        Iterator<ProcessInstance> it_ins = null;
        if (sortType.equals("date")) {
            it_ins = SWBComparator.sortByCreated(instances.iterator());            
        } else if (sortType.equals("name")) {
            it_ins = SWBComparator.sortSermanticObjects(processNameComparator, instances.iterator());
        }

        if (it_ins != null) {
            instances = new ArrayList<ProcessInstance>();
            while (it_ins.hasNext()) {
                ProcessInstance processInstance = it_ins.next();
                instances.add(processInstance);
            }
        }

        return instances;
    }

    /***
     * Obtiene la lista de instancias de procesos de un sitio filtradas por
     * grupo y/o estado.
     * @param site Sitio Web de Procesos del cual se listarán los procesos.
     * @param group Grupo al que deberán pertenecer los procesos.
     * @param process Tipo de proceso del cual se recuperarán las instancias.
     * @param status Estado de las instancias que serán listadas.
     * @return Lista de instancias de procesos filtradas por grupo y/o estado.
     */
    ArrayList<ProcessInstance> _getProcessInstances(ProcessSite site, ProcessGroup group, Process process, int status) {
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        Iterator<ProcessInstance> it=process.listProcessInstances();
        while (it.hasNext()) {
            ProcessInstance processInstance = (ProcessInstance)it.next();
            if (status >= 0) {
                if (group != null) {
                    if (processInstance.getStatus() == status && process.getProcessGroup().getURI().equals(group.getURI())) {
                        instances.add(processInstance);
                    }
                } else {
                    if (processInstance.getStatus() == status) {
                        instances.add(processInstance);
                    }
                }
            } else if (group != null) {
                if (process.getProcessGroup().getURI().equals(group.getURI())) {
                    instances.add(processInstance);
                }
            } else {
                instances.add(processInstance);
            }
        }
        return instances;
    }
}