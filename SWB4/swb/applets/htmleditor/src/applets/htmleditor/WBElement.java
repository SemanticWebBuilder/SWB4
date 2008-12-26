/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * @(#)WBElement.java	1.8 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package applets.htmleditor;

import javax.swing.text.html.parser.*;

import java.util.Hashtable;
import java.util.BitSet;
import java.io.*;
import java.util.*;

/**
 * An element as described in a DTD using the ELEMENT construct.
 * This is essentiall the description of a tag. It describes the
 * type, content model, attributes, attribute types etc. It is used
 * to correctly parse a document by the Parser.
 *
 * @see DTD
 * @see AttributeList
 * @version 1.8, 01/23/03
 * @author Arthur van Hoff
 */
public final class WBElement implements DTDConstants, Serializable {
    public int index;
    public String name;
    public boolean oStart;
    public boolean oEnd;
    public BitSet inclusions;
    public BitSet exclusions;
    public int type = ANY;
    public WBContentModel content;
    public AttributeList atts;

    static int maxIndex = 0;

    /**
     * A field to store user data. Mostly used to store
     * style sheets.
     */
    public Object data;

    WBElement() {
    }

    /**
     * Create a new element.
     */
    WBElement(String name, int index) {
	this.name = name;
	this.index = index;
	maxIndex = Math.max(maxIndex, index);
    }
    Vector tagsElements=new Vector();
    public void add(WBElement element)
    {
        tagsElements.add(element);
    }
    public Vector getElements()
    {
        return tagsElements;
    }
    /**
     * Get the name of the element.
     */
    public String getName() {
	return name;
    }

    /**
     * Return true if the start tag can be omitted.
     */
    public boolean omitStart() {
	return oStart;
    }

    /**
     * Return true if the end tag can be omitted.
     */
    public boolean omitEnd() {
	return oEnd;
    }

    /**
     * Get type.
     */
    public int getType() {
	return type;
    }

    /**
     * Get content model
     */
    public WBContentModel getContent() {
	return content;
    }

    /**
     * Get the attributes.
     */
    public AttributeList getAttributes() {
	return atts;
    }

    /**
     * Get index.
     */
    public int getIndex() {
	return index;
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
	return type == EMPTY;
    }

    /**
     * Convert to a string.
     */
    public String toString() {
	return name;
    }

    /**
     * Get an attribute by name.
     */
    public AttributeList getAttribute(String name) {
	for (AttributeList a = atts ; a != null ; a = a.next) {
	    if (a.name.equals(name)) {
		return a;
	    }
	}
	return null;
    }

    /**
     * Get an attribute by value.
     */
    public AttributeList getAttributeByValue(String name) {
	for (AttributeList a = atts ; a != null ; a = a.next) {
	    if ((a.values != null) && a.values.contains(name)) {
		return a;
	    }
	}
	return null;
    }
    
    
    public void setAttribute(String name,String value)
    {
        if(atts==null)
        {
            atts=new AttributeList(name);
            atts.value=value;
        }
        else
        {            
            for (AttributeList a = atts ; a != null ; a = a.next) {
                if (a.name.equals(name)) {
                    a.value=value;
                    return;
                }
            }
            AttributeList last=atts;
            AttributeList a=atts;
            while(a!=null)
            {
                last=a;
                a=a.next;
            }
            last.next=new AttributeList(name);
            last.next.value=value;             
        }
    }
    public String getHtml()
    {
        StringBuffer buf=new StringBuffer();
        /*if(this.type==this.DEFAULT)
        {
            if(this.getContent()!=null)
            {
                Vector elemVec=new Vector();
                this.getContent().getElements(elemVec);
                for(int i=0;i<elemVec.size();i++)
                {
                    if(elemVec.elementAt(i) instanceof String)
                    {
                        return elemVec.elementAt(i).toString();
                    }
                }
            }
            return "";
        }*/
        buf.append("<"+this.name);
        AttributeList a=atts;        
        while(a!=null)
        {
            if(a.name!=null && a.value!=null)
            {
                buf.append(" "+a.name+"=\""+a.value+"\"");
            }
            a=a.next;
        }
        buf.append(">\r\n");                
                  
        Vector elemVec=this.getElements();        
        for(int i=0;i<elemVec.size();i++)
        {                
            if(elemVec.elementAt(i) instanceof WBElement)
            {
                WBElement e=(WBElement)elemVec.elementAt(i);
                buf.append(e.getHtml());
            }
            else if(elemVec.elementAt(i) instanceof WBContentModel)
            {                    
                WBContentModel model=(WBContentModel)elemVec.elementAt(i);
                Vector modelVec=new Vector();
                model.getElements(modelVec);
                for(int j=0;j<elemVec.size();j++)
                {
                    if(elemVec.elementAt(i) instanceof WBElement)
                    {
                        WBElement e=(WBElement)elemVec.elementAt(i);
                        buf.append(e.getHtml()+"\r\n");
                    }
                }


            }
        }
        
        buf.append("</"+ this.name +">\r\n");
        return buf.toString();
    }

    static Hashtable contentTypes = new Hashtable();

    static {
	contentTypes.put("CDATA", new Integer(CDATA));
	contentTypes.put("RCDATA", new Integer(RCDATA));
	contentTypes.put("EMPTY", new Integer(EMPTY));
	contentTypes.put("ANY", new Integer(ANY));
    }

    public static int name2type(String nm) {
	Integer val = (Integer)contentTypes.get(nm);
	return (val != null) ? val.intValue() : 0;
    }
}
