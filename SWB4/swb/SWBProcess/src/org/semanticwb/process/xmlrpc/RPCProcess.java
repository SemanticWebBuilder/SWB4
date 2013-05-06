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

import org.semanticwb.xmlrpc.XmlRpcDescription;
import org.semanticwb.xmlrpc.XmlRpcMethod;
import org.semanticwb.xmlrpc.XmlRpcParam;

/**
 * Interfaz que define los servicios RPC del API de servicios de SWB Process.
 * @author victor.lorenzana
 */
public interface RPCProcess  {
    //En esta interfaz se deberán agregar las definiciones de los servicios del API
    @XmlRpcMethod(methodName="RPCProcess.closeProcessInstance")
    @XmlRpcDescription(description="Closes a process instance")
    public void closeProcessInstance(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the user who closes the instance") @XmlRpcParam(name="UserID")String UserID,
        @XmlRpcDescription(description="ID of the instance to close") @XmlRpcParam(name="InstanceID")String InstanceID,
        @XmlRpcDescription(description="Close status") @XmlRpcParam(name="closeStatus")int closeStatus,
        @XmlRpcDescription(description="Close action") @XmlRpcParam(name="closeAction")String closeAction,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID) 
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.closeTaskInstance")
    @XmlRpcDescription(description="Closes a usertask instance")
    public void closeTaskInstance(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the user who closes the instance") @XmlRpcParam(name="UserID")String UserID, 
        @XmlRpcDescription(description="ID of the instance to close") @XmlRpcParam(name="InstanceID")String InstanceID, 
        @XmlRpcDescription(description="Close status") @XmlRpcParam(name="closeStatus")int closeStatus,
        @XmlRpcDescription(description="Close action") @XmlRpcParam(name="closeAction")String closeAction,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID) 
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.listProcessInstances")
    @XmlRpcDescription(description="Gets the process instances with the given instanceStatus and SiteID")
    public InstanceInfo[] listProcessInstances(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="Status to filter returned instances") @XmlRpcParam(name="instanceStatus")int instanceStatus,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID)
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.listUserTaskInstances")
    @XmlRpcDescription(description="Gets the usertask instances with the given instanceStatus and SiteID")
    public FlowNodeInstanceInfo[] listUserTaskInstances(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the user to filter returned instances") @XmlRpcParam(name="UserID")String UserID, 
        @XmlRpcDescription(description="ID of the process to filter returned instances") @XmlRpcParam(name="ProcessID")String ProcessID,
        @XmlRpcDescription(description="Status to filter returned instances") @XmlRpcParam(name="instanceStatus")int instanceStatus,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID)
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.getProcessInstanceStatus")
    @XmlRpcDescription(description="Gets the status of the process instance with the given processInstanceID")
    public int getProcessInstanceStatus(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the instance whose status will be checked") @XmlRpcParam(name="processInstanceID")String processInstanceID,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID)
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.createProcessInstance")
    @XmlRpcDescription(description="Creates a new process instance")
    public String createProcessInstance(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the process") @XmlRpcParam(name="ProcessID")String ProcessID,
        @XmlRpcDescription(description="ID of the user who creates the instances") @XmlRpcParam(name="UserID")String UserID,
        @XmlRpcDescription(description="ID of the site which the process belongs to") @XmlRpcParam(name="SiteID")String SiteID)
    throws Exception;
    
    @XmlRpcMethod(methodName="RPCProcess.listUserProcesses")
    @XmlRpcDescription(description="Gets the processes that the user has acces to")
    public ProcessInfo [] listUserProcesses(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,
        @XmlRpcDescription(description="ID of the user") @XmlRpcParam(name="UserID")String UserID,
        @XmlRpcDescription(description="ID of the site which the processes belong to") @XmlRpcParam(name="SiteID")String SiteID)
    throws Exception;
}
