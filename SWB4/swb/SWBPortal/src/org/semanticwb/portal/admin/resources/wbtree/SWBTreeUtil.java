/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 


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
        Element option=addNode("option","refresh","Refresh",menu); //tma.getWebPage("WBAd_glos_refresh").getDisplayName(user.getLanguage())
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
