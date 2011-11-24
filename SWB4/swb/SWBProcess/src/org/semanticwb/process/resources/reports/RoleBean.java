/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources.reports;

/**
 *
 * @author hasdai
 */
public class RoleBean {
    private String roleName;
    
    public RoleBean(String name) {
        this.roleName = name;
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
