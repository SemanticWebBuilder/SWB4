/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

import java.util.Map;




/**
 *
 * @author victor.lorenzana
 */
public final class UserInfo 
{
    private String password, login;
    private Map<String,String> cookies=new java.util.HashMap<String,String>();
    public UserInfo(String password,String login)
    {
        this.login=login;
        this.password=password;
    }
    public Map<String,String> getCookies()
    {        
        return cookies;
    }
    public String getLogin()
    {
        return this.login;
    }
    public void getSession()
    {
        
    }
    public String getPassword()
    {
        return password;
    }
    void changePassword(String newPassword,String oldPassword)
    {
        this.password=newPassword;
        /*if(!oldPassword.equals(password))
        {
            // muestra mensaje de error
        }
        else
        {
            // cambia el password        
        }*/
    }
}
