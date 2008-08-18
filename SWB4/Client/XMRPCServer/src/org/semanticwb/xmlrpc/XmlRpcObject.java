/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcObject {
    protected Set<Part> parts;    
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
}
