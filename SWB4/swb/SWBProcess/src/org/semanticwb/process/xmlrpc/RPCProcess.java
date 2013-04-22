/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.xmlrpc;

import java.util.List;

/**
 *
 * @author victor.lorenzana
 */
public interface RPCProcess {

    public void closeProcessInstance(String APIKey,String UserID,String InstanceID,int closeStatus,String closeAction,String SiteID) throws Exception;
    public void closeTaskInstance(String APIKey, String UserID, String UserGroupsIDs, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception;
    public List<String> getProcessInstances(String APIKey, String processSiteID, int instanceStatus, String SiteID) throws Exception;
    public List<String> listUserTaskInstances(String APIKey, String UserID, String processSiteID,String ProcessID, int instanceStatus, String SiteID) throws Exception;
    public int getProcessInstanceStatus(String APIKey, String processInstanceID, String SiteID) throws Exception;
}
