/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletConfig;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcObject {
    protected Set<Part> requestParts;
    protected Set<Part> responseParts=new HashSet<Part>();
    protected ServletConfig config;
    protected String user;
    protected String password;
    public void setRequestParts(Set<Part> parts)
    {
        this.requestParts=parts;
    } 
    public void clearRequestParts()
    {
        if(this.requestParts!=null)
        {
            this.requestParts.clear();
        }
    }
    public void setResponseParts(Set<Part> parts)
    {
        this.responseParts=parts;
    }
    public void clearResponseParts()
    {
        if(this.responseParts!=null)
        {
            this.responseParts.clear();
        }
    }
    public void setUser(String user)
    {
        this.user=user;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public void init(ServletConfig config)
    {
        this.config=config;
    }
}
