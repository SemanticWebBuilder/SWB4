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
public class XmlRpcClientConfig {
    private URI ServerURI;
    public XmlRpcClientConfig()
    {
        
    }
    public URI getServerURI()
    {
        return ServerURI;
    }
    public void setServerURI(URI ServerURI)
    {
        this.ServerURI=ServerURI;
    }
}
