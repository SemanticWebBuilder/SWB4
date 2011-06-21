/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public interface ServiceInfo
{
    public Service[] getServices();
    public Document getDocument();
    public Document[] getSchemas();
    public org.jdom.Document getJDom();
    public Document toDom(JSONObject object);
    public JSONObject toJSON(Document object);
    
            
}
