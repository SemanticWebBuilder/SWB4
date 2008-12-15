/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.util.Set;
import javax.servlet.ServletConfig;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcObject {
    protected Set<Part> parts; 
    protected ServletConfig config;
    protected String user;
    protected String password;
    public void setParts(Set<Part> parts)
    {
        this.parts=parts;
    } 
    public void clearParts()
    {
        if(this.parts!=null)
        {
            this.parts.clear();
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
