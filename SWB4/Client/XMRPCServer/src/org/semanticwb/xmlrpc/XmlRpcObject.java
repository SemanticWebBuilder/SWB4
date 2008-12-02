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
    public void init(ServletConfig config)
    {
        this.config=config;
    }
}
