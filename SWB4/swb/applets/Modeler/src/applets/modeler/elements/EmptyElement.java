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

package applets.modeler.elements;

import com.sun.org.apache.regexp.internal.recompile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class EmptyElement extends GraphElement
{
    Color col=new Color(200,200,200);
    /** Creates a new instance of StartElement */
    public EmptyElement()
    {
        setMaxChilds(0);
        setMinChilds(0);
        setNumChilds(0);
        setTitle("Empty");    
    }
    
    public void paintElement(Graphics g)
    {
        Rectangle rec=getBounds();
        int x=(int)rec.getX();
        int y=(int)rec.getY();
        int w=(int)getWidth();
        int h=(int)rec.getHeight();        
//        if(haveFocus())
//            g.setColor(Color.RED);
//        else
//            g.setColor(Color.BLUE);
        
        g.setColor(col);
        g.drawRoundRect(x, y, w, h,w/3,h/3);
//        g.setColor(Color.BLACK);
        int l=g.getFontMetrics().stringWidth(getTitle());
        g.drawString(getTitle(), x+(w-l)/2,y+h/2+5);
        
    }    
     
}
