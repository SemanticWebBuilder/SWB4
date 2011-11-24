package org.semanticwb.process.resources.reports;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hasdai
 */
public class TaskBean {
    private String taskName;
    private ArrayList<UserBean> users;
    private ArrayList<String> roles;
    
    public TaskBean(String name) {
        this.taskName = name;
        users = new ArrayList<UserBean>();
        roles = new ArrayList<String>();
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
     * @return the users
     */
    public ArrayList<UserBean> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<UserBean> users) {
        this.users = users;
    }

    /**
     * @return the roles
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    
    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }
    
    public boolean hasRole(String role) {
        return roles.contains(role);
    }
    
    public void addUser(UserBean uBean) {
        users.add(uBean);
    }
}
