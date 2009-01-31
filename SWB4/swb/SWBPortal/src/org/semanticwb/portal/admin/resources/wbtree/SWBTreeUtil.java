/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * SWBTreeUtils.java
 *
 * Created on 20 de diciembre de 2005, 02:14 PM
 */

package org.semanticwb.portal.admin.resources.wbtree;


import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBTreeUtil
{
    /**
     *
     * @param node
     * @param id
     * @param name
     * @param parent
     * @return
     */    
    public static Element addNode(String node, String id, String name, Element parent)
    {
        Element ret=addElement(node,null,parent);
        if(id!=null)ret.setAttribute("id",id);
        if(name!=null)ret.setAttribute("name",name);
        return ret;
    }

    /**
     *
     * @param name
     * @param value
     * @param parent
     * @return
     */    
    public static Element addElement(String name, String value, Element parent)
    {
        Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) ele.appendChild(doc.createTextNode(value));
        parent.appendChild(ele);
        return ele;
    }        
    
    public static void addOptRefresh(Element menu, User user)
    {
        WebSite tma=SWBContext.getAdminWebSite();
        Element option=addNode("option","refresh",tma.getWebPage("WBAd_glos_refresh").getDisplayName(user.getLanguage()),menu);
        option.setAttribute("action","reload");
        option.setAttribute("shortCut","F5");      
        option.setAttribute("icon","refresh");
    }   
    
    public static void addSeparator(Element menu)
    {
        NodeList nl=menu.getChildNodes();
        if(nl.getLength()>0)
        {
            if(!nl.item(nl.getLength()-1).getNodeName().equals("separator"))
            {
                addNode("separator","-","-",menu);        
            }
        }
    }     
    
    /**
     *
     * @param it
     * @return
     */    
    public static Iterator sortIterator(Iterator it)
    {
        TreeSet set=new TreeSet(new SWBComparator());
        while(it.hasNext())
        {
            set.add(it.next());
        }
        return set.iterator();        
    }
    
    /**
     *
     * @param en
     * @return
     */    
    public static Iterator sortEnumeration(Enumeration en)
    {
        TreeSet set=new TreeSet(new SWBComparator());
        while(en.hasMoreElements())
        {
            set.add(en.nextElement());
        }
        return set.iterator();        
    }    
    
    /**
     *
     * @param collection
     * @return
     */    
    public static Iterator sortCollection(Collection collection)
    {
        TreeSet set=new TreeSet(new SWBComparator());
        set.addAll(collection);
        return set.iterator();        
    }        
    
}
