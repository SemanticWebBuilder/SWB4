/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.util.HashSet;
import java.util.Set;
import org.jdom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class XmlResponse {
    private Document document;
    private Set<Part> parts=new HashSet<Part>();
    public XmlResponse(Document document)
    {
        this.document=document;
    }
    public XmlResponse(Document document,Set<Part> parts)
    {
        this.document=document;
        this.parts=parts;
    }
    public Document getDocument()
    {
        return document;
    }
    public Set<Part> getResponseParts()
    {
        return parts;
    }
    
}
