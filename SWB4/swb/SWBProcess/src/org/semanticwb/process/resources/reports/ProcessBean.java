package org.semanticwb.process.resources.reports;


import java.util.ArrayList;

/**
 *
 * @author hasdai
 */
public class ProcessBean {
    private String processName;
    private ArrayList<RoleBean> roles;
    private ArrayList<TaskBean> tasks;

    public ProcessBean(String pName) {
        this.processName = pName;
        roles = new ArrayList<RoleBean>();
        tasks = new ArrayList<TaskBean>();
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
     * @return the roles
     */
    public ArrayList<RoleBean> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(ArrayList<RoleBean> roles) {
        this.roles = roles;
    }

    /**
     * @return the tasks
     */
    public ArrayList<TaskBean> getTasks() {
        return tasks;
    }

    /**
     * @param tasks the tasks to set
     */
    public void setTasks(ArrayList<TaskBean> tasks) {
        this.tasks = tasks;
    }
    
}
