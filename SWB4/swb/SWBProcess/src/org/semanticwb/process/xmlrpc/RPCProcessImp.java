/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import java.util.ArrayList;
import java.util.List;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class RPCProcessImp extends XmlRpcObject implements RPCProcess
{

    @Override
    public void closeProcessInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
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
        pi.close(u, closeStatus, closeAction);
    }

    @Override
    public void closeTaskInstance(String APIKey, String UserID, String UserGroupsIDs, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        FlowNodeInstance pi = FlowNodeInstance.ClassMgr.getFlowNodeInstance(InstanceID, site);
        if (pi == null)
        {
            throw new Exception("The FlowNodeInstance with id " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        pi.close(u, closeStatus, closeAction);
    }

    @Override
    public String[] getProcessInstances(String APIKey, String processSiteID, int instanceStatus, String SiteID) throws Exception
    {
        List<String> getProcessInstances = new ArrayList<String>();
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }

        ProcessSite p = ProcessSite.ClassMgr.getProcessSite(processSiteID);
        if (p == null)
        {
            throw new Exception("The ProcessSite with id " + SiteID + " was not found");
        }
        GenericIterator<ProcessInstance> gi = SWBProcessMgr.getProcessInstanceWithStatus(p, instanceStatus);
        while (gi.hasNext())
        {
            ProcessInstance pi = gi.next();
            getProcessInstances.add(pi.getId());
        }
        return getProcessInstances.toArray(new String[getProcessInstances.size()]);
    }

    /*@Override
    public List<String> getProcessInstances(String APIKey, int instanceStatus, String SiteID) throws Exception
    {
    List<String> getProcessInstances = new ArrayList<String>();
    WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
    if (site == null)
    {
    throw new Exception("The site " + SiteID + " was not found");
    }
    Iterator<ProcessSite> sites = ProcessSite.ClassMgr.listProcessSites(site);
    while (sites.hasNext())
    {
    ProcessSite p = sites.next();
    List<String> tmp=getProcessInstances(APIKey, p.getId(), instanceStatus, SiteID);
    getProcessInstances.addAll(tmp);
    }
    return getProcessInstances;
    }*/
    @Override
    public String[] listUserTaskInstances(String APIKey, String UserID, String processSiteID, String ProcessID, int instanceStatus, String SiteID) throws Exception
    {
        List<String> listUserTaskInstances = new ArrayList<String>();
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        ProcessSite processSite = ProcessSite.ClassMgr.getProcessSite(processSiteID);
        if (processSite == null)
        {
            throw new Exception("The processSite with id " + processSiteID + " was not found");
        }
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        if (p == null)
        {
            throw new Exception("The Process with id " + ProcessID + " was not found");
        }
        List<FlowNodeInstance> flows = SWBProcessMgr.getUserTaskInstancesWithStatus(processSite, instanceStatus, p);
        for (FlowNodeInstance flow : flows)
        {
            listUserTaskInstances.add(flow.getId());
        }
        return listUserTaskInstances.toArray(new String[listUserTaskInstances.size()]);
    }

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
}
