/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject 
{
    public boolean isValidVersion(String version)
    {
        return IOfficeApplication.version.equals(version);
    }    
}
