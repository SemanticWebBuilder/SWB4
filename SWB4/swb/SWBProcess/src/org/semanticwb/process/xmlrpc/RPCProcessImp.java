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
package org.semanticwb.process.xmlrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.StartEvent;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 * Clase que implementa la interfaz de servicios del API de servicios de SWB Process.
 * @author victor.lorenzana
 */
public class RPCProcessImp extends XmlRpcObject implements RPCProcess
{
    /**
     * Cierra una instancia de proceso.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param UserID ID del usuario que cierra la instancia del proceso.
     * @param InstanceID ID de la instancia de proceso a cerrar.
     * @param closeStatus Código del estatus de cierre de la instancia.
     * @param closeAction Acción de cierre de la instancia.
     * @param SiteID ID del sitio Web al que pertenece la instancia.
     * @throws Exception 
     */
    @Override
    public void closeProcessInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        String act = ProcessInstance.ACTION_ACCEPT;
        int stat = ProcessInstance.STATUS_CLOSED;
        
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(InstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The user " + UserID + " was not found");
        }
        if (closeAction != null && closeAction.length()>0) {
            act = closeAction; 
        }
        if (closeStatus >= 2 && closeStatus <= 4) {
            stat = closeStatus;
        }
        pi.close(u, stat, act);
    }

    /**
     * Cierra una instancia de tarea de usuario.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param UserID ID del usuario que cierra la instancia de tarea.
     * @param InstanceID ID de la instancia de tarea a cerrar.
     * @param closeStatus Código del estatus de cierre de la tarea.
     * @param closeAction Acción de cierre de la instancia.
     * @param SiteID ID del sitio Web al que pertenece la instancia.
     * @throws Exception 
     */
    @Override
    public void closeTaskInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        String act = FlowNodeInstance.ACTION_ACCEPT;
        int stat = FlowNodeInstance.STATUS_CLOSED;
        
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        FlowNodeInstance fni = FlowNodeInstance.ClassMgr.getFlowNodeInstance(InstanceID, site);
        if (fni == null)
        {
            throw new Exception("The FlowNodeInstance with id " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        if (closeAction != null && closeAction.length()>0) {
            act = closeAction; 
        }
        if (closeStatus >= 2 && closeStatus <= 4) {
            stat = closeStatus;
        }
        fni.close(u, stat, act);
    }

    /**
     * Devuelve las instancias de procesos con cierto estado.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param instanceStatus Código de estatus para filtrar las tareas.
     * @param SiteID ID del sitio al que pertenecen las instancias.
     * @return Arreglo con la información de las intancias de proceso.
     * @throws Exception 
     */
    @Override
    public InstanceInfo[] listProcessInstances(String APIKey, int instanceStatus, String SiteID) throws Exception
    {
        List<InstanceInfo> getProcessInstances = new ArrayList<InstanceInfo>();
        ProcessSite p = ProcessSite.ClassMgr.getProcessSite(SiteID);
        
        if (p == null) {
            throw new Exception("The ProcessSite with id " + SiteID + " was not found");
        }
        Iterator<ProcessInstance> gi = SWBProcessMgr.getProcessInstanceWithStatus(p, instanceStatus);
        if (gi.hasNext()) {
            while (gi.hasNext()) {
                ProcessInstance pi = gi.next();
                getProcessInstances.add(getProcessInstanceInfo(pi));
            }
        }
        return getProcessInstances.toArray(new InstanceInfo[getProcessInstances.size()]);
    }

    /**
     * Devuelve la lista de instancias de tareas de usuario.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param UserID ID del usuario.
     * @param ProcessID ID del proceso al que pertencen las instancias de tareas de usuario (opcional).
     * @param instanceStatus Código de estatus de las instancias de tarea a recuperar.
     * @param SiteID ID del sitio al que pertenecen las instancias.
     * @return Arreglo con la información de las instancias de tareas de usuario.
     * @throws Exception 
     */
    @Override
    public FlowNodeInstanceInfo[] listUserTaskInstances(String APIKey, String UserID, String ProcessID, int instanceStatus, String SiteID) throws Exception
    {
        List<FlowNodeInstanceInfo> listUserTaskInstances = new ArrayList<FlowNodeInstanceInfo>();
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        User u = site.getUserRepository().getUser(UserID);
        int status = instanceStatus<0?FlowNodeInstance.STATUS_PROCESSING:instanceStatus;
        
        if (site == null) {
            throw new Exception("The processSite with id " + SiteID + " was not found");
        }
        
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        
        Iterator<FlowNodeInstance> flows = SWBProcessMgr.getUserTaskInstancesWithStatus(site, status, p).iterator();
        while (flows.hasNext()) {
            FlowNodeInstance fni = flows.next();
            if (validUserTaskInstance(fni, u, status)) {
                FlowNodeInstanceInfo info = getFlowNodeInstanceInfo(fni);
                listUserTaskInstances.add(info);
            }
        }
        listUserTaskInstances.toArray(new FlowNodeInstanceInfo[listUserTaskInstances.size()]);
        return listUserTaskInstances.toArray(new FlowNodeInstanceInfo[listUserTaskInstances.size()]);
    }

    /**
     * Devuelve el código de estatus de una instancia de proceso
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param processInstanceID ID de la instancia del proceso.
     * @param SiteID ID del sitio al que pertenece el proceso.
     * @return Código de estatus de la instancia del proceso.
     * @throws Exception 
     */
    @Override
    public int getProcessInstanceStatus(String APIKey, String processInstanceID, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(processInstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with id " + processInstanceID + " was not found");
        }
        return pi.getStatus();
    }
    
    /**
     * Crea una nueva instancia de un proceso.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param ProcessID ID del proceso del cual se creará una instancia.
     * @param UserID ID del usuario que crea la instancia.
     * @param SiteID ID del sitio al que pertenece el proceso.
     * @return ID de la instancia de proceso creada.
     * @throws Exception 
     */
    @Override
    public String createProcessInstance(String APIKey, String ProcessID, String UserID, String SiteID) throws Exception {
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        if (p == null) {
            throw new Exception("The Process with id " + ProcessID + " was not found");
        }
        ProcessInstance pi = SWBProcessMgr.createProcessInstance(p, u);
        //TODO: Checar por qué no se asigna
        pi.setCreator(u);
        return pi==null?null:pi.getId();
    }

    /**
     * Devuelve la lista de procesos accesibles por el usuario.
     * @param APIKey Llave del API establecida en los archivos de configuración.
     * @param UserID ID del usuario.
     * @param SiteID ID del sitio al que pertenecen los procesos.
     * @return Arreglo con la información de los procesos accesibles por el usuario.
     * @throws Exception 
     */
    @Override
    public ProcessInfo[] listUserProcesses(String APIKey, String UserID, String SiteID) throws Exception {
        List<ProcessInfo> processes = new ArrayList<ProcessInfo>();
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        User u = site.getUserRepository().getUser(UserID);
        
        if (site == null) {
            throw new Exception("The processSite with id " + SiteID + " was not found");
        }
        
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(site);
        while (startEvents.hasNext()) {
            StartEvent sevt = startEvents.next();
            if (sevt.getContainer() != null && sevt.getContainer() instanceof Process && u.haveAccess(sevt)) {
                org.semanticwb.process.model.Process itp = sevt.getProcess();
                if (itp != null && itp.isValid()) {
                    processes.add(getProcessInfo(itp));
                }
            }
        }
        return processes.toArray(new ProcessInfo[processes.size()]);
    }
    
    /**
     * Devuelve un objeto con la información de una instancia de proceso.
     * @param pi Instancia de proceso.
     * @return Objeto con la información de una instancia de proceso.
     * @throws Exception 
     */
    private InstanceInfo getProcessInstanceInfo(ProcessInstance pi) throws Exception{
        InstanceInfo ret = new InstanceInfo();
        ret.id = pi.getId();
        ret.title = pi.getProcessType().getTitle();
        ret.description = pi.getProcessType().getDescription();
        ret.status = pi.getStatus();
        ret.creator = pi.getCreator()==null?null:pi.getCreator().getId();
        ret.created = pi.getCreated();
        ret.closed = pi.getEnded();
        ret.closedBy = pi.getEndedby()==null?null:pi.getEndedby().getId();
        return ret;
    }
    
    /**
     * Devuelve un objeto con la información de un proceso
     * @param p Proceso.
     * @return objeto con la información de un proceso.
     * @throws Exception 
     */
    private ProcessInfo getProcessInfo(Process p) throws Exception {
        ProcessInfo ret = new ProcessInfo();
        ret.id = p.getId();
        ret.title = p.getTitle();
        ret.description = p.getDescription();
        ret.processGroup = p.getProcessGroup()==null?null:p.getProcessGroup().getId();
        return ret;
    }
    
    /**
     * Devuelve un objeto con la información de una instancia de tarea.
     * @param flow Instancia de tarea.
     * @return Objeto con la información de una instancia de tarea.
     * @throws Exception 
     */
    private FlowNodeInstanceInfo getFlowNodeInstanceInfo(FlowNodeInstance flow) throws Exception {
        FlowNodeInstanceInfo ret = new FlowNodeInstanceInfo();
        ret.id = flow.getId();
        ret.title = flow.getFlowNodeType().getTitle();
        ret.description = flow.getFlowNodeType().getDescription();
        ret.status = flow.getStatus();
        ret.creator = flow.getCreator()==null?null:flow.getCreator().getId();
        ret.assignedTo = flow.getAssignedto()==null?null:flow.getAssignedto().getId();
        ret.assigned = flow.getAssigned();
        ret.created = flow.getCreated();
        ret.closed = flow.getEnded();
        ret.closedBy = flow.getEndedby()==null?null:flow.getEndedby().getId();
        ret.subject = flow.getSubject();
        ret.processInstance = getProcessInstanceInfo(flow.getProcessInstance());
        return ret;
    }
    
    /**
     * Valida una instancia de tarea de usuario para filtrado.
     * @param fni Instancia de tarea.
     * @param user Usuario para validar.
     * @param statusFilter Filtro de estatus.
     * @return True si la instancia es accesible por el usuario y tiene el estatus determinado.
     */
    private boolean validUserTaskInstance(FlowNodeInstance fni, User user, int statusFilter) {
        boolean hasStatus = false;
        boolean canAccess = false;
        
        org.semanticwb.process.model.Process pType = fni.getProcessInstance().getProcessType();
        
        if (!pType.isValid()) {
            return false;
        }
        canAccess = fni.haveAccess(user);
        if (canAccess) {
            //Verificar filtrado por estatus
            if (statusFilter > 0 && fni.getStatus() == statusFilter) {
                hasStatus = true;
            }
        }
        return canAccess && hasStatus;
    }
}