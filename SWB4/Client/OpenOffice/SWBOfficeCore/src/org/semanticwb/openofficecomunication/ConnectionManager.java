/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openofficecomunication;

import org.semanticwb.openoffice.*;
import java.net.URI;

/**
 *
 * @author victor.lorenzana
 */
final class ConnectionManager {
    private UserInfo userInfo;
    private URI uri;
    Configuration configuration=new Configuration();
    public ConnectionManager(UserInfo userInfo,URI uri)
    {
        this.userInfo=userInfo;
        this.uri=uri;
    }
    public void sendData()
    {
        
        
    }
}
