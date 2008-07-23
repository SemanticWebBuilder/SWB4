/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.net.URI;

/**
 *
 * @author victor.lorenzana
 */
public interface XmlProxy
{
    public URI getWebAddress();
    public void setWebAddress(URI uri);
    
    public String getUser();
    public void setUser(String user);
    
    public String getPassword();
    public void setPassword(String password);
    
    public void addAttachment(Attachment attachment);
    public void clearAttachments();
    
    public URI getProxyAddress();
    public void setProxyAddress(URI proxyAddress);
    
    public int getProxyPort();
    public void setProxyPort(int port);
    
}
