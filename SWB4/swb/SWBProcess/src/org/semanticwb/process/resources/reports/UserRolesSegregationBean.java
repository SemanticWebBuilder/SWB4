/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources.reports;

/**
 *
 * @author hasdai
 */
public class UserRolesSegregationBean {
    private String userName;
    private String processName;
    private String taskName;
    private String roleName;
    
    public UserRolesSegregationBean(String uName, String pName, String tName, String rName) {
        this.userName = uName;
        this.processName = pName;
        this.taskName = tName;
        this.roleName = rName;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * @param uName the userName to set
     */
    public void setUserName(String uName) {
        this.userName = uName;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}