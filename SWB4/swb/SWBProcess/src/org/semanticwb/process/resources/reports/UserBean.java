package org.semanticwb.process.resources.reports;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hasdai
 */
public class UserBean {
  private String userName;
  
  public UserBean (String name) {
      this.userName = name;
  }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
