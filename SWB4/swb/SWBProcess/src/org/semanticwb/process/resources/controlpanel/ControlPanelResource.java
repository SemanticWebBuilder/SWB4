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
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.UserTask;
import org.semanticwb.process.resources.taskinbox.UserTaskInboxResource;
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
    public static final String MODE_DETAIL="detail";
    public static final String MODE_CONFIG="config";
    public static final String MODE_SHOWFILES="showFiles";
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
        if (MODE_CONFIG.equals(mode)) {
            doConfig(request, response, paramRequest);
        } else if (MODE_SHOWFILES.equals(mode)) {
            doShowFiles(request, response, paramRequest);
        } else if (MODE_DETAIL.equals(mode)) {
            doDetail(request, response, paramRequest);
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

        //Asignaciones de compatibilidad con JSP anterior
        if (isShowInstanceGraph()) {
            getResourceBase().setAttribute(UserTaskInboxResource.ATT_INSTANCEGRAPH, "use");
        } else {
            getResourceBase().removeAttribute(UserTaskInboxResource.ATT_INSTANCEGRAPH);
        }
        
        if (isShowResponseGraph()) {
            getResourceBase().setAttribute(UserTaskInboxResource.ATT_RESPONSEGRAPH, "use");
        } else {
            getResourceBase().removeAttribute(UserTaskInboxResource.ATT_RESPONSEGRAPH);
        }
                
        if (isShowStatusGraph()) {
            getResourceBase().setAttribute(UserTaskInboxResource.ATT_STATUSGRAPH, "use");
        } else {
            getResourceBase().removeAttribute(UserTaskInboxResource.ATT_STATUSGRAPH);
        }
                
        if (isShowPartGraph()) {
            getResourceBase().setAttribute(UserTaskInboxResource.ATT_PARTGRAPH, "use");
        } else {
            getResourceBase().removeAttribute(UserTaskInboxResource.ATT_PARTGRAPH);
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
    
    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/monitor/processDetail.jsp";

        try {
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            request.setAttribute("paramRequest", paramRequest);
            if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
                request.setAttribute("instances", getDetailProcessInstances(request, paramRequest));
                request.setAttribute("statusWp", getDisplayMapWp());
                request.setAttribute("itemsPerPage", getItemsPerPage());
                request.setAttribute("base", getResourceBase());
            }
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error including jsp in detail mode", e);
        }
    }
    
    public ArrayList<ProcessInstance> getDetailProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<ProcessInstance> unpaged = new ArrayList<ProcessInstance>();
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        String suri = request.getParameter("suri");
        Process p = (Process)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
        HashMap<User, Integer> participantCount = new HashMap<User, Integer>();
        
        if (p != null) {
            int page = 1;
            int processing = 0;
            int aborted = 0;
            int closed = 0;
            int delayed = 0;
            int ontime = 0;
            long maxTime = -3;
            long minTime = Long.MAX_VALUE;
            long sumTime = 0;
            int itemsPerPage = getItemsPerPage();
            
            Iterator<ProcessInstance> it = SWBComparator.sortByCreated(p.listProcessInstances(), false);
            while (it.hasNext()) {
                ProcessInstance pi = it.next();
                
                //Conteo de instancias
                if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) {
//                    if (pi.getCreator() != null) {
//                        if (participantCount.get(pi.getCreator()) == null) {
//                            participantCount.put(pi.getCreator(), new Integer(1));
//                        } else {
//                            participantCount.put(pi.getCreator(), participantCount.get(pi.getCreator())+1);
//                        }
//                    }
//                    if (pi.getAssignedto()!= null) {
//                        if (participantCount.get(pi.getAssignedto()) == null) {
//                            participantCount.put(pi.getAssignedto(), new Integer(1));
//                        } else {
//                            participantCount.put(pi.getAssignedto(), participantCount.get(pi.getAssignedto())+1);
//                        }
//                    }
                    boolean isDelayed = false;
                    //Verifica retraso
                    Iterator<FlowNodeInstance> itfni = pi.listAllFlowNodeInstance();
                    while (itfni.hasNext() && !isDelayed) {
                        FlowNodeInstance fni = itfni.next();
                        if (fni.getFlowNodeType() instanceof UserTask) {
                            if (fni.getStatus() == FlowNodeInstance.STATUS_PROCESSING) {
//                                if (fni.getCreator() != null) {
//                                    if (participantCount.get(fni.getCreator()) == null) {
//                                        participantCount.put(fni.getCreator(), new Integer(1));
//                                    } else {
//                                        participantCount.put(fni.getCreator(), participantCount.get(fni.getCreator())+1);
//                                    }
//                                }
//                                if (fni.getAssignedto()!= null) {
//                                    if (participantCount.get(fni.getAssignedto()) == null) {
//                                        participantCount.put(fni.getAssignedto(), new Integer(1));
//                                    } else {
//                                        participantCount.put(fni.getAssignedto(), participantCount.get(fni.getAssignedto())+1);
//                                    }
//                                }
                                
                                UserTask ut = (UserTask) fni.getFlowNodeType();
                                int delay = ut.getNotificationTime();

                                if (delay > 0) {
                                    long today = System.currentTimeMillis();
                                    long cr = fni.getCreated().getTime();
                                    if (today - cr > (1000*60*delay)) {
                                        isDelayed = true;
                                    }
                                }
                            }
                            
                            if (fni.getStatus() == FlowNodeInstance.STATUS_CLOSED) {
                                if (fni.getEndedby() != null) {
                                    if (participantCount.get(fni.getEndedby()) == null) {
                                        participantCount.put(fni.getEndedby(), new Integer(1));
                                    } else {
                                        participantCount.put(fni.getEndedby(), participantCount.get(fni.getEndedby())+1);
                                    }
                                }
                            }
                        }
                    }
                    
                    if (isDelayed) {
                        delayed++;
                    } else {
                        ontime++;
                    }
                    processing++;
                }
                if (pi.getStatus() == ProcessInstance.STATUS_ABORTED) aborted++;
                if (pi.getStatus() == ProcessInstance.STATUS_CLOSED) {
                    if (pi.getEndedby() != null) {
                        if (participantCount.get(pi.getEndedby()) == null) {
                            participantCount.put(pi.getEndedby(), new Integer(1));
                        } else {
                            participantCount.put(pi.getEndedby(), participantCount.get(pi.getEndedby())+1);
                        }
                    }
                    
                    //Cálculo de tiempos de respuesta generales
                    long resTime = pi.getEnded().getTime() - pi.getCreated().getTime();
                    if (resTime > maxTime) {
                        maxTime = resTime;
                    }
                    
                    if(resTime < minTime) {
                        minTime = resTime;
                    }
                    sumTime += resTime;
                    closed++;
                }
                unpaged.add(pi);
            }
            
            request.setAttribute("processing", processing);
            request.setAttribute("aborted", aborted);
            request.setAttribute("closed", closed);
            request.setAttribute("delayed", delayed);
            request.setAttribute("ontime", ontime);
            
            if (closed > 0) {
                request.setAttribute("minTime", TimeUnit.MILLISECONDS.toMinutes(minTime));
                request.setAttribute("maxTime", TimeUnit.MILLISECONDS.toMinutes(maxTime));
                request.setAttribute("avgTime", TimeUnit.MILLISECONDS.toMinutes(sumTime/closed));
            } else {
                request.setAttribute("minTime", 0L);
                request.setAttribute("maxTime", 0L);
                request.setAttribute("avgTime", 0L);
            }
        
            //Realizar paginado de instancias
            int maxPages = 1;
            if (request.getParameter("p") != null && !request.getParameter("p").trim().equals("")) {
                page = Integer.valueOf(request.getParameter("p"));
                if (page < 0) page = 1;
            }

            if (itemsPerPage < 5) itemsPerPage = 5;

            if (unpaged.size() >= itemsPerPage) {
                maxPages = (int)Math.ceil((double)unpaged.size() / itemsPerPage);
            }
            if (page > maxPages) page = maxPages;

            int sIndex = (page - 1) * itemsPerPage;
            if (unpaged.size() > itemsPerPage && sIndex > unpaged.size() - 1) {
                sIndex = unpaged.size() - itemsPerPage;
            }

            int eIndex = sIndex + itemsPerPage;
            if (eIndex >= unpaged.size()) eIndex = unpaged.size();

            request.setAttribute("maxPages", maxPages);
            
            for (int i = sIndex; i < eIndex; i++) {
                ProcessInstance instance = unpaged.get(i);
                instances.add(instance);
            }
            
            Iterator<User> uit = participantCount.keySet().iterator();
            if (uit.hasNext()) {
                int max = 0;
                User theUser = null;
                
                try {
                    JSONObject processInfo = new JSONObject();
                    processInfo.put("name", p.getTitle());
                    processInfo.put("size", 10);
                    processInfo.put("type", "process");

                    JSONArray users = new JSONArray();
                    while (uit.hasNext()) {
                        User key = uit.next();
                        int value = (Integer) participantCount.get(key);
                        if (value > max) {
                            max = value;
                            theUser = key;
                        }
                        
                        JSONObject jsonUser = new JSONObject();
                        jsonUser.put("name", key.getFullName()==null?"Anónimo":key.getFullName());
                        jsonUser.put("size", (10 + (value / 70)));
                        jsonUser.put("participa", value);
                        users.put(jsonUser);
                    }
                    processInfo.put("max", max);
                    processInfo.put("children", users);
                    processInfo.put("theUser", theUser.getFullName()==null?"Anónimo":theUser.getFullName());
                    
                    request.setAttribute("participation", processInfo.toString());
                } catch (JSONException ex) {
                    log.error("UserTaskInboxResource - Error al generar JSON", ex);
                }
            }
        }
        return instances;
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