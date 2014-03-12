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
package org.semanticwb.process.resources.controlpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.schema.File;
import org.semanticwb.process.schema.FileCollection;

/***
 * Recurso Panel de Control para monitoreo de instancias de procesos.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class ControlPanelResource extends org.semanticwb.process.resources.controlpanel.base.ControlPanelResourceBase 
{
    private static Logger log = SWBUtils.getLogger(ControlPanelResource.class);
    public static final int SORT_DATE = 1;
    public static final int SORT_NAME = 2;
    public static final int STATUS_ALL = -1;
    private static final String paramCatalog = "idCol|priorityCol|nameCol|sdateCol|edateCol|pendingCol|rolesCol|actionsCol";
    private Comparator processNameComparator = new Comparator() {
        String lang = "es";
        public void Comparator (String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            return ((ProcessInstance)t).getProcessType().getDisplayTitle(lang).compareTo(((ProcessInstance)t1).getProcessType().getDisplayTitle(lang));
        }
    };
    private Comparator processNameComparatorDesc = new Comparator() {
        String lang = "es";
        public void Comparator (String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            return ((ProcessInstance)t1).getProcessType().getDisplayTitle(lang).compareTo(((ProcessInstance)t).getProcessType().getDisplayTitle(lang));
        }
    };
    private Comparator processPriorityComparator = new Comparator() {
        String lang = "es";
        
        public int compare(Object t, Object t1) {
            int it1 = ((ProcessInstance)t).getPriority();
            int it2 = ((ProcessInstance)t1).getPriority();
            int ret = 0;

            if (it1 > it2) ret = 1;
            if (it1 < it2) ret = -1;
            return ret;
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("config")) {
            doConfig(request, response, paramRequest);
        } else if (mode.equals("showFiles")) {
            doShowFiles(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("setDisplay")) {
            Enumeration params = request.getParameterNames();
            String dCols = "";
            while(params.hasMoreElements()) {
                String param = (String)params.nextElement();
                if (paramCatalog.contains(param)) {
                    dCols += param;
                    if (params.hasMoreElements()) {
                        dCols += "|";
                    }
                }
            }
            setDisplayCols(dCols+"|actionsCol");
        } else if (action.equals("create")) {
            User user = response.getUser();
            String pid = request.getParameter("pid");
            if (pid != null && !pid.trim().equals("")) {
                Process p = Process.ClassMgr.getProcess(pid, response.getWebPage().getWebSite());
                SWBProcessMgr.createProcessInstance(p, user);
            }
        } else if (action.equals("setPageItems")) {
            String ipp = request.getParameter("ipp");
            int itemsPerPage = 0;
            
            if (ipp == null || ipp.trim().equals("")) {
                ipp = "5";
            }
            
            try {
                itemsPerPage = Integer.parseInt(ipp);
            } catch (NumberFormatException e) {
                log.error("UserTaskInboxResource",e);
            }
            setItemsPerPage(itemsPerPage);
        } else if (action.equals(response.Action_REMOVE)) {
            String pid = request.getParameter("pid");
            if (pid != null && !pid.trim().equals("")) {
                ProcessInstance instance = ProcessInstance.ClassMgr.getProcessInstance(pid, response.getWebPage().getWebSite());
                instance.remove();
            }
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/monitor/businessControlPanel.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanel.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        if (getDisplayCols() == null || getDisplayCols().trim().equals("")) {
            setDisplayCols("idCol|nameCol|sdateCol|edateCol|pendingCol|actionsCol");
        }

        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getProcessInstances(request, paramRequest));
            request.setAttribute("displayCols", getDisplayCols());
            request.setAttribute("statusWp", getDisplayMapWp());
            request.setAttribute("trackWp", getTrackWp());
            request.setAttribute("itemsPerPage", getItemsPerPage());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including view JSP", e);
        }
    }
    
    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/monitor/businessControlPanelConfig.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanelConfig.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("displayCols", getDisplayCols());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including config JSP", e);
        }
    }
    
    public void doShowObjects(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/monitor/businessControlPanelObjects.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanelObjects.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        String pid = request.getParameter("pid");
        ArrayList<ItemAware> iaw = null;
        
        if (pid != null && !pid.trim().equals("")) {
            ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(pid, paramRequest.getWebPage().getWebSite());
            if (pi != null) {
                iaw = getProcessItemawareList(pi);
            }
        }
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("itemawareList", iaw);
            request.setAttribute("itemsPerPage", getItemsPerPage());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including view JSP", e);
        }
    }
    
    public void doShowFiles(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/monitor/businessControlPanelFiles.jsp";
        if (getDocsJSP() != null && !getDocsJSP().trim().equals("")) {
            jsp = getDocsJSP();
        }
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanelFiles.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        String pid = request.getParameter("pid");
        User user = paramRequest.getUser();
        
        String lang = "es";
        if (user != null && user.getLanguage() != null) {
            lang = user.getLanguage();
        }
        
        ArrayList<RepositoryFile> docs = null;
        ProcessInstance pi = null;
        
        if (pid != null && !pid.trim().equals("")) {
            pi = ProcessInstance.ClassMgr.getProcessInstance(pid, paramRequest.getWebPage().getWebSite());
            if (pi != null) {
                docs = getProcessRepositoryFiles(pi);
            }
        }
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("docs", docs);
            if (pi != null) {
                request.setAttribute("pName", pi.getProcessElementType().getTitle(lang));
            }
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including show docs JSP", e);
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
        ArrayList<ProcessInstance> t_instances = new ArrayList<ProcessInstance>();
        WebSite site = paramRequest.getWebPage().getWebSite();
        ProcessGroup group = null;
        User user = paramRequest.getUser();
        String sortType = request.getParameter("sort");
        int itemsPerPage = getItemsPerPage();
        int statusFilter = ProcessInstance.STATUS_PROCESSING;
        int page = 1;

        if (sortType == null || sortType.trim().equals("")) {
            sortType = "1";
        } else {
            sortType = sortType.trim();
        }

        if (request.getParameter("p") != null && !request.getParameter("p").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("p"));
            if (page < 0) page = 1;
        }

        if (itemsPerPage < 5) itemsPerPage = 5;

        if (request.getParameter("sF") != null && !request.getParameter("sF").trim().equals("")) {
            statusFilter = Integer.valueOf(request.getParameter("sF"));
        }
        
        if (request.getParameter("gF") != null && !request.getParameter("gF").trim().equals("")) {
            group = ProcessGroup.ClassMgr.getProcessGroup(request.getParameter("gF"), site);
        }
        
        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
        if (group != null) {
            processes = group.listProcesses();
        }
        
        if (processes != null && processes.hasNext()) {
            while(processes.hasNext()) {
                Process process = processes.next();
                if (process.isValid()) {
                    t_instances.addAll(_getProcessInstances((ProcessSite)site, process, statusFilter));
                }
            }
        }

        Iterator<ProcessInstance> it_ins = null;
        if (sortType.equals("1")) {
            it_ins = SWBComparator.sortByCreated(t_instances.iterator(), false);
        } else if (sortType.equals("2")) {
            it_ins = SWBComparator.sortByCreated(t_instances.iterator(), true);
        } else if (sortType.equals("3")) {
            Collections.sort(t_instances, processNameComparator);
            it_ins = t_instances.iterator();
        } else if (sortType.equals("4")) {
            Collections.sort(t_instances, processNameComparatorDesc);
            it_ins = t_instances.iterator();
        } else if (sortType.equals("5")) {
            Collections.sort(t_instances, processPriorityComparator);
            it_ins = t_instances.iterator();
        }        

        if (it_ins != null) {
            t_instances = new ArrayList<ProcessInstance>();
            
            while (it_ins.hasNext()) {
                ProcessInstance processInstance = it_ins.next();
                
                if (isFilterByGroup()) { //Si hay que filtrar por grupo de usuarios
                    UserGroup iug = processInstance.getOwnerUserGroup();
                    UserGroup uug = user.getUserGroup();
                    
                    if (iug != null && uug != null) { //Si la instancia y el usuario tienen grupo
                        if (user.getUserGroup().getURI().equals(processInstance.getOwnerUserGroup().getURI())) { //Si tienen el mismo grupo
                            t_instances.add(processInstance);
                        }
                    } else if (iug == null && uug == null) { //Si el proceso y el usuario no tienen grupo
                        t_instances.add(processInstance);
                    }
                } else { //Si no hay que filtrar por grupo de usuarios
                    t_instances.add(processInstance);
                }
            }
        }

        int maxPages = 1;
        if (t_instances.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)t_instances.size() / itemsPerPage);
        }
        if (page > maxPages) page = maxPages;
        
        int sIndex = (page - 1) * itemsPerPage;
        if (t_instances.size() > itemsPerPage && sIndex > t_instances.size() - 1) {
            sIndex = t_instances.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= t_instances.size()) eIndex = t_instances.size();

        request.setAttribute("maxPages", maxPages);
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            ProcessInstance instance = t_instances.get(i);
            instances.add(instance);
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
    ArrayList<ProcessInstance> _getProcessInstances(ProcessSite site, Process process, int status) {
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        Iterator<ProcessInstance> it=process.listProcessInstances();
        
        while (it.hasNext()) {
            ProcessInstance processInstance = it.next();
            if (status >= 0) {
                if (processInstance.getStatus() == status) {
                    instances.add(processInstance);
                }
            } else {
                instances.add(processInstance);
            }
        }
        return instances;
    }
    
    /**
     * Obtiene la lista de objetos {@link ItemAware} relacionados con la instancia
     * del proceso.
     * @param pi Instancia del proceso.
     * @return Lista de objetos {@link ItemAware} relacionados con la instancia
     */
    public ArrayList<ItemAware> getProcessItemawareList(ProcessInstance pi) {
        ArrayList<ItemAware> ret = new ArrayList<ItemAware>();
        
        Iterator<ItemAwareReference> objit = pi.listAllItemAwareReferences();
        while (objit.hasNext()) {
            ItemAwareReference item=objit.next();
            ret.add(item.getItemAware());
        }
        return ret;
    }
    
    /**
     * Obtiene la lista de Archivos relacionados con la instancia del proceso.
     * @param pi Instancia del proceso
     * @return Lista de archivos relacionados con la instancia del proceso.
     */
    public ArrayList<RepositoryFile> getProcessRepositoryFiles(ProcessInstance pi) {
        ArrayList<RepositoryFile> ret = new ArrayList<RepositoryFile>();
        Iterator<ItemAwareReference> objit = pi.listAllItemAwareReferences();
        while (objit.hasNext()) {
            ItemAwareReference item=objit.next();
            SWBClass obj = item.getProcessObject();

            if (obj != null) {
                if (obj instanceof File) {
                    File f = (File)obj;
                    if (f.getRepositoryFile() != null) {
                        ret.add(f.getRepositoryFile());
                    }
                }
                if (obj instanceof FileCollection) {
                    FileCollection f = (FileCollection)obj;
                    Iterator<RepositoryFile> rfs = f.listRepositoryFiles();
                    while (rfs.hasNext()) {
                        RepositoryFile repositoryFile = rfs.next();
                        ret.add(repositoryFile);
                    }
                }
            }
        }
        return ret;
    }
}