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
 * StartElement.java
 *
 * Created on 17 de enero de 2006, 12:23 PM
 */

package applets.rules.elements;

import java.awt.Color;
import java.awt.Graphics;
import applets.modeler.elements.GraphElement;
import java.awt.event.MouseEvent;
import java.util.Iterator;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class AndElement extends GraphElement
{
    /** Creates a new instance of StartElement */
    public AndElement()
    {
        setNumChilds(2);
        setMinChilds(2);
        setMaxChilds(100);        
        setTitle("And");
        setToolTipText("And");    
    }
    
    public void init()
    {
        setImage(getContainer().getApplet().getImage(getClass().getResource("/applets/rules/images/and.gif")));        
        //setWidth(getImage().getWidth(getContainer().getApplet()));
        //setHeight(getImage().getWidth(getContainer().getApplet()));
        setWidth(84);
        setHeight(84);
    }
    
    public void onMouseMoved(java.awt.event.MouseEvent evt)
    {
    }
    
    
    public void onMouseClicked(java.awt.event.MouseEvent evt)
    {
    }   
    
    public void onMouseEntered(java.awt.event.MouseEvent evt)
    {
    }    
    
    public void onMouseExited(java.awt.event.MouseEvent evt)
    {
    }   
    
    public String getToolTipText()
    {
        String ret=getToolTipText("<html><font face='Verdana' size='3'>","&nbsp;&nbsp;&nbsp;&nbsp;","</font><html>");
        //System.out.println(ret);
        return ret;
    }
    
    public java.lang.String getToolTipText(String pre, String intr, String pos)
    {
        String s=super.getToolTipText();
        StringBuffer ret=new StringBuffer();
        ret.append(pre);
        ret.append("<b>-"+s+"</b><br>");
        Iterator it=getChilds();
        while(it.hasNext())
        {
            GraphElement g=(GraphElement)it.next();
            ret.append(intr+g.getToolTipText("",intr+"&nbsp;&nbsp;&nbsp;&nbsp;",""));
            if(it.hasNext())ret.append("<br>");
        }
        ret.append(pos);
        return ret.toString();
    }  
}
